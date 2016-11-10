package bilokhado.transactionexplorer.service.implementation;

import bilokhado.transactionexplorer.service.AbstractChainLink;
import bilokhado.transactionexplorer.service.ChainLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

/**
 * ChainLink ejb for which transaction is mandatory
 */
@Stateless
public class TransactionMandatoryChainLink extends AbstractChainLink {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionMandatoryChainLink.class);

    @PersistenceContext(unitName = "h2mem")
    EntityManager em;

    @Resource(mappedName = "jdbc/h2mem")
    DataSource dataSource;

    @Override
    public String getType() {
        return "MANDATORY";
    }

    @Override
    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }


    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @Override
    public void processChain(int baseId, int id, List<ChainLink> ejbChain, String[][] resultMatrix) {
        super.processChain(baseId, id, ejbChain, resultMatrix);
    }

}
