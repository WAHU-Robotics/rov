package me.jbuelow.rov.common.exception;

public class ROVException extends Exception {
  public ROVException() {
    super();
  }

  public ROVException(String errorMessage) {
    super(errorMessage);
  }

  public ROVException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
