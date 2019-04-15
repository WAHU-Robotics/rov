package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Object used to represent a physical motor.
 */
@Slf4j
public class Motor {
  private static final float POWER_TO_PULSE_FACTOR = 2000f;
  private static final float CENTER_PULSE = 1.5f;
  
  private boolean armed = false;
  private PwmChannel pwmChannel;
  private Integer power;
  
  public Motor(PwmChannel pwmChannel) throws IOException {
    this.pwmChannel = pwmChannel;
  }
  
  public void setPower(int power) throws IOException {
    if (this.power == null || !this.power.equals(power)) {
      this.power = power;
      pwmChannel.setServoPulse(convertToPulse(power));
    }
  }
  
  public Integer getPower() {
    return power;
  }
  
  public boolean isArmed() {
    return armed;
  }
  
  public void arm() throws IOException {
    if (!armed) {
      //arm our Motors
      log.debug("Arming motor on channel " + pwmChannel.getChannelNumber());
      pwmChannel.setServoPulse(1.5f);
      delay(1500);
      setPower(0);
      log.debug("Arming complete.");
    }
  }

  private float convertToPulse(int power) {
    return ((float) power / POWER_TO_PULSE_FACTOR) + CENTER_PULSE;
  }
  
  private void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //
    }
  }
}
