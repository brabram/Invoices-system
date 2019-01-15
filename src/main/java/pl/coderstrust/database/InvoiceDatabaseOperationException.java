package pl.coderstrust.database;

public class InvoiceDatabaseOperationException extends Exception {

  public InvoiceDatabaseOperationException() {
  }

  public InvoiceDatabaseOperationException(String message) {
    super(message);
  }

  public InvoiceDatabaseOperationException(Throwable cause) {
    super(cause);
  }

  public InvoiceDatabaseOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
