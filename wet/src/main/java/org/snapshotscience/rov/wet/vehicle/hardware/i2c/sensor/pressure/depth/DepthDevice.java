package org.snapshotscience.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth;

import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.quantity.Length;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.sensor.pressure.PressureDevice;

public interface DepthDevice extends PressureDevice {

  Measurable<Length> getDepth() throws IOException;

  void setZero();

  void setZero(Measurable<Length> offset);

}
