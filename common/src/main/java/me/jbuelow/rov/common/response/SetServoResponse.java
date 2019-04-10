package me.jbuelow.rov.common.response;

import me.jbuelow.rov.common.command.Command;

public class SetServoResponse extends Response {

  private static final long serialVersionUID = -6274761718600641551L;

  public SetServoResponse(Command request, boolean success, Exception exception) {
    super(request, success, exception);
  }

  public SetServoResponse(boolean success) {
    super(success);
  }

  public SetServoResponse(boolean success, Exception exception) {
    super(success, exception);
  }
}
