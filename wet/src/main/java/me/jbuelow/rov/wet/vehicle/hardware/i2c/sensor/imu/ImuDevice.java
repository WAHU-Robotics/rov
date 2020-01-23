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
import me.jbuelow.rov.wet.vehicle.hardware.i2c.I2CDevice;

public interface ImuDevice extends I2CDevice {

  void initialize() throws IOException, UnsupportedBusNumberException;

  Vector<Quantity<Acceleration>> getAccelerationVector();

  Vector<Quantity<Acceleration>> getLinearAccelerationVector();

  Vector<Quantity<Acceleration>> getGravityVector();

  Orientation getAbsoluteRotation() throws IOException;

  Vector<AngularVelocity> getAngularVelocity();

  Vector<Quantity<MagneticFluxDensity>> getMagneticField();

  Quantity<Temperature> getTemperature();
}
