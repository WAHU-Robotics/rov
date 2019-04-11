package me.jbuelow.rov.wet.vehicle.hardware;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * Object used to represent a physical motor.
 */
@Slf4j
public class Servo {
  private static final float VALUE_TO_PULSE_FACTOR = 2000f;
  private static final float CENTER_PULSE = 1.5f;

  private PwmChannel pwmChannel;
  private Integer value;

  public Servo(PwmChannel pwmChannel) throws IOException {
    this.pwmChannel = pwmChannel;
  }
  
  public void setValue(int value) throws IOException {
    if (this.value == null || !this.value.equals(value)) {
      this.value = value;
      pwmChannel.setServoPulse(convertToPulse(value));
    }
  }
  
  public Integer getValue() {
    return value;
  }

  private float convertToPulse(int power) {
    return ((float) power / VALUE_TO_PULSE_FACTOR) + CENTER_PULSE;
  }
  
  private void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //
    }
  }
}
