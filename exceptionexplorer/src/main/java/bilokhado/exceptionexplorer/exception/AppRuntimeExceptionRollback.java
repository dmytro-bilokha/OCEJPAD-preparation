package bilokhado.exceptionexplorer.exception;

import javax.ejb.ApplicationException;

/**
 * Application runtime exception with rollback
 */
@ApplicationException(rollback = true)
public class AppRuntimeExceptionRollback extends RuntimeException {
}
