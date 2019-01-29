package me.jbuelow.rov.common;

import lombok.Getter;
import lombok.Setter;

public class SetMotorsResponse extends Response {

  @Getter
  @Setter
  private boolean success;

  public SetMotorsResponse(boolean success) {
    this.success = success;
  }

}
