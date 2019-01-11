package pl.coderstrust.logic;

public class InvoiceBookOperationException extends Throwable {

  public InvoiceBookOperationException() {
  }

  public InvoiceBookOperationException(String message) {
    super(message);
  }

  public InvoiceBookOperationException(Throwable cause) {
    super(cause);
  }

  public InvoiceBookOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
