package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import java.io.IOException;
import java.util.Vector;
import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.AngularVelocity;
import javax.measure.quantity.MagneticFluxDensity;
import javax.measure.quantity.Temperature;
import me.jbuelow.rov.common.object.imu.Orientation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!useIMU")
public class MockImuDevice implements ImuDevice {

  @Override
  public void initialize() throws IOException, UnsupportedBusNumberException {

  }

  @Override
  public Vector<Quantity<Acceleration>> getAccelerationVector() {
    return null;
  }

  @Override
  public Vector<Quantity<Acceleration>> getLinearAccelerationVector() {
    return null;
  }

  @Override
  public Vector<Quantity<Acceleration>> getGravityVector() {
    return null;
  }

  @Override
  public Orientation getAbsoluteRotation() throws IOException {
    return null;
  }

  @Override
  public Vector<AngularVelocity> getAngularVelocity() {
    return null;
  }

  @Override
  public Vector<Quantity<MagneticFluxDensity>> getMagneticField() {
    return null;
  }

  @Override
  public Quantity<Temperature> getTemperature() {
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
