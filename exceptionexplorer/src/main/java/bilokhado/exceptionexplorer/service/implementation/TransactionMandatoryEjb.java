package bilokhado.exceptionexplorer.service.implementation;

import bilokhado.exceptionexplorer.service.AbstractTransactionEjb;
import bilokhado.exceptionexplorer.service.TransactionEjb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * TransactionEjb ejb for which transaction is mandatory
 */
@Stateless
public class TransactionMandatoryEjb extends AbstractTransactionEjb {

    @PersistenceContext(unitName = "h2mem")
    EntityManager em;

    @EJB
    BookIdPoolService poolService;

    @Override
    public String getType() {
        return "MANDATORY";
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @PostConstruct
    public void init() {
        super.register(poolService);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @Override
    public void persistItemAndThrowException(int itemId, Exception ex) throws Exception {
        super.persistItemAndThrowException(itemId, ex);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @Override
    public Optional<Exception> persistItemAndCallNext(int itemId, Exception ex, TransactionEjb next) {
        return super.persistItemAndCallNext(itemId, ex, next);
    }

}
