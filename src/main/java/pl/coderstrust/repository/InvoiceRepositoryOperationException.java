package pl.coderstrust.repository;

public class InvoiceRepositoryOperationException extends Exception {

  public InvoiceRepositoryOperationException() {
  }

  public InvoiceRepositoryOperationException(String message) {
    super(message);
  }

  public InvoiceRepositoryOperationException(Throwable cause) {
    super(cause);
  }

  public InvoiceRepositoryOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
