package me.jbuelow.rov.common.exception;

public class ROVException extends Exception {

  private static final long serialVersionUID = 3657204124543182131L;

  protected ROVException() {
    super();
  }

  protected ROVException(String errorMessage) {
    super(errorMessage);
  }

  public ROVException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
