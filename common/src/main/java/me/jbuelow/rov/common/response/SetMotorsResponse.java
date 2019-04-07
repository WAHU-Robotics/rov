package me.jbuelow.rov.common.response;

import me.jbuelow.rov.common.command.Command;

public class SetMotorsResponse extends Response {
  /**
   * 
   */
  private static final long serialVersionUID = 2371092517758388080L;

  public SetMotorsResponse(Command request, boolean success, Exception exception) {
    super(request, success, exception);
  }

  public SetMotorsResponse(boolean success) {
    super(success);
  }
  
  public SetMotorsResponse(boolean success, Exception exception) {
    super(success, exception);
  }
}
