package me.jbuelow.rov.common.response;

import lombok.Getter;

public class WaterTemp extends Response {
  private static final long serialVersionUID = -6032642389159557521L;

  @Getter
  private final float temperature;

  public WaterTemp(float temperature) {
    this.temperature = temperature;
  }
}
