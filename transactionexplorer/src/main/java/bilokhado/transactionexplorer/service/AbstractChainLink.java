package bilokhado.transactionexplorer.service;

import bilokhado.transactionexplorer.model.Item;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

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
        persistTestItem(baseId + id);
        int nextId = id + 1;
        if (nextId < numberOfChainLinks)
            ejbChain.get(nextId).processChain(baseId, nextId, ejbChain, resultMatrix);
        checkItemsVisible(id, baseId + id, baseId + numberOfChainLinks, ejbChain, resultRow);
        resultMatrix[id] = resultRow;
    }

    protected abstract EntityManager getEntityManager();
    protected abstract Logger getLogger();

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
                getLogger().error("For bean with type {} during checking visibility of bean with type {} exception happened"
                        , getType()
                        , ejbChain.get(i).getType()
                        , ex);
            }
        }
    }

    private void persistTestItem(int itemId) {
        getEntityManager().persist(new Item(itemId));
    }

}
