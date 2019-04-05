package me.jbuelow.rov.common.response;

public class SetMotionResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -7695927574850060353L;

  public SetMotionResponse(boolean success) {
    super(success);
  }

  public SetMotionResponse(boolean success, Exception exception) {
    super(success, exception);
  }

}
