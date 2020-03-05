package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth;

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Pressure;

public class MS5837_02BA implements DepthDevice {

  @Override
  public Quantity<Length> getDepth() throws IOException {
    return null;
  }

  @Override
  public void setZero() {

  }

  @Override
  public void setZero(Quantity<Length> offset) {

  }

  @Override
  public Quantity<Pressure> getPressure() throws IOException {
    return null;
  }

  @Override
  public int getBus() {
    return 0;
  }

  @Override
  public int getAddress() {
    return 0;
  }
}
