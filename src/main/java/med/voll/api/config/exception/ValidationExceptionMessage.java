package med.voll.api.config.exception;

public class ValidationExceptionMessage extends RuntimeException {

  public ValidationExceptionMessage(String message) {
    super(message);
  }
}
