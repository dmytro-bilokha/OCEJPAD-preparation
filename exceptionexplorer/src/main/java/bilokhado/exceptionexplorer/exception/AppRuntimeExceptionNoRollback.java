package bilokhado.exceptionexplorer.exception;

import javax.ejb.ApplicationException;

/**
 * Application runtime exception without rollback
 */
@ApplicationException(rollback = false)
public class AppRuntimeExceptionNoRollback extends RuntimeException {
}
