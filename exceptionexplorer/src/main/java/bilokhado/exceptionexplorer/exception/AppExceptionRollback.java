package bilokhado.exceptionexplorer.exception;

import javax.ejb.ApplicationException;

/**
 * Application exception with rollback
 */
@ApplicationException(rollback = true)
public class AppExceptionRollback extends Exception {
}
