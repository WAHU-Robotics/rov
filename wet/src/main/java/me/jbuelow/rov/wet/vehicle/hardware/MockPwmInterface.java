package me.jbuelow.rov.wet.vehicle.hardware;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("noHardware")
public class MockPwmInterface implements PwmInterface {

  @Override
  public int getServoMaxValue(int freq) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getServoCenterValue(int freq) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getServoMinValue(int freq) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getPulseFromValue(int freq, int value) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getServoValueFromPulse(int freq, float targetPulse) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setServoPulse(int channel, float pulseMS) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setPWM(int channel, int on, int off) throws IllegalArgumentException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setPWMFreq(int freq) {
    // TODO Auto-generated method stub

  }

}
