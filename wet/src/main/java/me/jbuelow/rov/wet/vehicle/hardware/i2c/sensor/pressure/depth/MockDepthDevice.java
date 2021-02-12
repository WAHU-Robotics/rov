package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth;

import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.quantity.Pressure;
import javax.measure.unit.SI;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!useDepth")
public class MockDepthDevice implements DepthDevice {

  @Override
  public Measurable<Length> getDepth() throws IOException {
    return Measure.valueOf(0, SI.METER);
  }

  @Override
  public void setZero() {

  }

  @Override
  public void setZero(Measurable<Length> offset) {

  }

  @Override
  public Measurable<Pressure> getPressure() throws IOException {
    return Measure.valueOf(0, SI.PASCAL);
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
