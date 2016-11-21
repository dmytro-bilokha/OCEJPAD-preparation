package bilokhado.exceptionexplorer.exception;

import javax.ejb.ApplicationException;

/**
 * Application exception with no rollback option
 */
@ApplicationException(rollback = false)
public class AppExceptionNoRollback extends Exception {
}
