package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.altitude;

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.PressureDevice;

public interface AltitudeDevice extends PressureDevice {

  Quantity<Length> getAltitude() throws IOException;

  void setZero();

  void setZero(Quantity<Length> offset);

}
