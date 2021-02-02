package org.snapshotscience.rov.wet.vehicle.hardware.i2c.sensor.pressure;

import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.quantity.Pressure;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.sensor.SensorDevice;

public interface PressureDevice extends SensorDevice {

  Measurable<Pressure> getPressure() throws IOException;

}
