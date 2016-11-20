package bilokhado.exceptionexplorer.service;

import bilokhado.exceptionexplorer.model.Item;
import bilokhado.exceptionexplorer.service.implementation.BookIdPoolService;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * The abstract implementation of the TransactionEjb interface.
 * The goal of the class is to avoid code duplication, because every ejb bean will be almost the same.
 * Difference is only in transaction attribute type.
 */
public abstract class AbstractTransactionEjb implements TransactionEjb {

    @Override
    public abstract String getType();

    protected abstract EntityManager getEntityManager();

    protected void register(BookIdPoolService service) {
        service.countEjbByType(getType());
    }

    @Override
    public void persistItemAndThrowException(int itemId, Exception ex) throws Exception {
        EntityManager em = getEntityManager();
        if (em != null)
            em.persist(new Item(itemId));
        throw ex;
    }

    public Optional<Exception> persistItemAndCallNext(int itemId, Exception ex, TransactionEjb next) {
        EntityManager em = getEntityManager();
        if (em != null)
            em.persist(new Item(itemId));
        Exception thrownException = null;
        try {
            next.persistItemAndThrowException(itemId + 1, ex);
        } catch (Exception exThrown) {
            thrownException = exThrown;
        }
        return Optional.ofNullable(thrownException);
    }

    @Override
    public String toString() {
        return getType();
    }

}
