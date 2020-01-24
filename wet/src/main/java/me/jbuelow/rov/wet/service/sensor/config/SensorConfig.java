package me.jbuelow.rov.wet.service.sensor.config;

import java.util.ArrayList;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import me.jbuelow.rov.common.sensor.Type;

@Getter
@Setter
public class SensorConfig {

  private String driver;
  private Map<String, Integer> driverParameters;
  private ArrayList<Type> primaryTypes;

}
