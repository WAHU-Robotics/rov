package me.jbuelow.rov.wet.vehicle.hardware;

import lombok.extern.slf4j.Slf4j;

/**
 * Object used to represent a physical motor.
 */
@Slf4j
public class Motor {
  private static final float POWER_TO_PULSE_FACTOR = 2000f;
  private static final float CENTER_PULSE = 1.5f;
  
  private boolean armed = false;
  private PwmInterface pwmInterface;
  private int pwmPort;
  private int power;
  
  public Motor(PwmInterface pwmInterface, int pwmPort) {
    this.pwmInterface = pwmInterface;
    this.pwmPort = pwmPort;
    setPower(0);
    arm();
  }
  
  public void setPower(int power) {
    if (this.power != power) {
      this.power = power;
      pwmInterface.setServoPulse(pwmPort, convertToPulse(power));
    }
  }
  
  public int getPower() {
    return power;
  }
  
  public boolean isArmed() {
    return armed;
  }
  
  public void arm() {
    if (!armed) {
      //arm our Motors
      log.debug("Arming motor on channel " + pwmPort);
      this.pwmInterface.setServoPulse(pwmPort, 1.75f);
      delay(1000);
      this.pwmInterface.setServoPulse(pwmPort, 0f);
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
