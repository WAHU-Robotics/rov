package me.jbuelow.rov.common;

import lombok.Getter;
import lombok.Setter;

public class SetMotorsResponse extends Response {

  @Getter
  private boolean success;

  @Getter
  private Exception exception;

  public SetMotorsResponse(boolean success) {
    this.success = success;
  }

  public SetMotorsResponse(boolean success, Exception exception) {
    if (!success) {
      this.exception = exception;
    }
  }

}
