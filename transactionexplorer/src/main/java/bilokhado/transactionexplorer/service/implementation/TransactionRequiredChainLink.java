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
 * ChainLink ejb which requires transaction
 */
@Stateless
public class TransactionRequiredChainLink extends AbstractChainLink {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionRequiredChainLink.class);

    @PersistenceContext(unitName = "h2mem")
    EntityManager em;

    @Override
    public String getType() {
        return "TransactionRequired";
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void processChain(int baseId, int id, List<ChainLink> ejbChain, String[][] resultMatrix) {
        super.processChain(baseId, id, ejbChain, resultMatrix);
    }

}
