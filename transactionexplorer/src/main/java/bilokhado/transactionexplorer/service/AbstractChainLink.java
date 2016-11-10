package bilokhado.transactionexplorer.service;

import bilokhado.transactionexplorer.model.Item;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The abstract implementation of the ChainLink interface.
 * The goal of the class is to avoid code duplication, because every ejb bean will be almost the same.
 * Difference is only in transaction attribute type.
 */
public abstract class AbstractChainLink implements ChainLink {

    @Override
    public abstract String getType();

    @Override
    public void processChain(int baseId, int id, List<ChainLink> ejbChain, String[][] resultMatrix) {
        int numberOfChainLinks = ejbChain.size();
        String[] resultRow = new String[numberOfChainLinks];
        checkItemsVisible(0, baseId, baseId + id, ejbChain, resultRow);
        boolean jdbcUsed = persistTestItem(baseId + id);
        int nextId = id + 1;
        if (nextId < numberOfChainLinks)
            ejbChain.get(nextId).processChain(baseId, nextId, ejbChain, resultMatrix);
        if (jdbcUsed)
            checkItemsVisibleWithJdbc(id, baseId + id, baseId + numberOfChainLinks, ejbChain, resultRow);
        else
            checkItemsVisible(id, baseId + id, baseId + numberOfChainLinks, ejbChain, resultRow);
        resultMatrix[id] = resultRow;
    }

    protected abstract EntityManager getEntityManager();
    protected abstract Logger getLogger();
    protected abstract DataSource getDataSource();

    private void checkItemsVisible(int beginColumn, int startId, int endId, List<ChainLink> ejbChain, String[] resultRow) {
        EntityManager em = getEntityManager();
        for (int i = beginColumn, j = startId; j < endId; i++, j++) {
            try {
                Item item = em.find(Item.class, j);
                if (item == null)
                    resultRow[i] = "N";
                else
                    resultRow[i] = "Y";
            } catch (Exception ex) {
                resultRow[i] = "X";
                getLogger().error("For bean with type {} during checking visibility of bean with type {} in chain {} exception happened"
                        , getType()
                        , ejbChain.get(i).getType()
                        , getChainTitle(ejbChain)
                        , ex);
            }
        }
    }

    private void checkItemsVisibleWithJdbc(int beginColumn, int startId, int endId, List<ChainLink> ejbChain, String[] resultRow) {
        try (Connection con = getDataSource().getConnection();
                PreparedStatement statement = con.prepareStatement("SELECT id FROM item WHERE id=?");) {
            for (int i = beginColumn, j = startId; j < endId; i++, j++) {
                statement.setInt(1, j);
                try (ResultSet resultSet = statement.executeQuery();) {
                    if (resultSet.next())
                        resultRow[i] = "JY";
                    else
                        resultRow[i] = "JN";
                } catch (Exception ex) {
                    resultRow[i] = "JX";
                    getLogger().error("For bean with type {} during checking JDBC visibility of bean with type {} in chain {} exception happened"
                            , getType()
                            , ejbChain.get(i).getType()
                            , getChainTitle(ejbChain)
                            , ex);
                }
            }
        } catch (Exception ex) {
            getLogger().error("For bean {} in chain {} unable to create JDBC statement", getType(), getChainTitle(ejbChain));
            for (int i = beginColumn; i < resultRow.length; i++) {
                resultRow[i] ="JXX";
            }
        }
    }

    private boolean persistTestItem(int itemId) {
        boolean jdbcUsed = false;
        try {
            getEntityManager().persist(new Item(itemId));
        } catch (Exception ex) {
            jdbcUsed = true;
            getLogger().warn("For bean with type {} during persisting item with id={} exception happened, will use JDBC"
                    , getType()
                    , itemId
                    , ex);
            persistTestItemWithJdbc(itemId);
        }
        return jdbcUsed;
    }

    private String getChainTitle(List<ChainLink> linkList) {
        return linkList.stream().map(ChainLink::getType).collect(Collectors.joining(","));
    }

    private void persistTestItemWithJdbc(int itemId) {
        DataSource ds = getDataSource();
        try (Connection con = ds.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO item (id) VALUES (?)");) {
            statement.setInt(1, itemId);
            statement.executeUpdate();
        } catch (Exception ex) {
            getLogger()
                    .error("For bean with type {} during inserting item with id={} by JDBC got exception"
                    , getType()
                    , itemId);
        }
    }

    @Override
    public String toString() {
        return getType();
    }

}
