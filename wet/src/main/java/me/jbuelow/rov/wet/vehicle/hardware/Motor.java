package me.jbuelow.rov.wet.vehicle.hardware;

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
  }
  
  public void setPower(int power) {
    this.power = power;
    pwmInterface.setServoPulse(pwmPort, convertToPulse(power));
  }
  
  public int getPower() {
    return power;
  }
  
  public boolean isArmed() {
    return armed;
  }
  
  public void arm() {
    if (!armed) {
      //TODO
    }
  }

  private float convertToPulse(int power) {
    return ((float) power / POWER_TO_PULSE_FACTOR) + CENTER_PULSE;
  }
}
