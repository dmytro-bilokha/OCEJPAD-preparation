package bilokhado.exceptionexplorer.service;

import java.util.Optional;

/**
 * Interface of the ejb to test exceptions wrapping
 */
public interface TransactionEjb {

    public String getType();

    public void persistItemAndThrowException(int itemId, Exception ex) throws Exception;

    public Optional<Exception> persistItemAndCallNext(int itemId, Exception ex, TransactionEjb next);

}
