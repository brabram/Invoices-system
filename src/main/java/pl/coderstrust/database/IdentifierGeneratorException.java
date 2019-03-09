package pl.coderstrust.database;

public class IdentifierGeneratorException  extends Exception {

  public IdentifierGeneratorException() {
  }

  public IdentifierGeneratorException(String message) {
    super(message);
  }

  public IdentifierGeneratorException(Throwable cause) {
    super(cause);
  }

  public IdentifierGeneratorException(String message, Throwable cause) {
    super(message, cause);
  }
}
