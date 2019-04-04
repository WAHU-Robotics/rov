package me.jbuelow.rov.wet.vehicle;

public class InvalidThrustAxisException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 7659378390839865121L;

  public InvalidThrustAxisException() {
    super();
  }

  public InvalidThrustAxisException(String message) {
    super(message);
  }

  public InvalidThrustAxisException(Throwable cause) {
    super(cause);
  }

  public InvalidThrustAxisException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidThrustAxisException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
