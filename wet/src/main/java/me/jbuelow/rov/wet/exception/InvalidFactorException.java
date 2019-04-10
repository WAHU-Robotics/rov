package me.jbuelow.rov.wet.exception;

public class InvalidFactorException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 5619309471373955898L;

  public InvalidFactorException() {
    super();
  }

  public InvalidFactorException(String message) {
    super(message);
  }

  public InvalidFactorException(Throwable cause) {
    super(cause);
  }

  public InvalidFactorException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidFactorException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
