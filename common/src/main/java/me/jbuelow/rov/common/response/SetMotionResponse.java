package me.jbuelow.rov.common.response;

import lombok.Getter;

public class SetMotionResponse extends Response {

  @Getter
  private boolean success;

  @Getter
  private Exception exception;

  public SetMotionResponse(boolean success) {
    this.success = success;
  }

  public SetMotionResponse(boolean success, Exception exception) {
    if (!success) {
      this.exception = exception;
    }
  }

}
