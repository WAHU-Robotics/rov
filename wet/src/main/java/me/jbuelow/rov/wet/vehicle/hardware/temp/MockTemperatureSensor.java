package me.jbuelow.rov.wet.vehicle.hardware.temp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("noHardware")
public class MockTemperatureSensor implements TempDevice{
  @Override
  public float getTemp() {
    return 0;
  }

  @Override
  public int getBus() {
    return 0;
  }

  @Override
  public int getAddress() {
    return 0;
  }
}
