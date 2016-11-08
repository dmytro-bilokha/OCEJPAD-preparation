package bilokhado.transactionexplorer.service.implementation;

import bilokhado.transactionexplorer.service.AbstractChainLink;
import bilokhado.transactionexplorer.service.ChainLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * ChainLink ejb which requires new transaction
 */
@Stateless
public class TransactionRequiresNewChainLink extends AbstractChainLink {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionRequiresNewChainLink.class);

    @PersistenceContext(unitName = "h2mem")
    EntityManager em;

    @Override
    public String getType() {
        return "TransactionRequiresNew";
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void processChain(int baseId, int id, List<ChainLink> ejbChain, String[][] resultMatrix) {
        super.processChain(baseId, id, ejbChain, resultMatrix);
    }

}
