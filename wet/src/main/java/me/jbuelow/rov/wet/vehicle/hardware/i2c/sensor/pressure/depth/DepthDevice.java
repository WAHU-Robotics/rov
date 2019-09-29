package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth;

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.PressureDevice;

public interface DepthDevice extends PressureDevice {

  Quantity<Length> getDepth() throws IOException;

  void setZero();

  void setZero(Quantity<Length> offset);

}
