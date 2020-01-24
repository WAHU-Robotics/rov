package me.jbuelow.rov.wet.service.sensor;

import lombok.Getter;
import me.jbuelow.rov.wet.service.sensor.drivers.SensorDriver;

@Getter
public class Sensor {

  private final String zone;
  private final SensorDriver driver;

  public Sensor(String zone, SensorDriver driver) {
    this.zone = zone;
    this.driver = driver;
  }
}
