package pl.coderstrust.service;

public class InvoiceServiceOperationException extends Exception {

  InvoiceServiceOperationException() {
  }

  InvoiceServiceOperationException(String message) {
    super(message);
  }

  InvoiceServiceOperationException(Throwable cause) {
    super(cause);
  }

  InvoiceServiceOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
