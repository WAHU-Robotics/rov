package me.jbuelow.rov.wet.vehicle.hardware;

public interface PwmInterface {

  int getServoMaxValue(int freq);

  int getServoCenterValue(int freq);

  int getServoMinValue(int freq);

  double getPulseFromValue(int freq, int value);

  int getServoValueFromPulse(int freq, float targetPulse);

  void setServoPulse(int channel, float pulseMS);

  void setPWM(int channel, int on, int off) throws IllegalArgumentException;

  void setPWMFreq(int freq);
}
