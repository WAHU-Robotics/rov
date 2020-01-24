package me.jbuelow.rov.wet.service.sensor;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("sensorMock")
public class SensorSystemMock implements SensorSystem {

}
