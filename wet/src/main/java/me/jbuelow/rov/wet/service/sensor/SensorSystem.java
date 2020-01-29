package me.jbuelow.rov.wet.service.sensor;

import me.jbuelow.rov.common.sensor.Reading;
import me.jbuelow.rov.common.sensor.Type;
import me.jbuelow.rov.common.sensor.Zone;

public interface SensorSystem {

  Reading<?, ?> getReading(Type type, Zone zone);
}
