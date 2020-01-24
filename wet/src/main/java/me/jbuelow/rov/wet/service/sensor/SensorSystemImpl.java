package me.jbuelow.rov.wet.service.sensor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.service.sensor.config.Config;
import me.jbuelow.rov.wet.service.sensor.config.SensorConfig;
import me.jbuelow.rov.wet.service.sensor.drivers.SensorDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("!sensorMock")
public class SensorSystemImpl implements SensorSystem, ApplicationContextAware {

  @Setter
  private ApplicationContext applicationContext;

  private final Config sensorConfig;

  private final List<Sensor> sensors = new ArrayList<>();

  public SensorSystemImpl(Config sensorConfig) {
    this.sensorConfig = sensorConfig;
  }

  @PostConstruct
  public void loadSensors() {
    sensorConfig.getSensors().forEach((zone, sensorConfigs) -> {
      for (SensorConfig sensor : sensorConfigs) {
        Class<? extends SensorDriver> sensorClass;
        try {
          Class<?> aClass = Class.forName(sensor.getDriver());
          if (SensorDriver.class.isAssignableFrom(aClass)) {
            sensorClass = (Class<? extends SensorDriver>) aClass;
          } else {
            log.error("Driver class {} does not implement SensorDriver and cannot be loaded.", aClass.getCanonicalName());
            continue;
          }
        } catch (ClassNotFoundException e) {
          log.error("No driver class found for {}", sensor.getDriver(), e);
          continue;
        }
        SensorDriver driver;
        try {
          driver = (SensorDriver) sensorClass.getDeclaredConstructors()[0].newInstance(sensor.getDriverParameters());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
          log.error("Exception while trying to instantiate SensorDriver {}: ", sensorClass.getCanonicalName(), e);
          continue;
        }
        log.debug("Loaded SensorDriver {} for zone {}.", driver.getClass().getCanonicalName(), zone);
        sensors.add(new Sensor(zone, driver));
      }
    });
  }


}
