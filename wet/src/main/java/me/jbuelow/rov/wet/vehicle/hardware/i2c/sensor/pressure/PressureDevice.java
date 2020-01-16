package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure;

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.quantity.Pressure;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.SensorDevice;

public interface PressureDevice extends SensorDevice {

  Quantity<Pressure> getPressure() throws IOException;

}
