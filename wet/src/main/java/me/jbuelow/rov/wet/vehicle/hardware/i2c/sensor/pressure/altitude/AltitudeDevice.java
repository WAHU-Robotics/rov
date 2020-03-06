package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.altitude;

import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.quantity.Length;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.PressureDevice;
import org.jscience.geography.coordinates.Altitude;

public interface AltitudeDevice extends PressureDevice {

  Altitude getAltitude() throws IOException;

  Measurable<Length> getAltitudeLength() throws IOException;

  void setZero();

  void setZero(Measurable<Length> offset);

}
