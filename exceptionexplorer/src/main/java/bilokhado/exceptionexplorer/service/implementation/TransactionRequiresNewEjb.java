package bilokhado.exceptionexplorer.service.implementation;

import bilokhado.exceptionexplorer.service.AbstractTransactionEjb;
import bilokhado.exceptionexplorer.service.TransactionEjb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * TransactionEjb ejb which requires new transaction
 */
@Stateless
public class TransactionRequiresNewEjb extends AbstractTransactionEjb {

    @EJB
    BookIdPoolService poolService;

    @PersistenceContext(unitName = "h2mem")
    EntityManager em;

    @Override
    public String getType() {
        return "REQUIRES_NEW";
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @PostConstruct
    public void init() {
        super.register(poolService);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void persistItemAndThrowException(int itemId, Exception ex) throws Exception {
        super.persistItemAndThrowException(itemId, ex);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Optional<Exception> persistItemAndCallNext(int itemId, Exception ex, TransactionEjb next) {
        return super.persistItemAndCallNext(itemId, ex, next);
    }

}
