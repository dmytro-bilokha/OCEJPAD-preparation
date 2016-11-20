package bilokhado.exceptionexplorer.service.implementation;

import bilokhado.exceptionexplorer.service.AbstractTransactionEjb;
import bilokhado.exceptionexplorer.service.TransactionEjb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * TransactionEjb ejb with NEVER transaction attribute
 */
@Stateless
public class TransactionNeverEjb extends AbstractTransactionEjb {

    @EJB
    BookIdPoolService poolService;

    @Override
    public String getType() {
        return "NEVER";
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }

    @PostConstruct
    public void init() {
        super.register(poolService);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @Override
    public void persistItemAndThrowException(int itemId, Exception ex) throws Exception {
        super.persistItemAndThrowException(itemId, ex);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @Override
    public Optional<Exception> persistItemAndCallNext(int itemId, Exception ex, TransactionEjb next) {
        return super.persistItemAndCallNext(itemId, ex, next);
    }
}
