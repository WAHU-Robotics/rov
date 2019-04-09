package me.jbuelow.rov.wet.vehicle.hardware;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * Object used to represent a physical motor.
 */
@Slf4j
public class Motor {
  private static final float POWER_TO_PULSE_FACTOR = 2000f;
  private static final float CENTER_PULSE = 1.5f;
  
  private boolean armed = false;
  private PwmChannel pwmChannel;
  private int power;
  
  public Motor(PwmChannel pwmChannel) throws IOException {
    this.pwmChannel = pwmChannel;
    arm();
  }
  
  public void setPower(int power) throws IOException {
    if (this.power != power) {
      this.power = power;
      pwmChannel.setServoPulse(convertToPulse(power));
    }
  }
  
  public int getPower() {
    return power;
  }
  
  public boolean isArmed() {
    return armed;
  }
  
  public void arm() throws IOException {
    if (!armed) {
      //arm our Motors
      log.debug("Arming motor on channel " + pwmChannel.getChannelNumber());
      pwmChannel.setServoPulse(1.75f);
      delay(1000);
      pwmChannel.setServoPulse(0f);
      delay(1000);
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
