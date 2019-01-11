package pl.coderstrust.logic;

public class InvoiceServiceOperationException extends Throwable {

  public InvoiceServiceOperationException() {
  }

  public InvoiceServiceOperationException(String message) {
    super(message);
  }

  public InvoiceServiceOperationException(Throwable cause) {
    super(cause);
  }

  public InvoiceServiceOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
