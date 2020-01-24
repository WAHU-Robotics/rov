package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055;

import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055.constant.Registers.*;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;
import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Angle;
import javax.measure.quantity.AngularVelocity;
import javax.measure.quantity.MagneticFluxDensity;
import javax.measure.quantity.Temperature;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.object.imu.Orientation;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055.constant.AccRange;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055.constant.OperationModes;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055.constant.PowerModes;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.ImuDevice;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

@Slf4j
@Component
@Profile("useIMU")
public class BNO055 implements ImuDevice {

  @Getter
  private final int bus;

  @Getter
  private final int address;

  private I2CDevice device;

  private static int DEFAULT_ADDRESS = 0x28;

  public BNO055() {
    this(I2CBus.BUS_1, DEFAULT_ADDRESS);
  }

  public BNO055(int bus, int address) {
    this.bus = bus;
    this.address = address;
  }

  @Override
  public void initialize() throws IOException, UnsupportedBusNumberException {
    device = I2CFactory.getInstance(bus).getDevice(address);

    device.write(OPR_MODE, OperationModes.CONFIG_MODE); //Enter config mode
    device.write(PWR_MODE, PowerModes.NORMAL); //Normal power mode
    device.write(UNIT_SEL, (byte) 0b10000000); //Select units
    device.write(ACC_Config, AccRange.G4); //Set accelerometer range
    device.write(OPR_MODE, OperationModes.NDOF); //Set operation mode
  }

  /**
   * Connects and checks if the device located at the target bus and address is a BNO055 chip
   * @return true if the device is identified as correct chip and active
   */
  public boolean isPresent() {
    try {
      device = I2CFactory.getInstance(bus).getDevice(address);
    } catch (IOException | UnsupportedBusNumberException e) {
      log.error("Exception while initializing i2c: ", e);
      device = null;
      return false;
    }
    try {
      int read = device.read(CHIP_ID);
      return read == 0xA0;
    } catch (IOException e) {
      log.error("Exception while attempting to identify device: ", e);
      device = null;
      return false;
    }
  }


  @Override
  public Vector<Quantity<Acceleration>> getAccelerationVector() {
    throw new NotImplementedException();
  }

  @Override
  public Vector<Quantity<Acceleration>> getLinearAccelerationVector() {
    throw new NotImplementedException();
  }

  @Override
  public Vector<Quantity<Acceleration>> getGravityVector() {
    throw new NotImplementedException();
  }

  @Override
  public Orientation getAbsoluteRotation() throws IOException {

    byte[] pitchData = new byte[2];
    device.read(EUL_Pitch_MSB, pitchData, 0, 1);
    device.read(EUL_Pitch_LSB, pitchData, 1, 1);
    Quantity<Angle> pitch = Quantities.getQuantity((pitchData[0]<<8)&0x0000ff00|pitchData[1]&0x000000ff, Units.DEGREE_ANGLE);

    byte[] rollData = new byte[2];
    device.read(EUL_Roll_MSB, rollData, 0, 1);
    device.read(EUL_Roll_LSB, rollData, 1, 1);
    Quantity<Angle> roll = Quantities.getQuantity((rollData[0]<<8)&0x0000ff00|rollData[1]&0x000000ff, Units.DEGREE_ANGLE);

    byte[] yawData = new byte[2];
    device.read(EUL_Heading_MSB, yawData, 0, 1);
    device.read(EUL_Heading_LSB, yawData, 1, 1);
    Quantity<Angle> yaw = Quantities.getQuantity((yawData[0]<<8)&0x0000ff00|yawData[1]&0x000000ff, Units.DEGREE_ANGLE);

    return new Orientation() {
      @Override
      public Quantity<Angle> getPitch() {
        return pitch;
      }

      @Override
      public Quantity<Angle> getRoll() {
        return roll;
      }

      @Override
      public Quantity<Angle> getYaw() {
        return yaw;
      }
    };
  }

  @Override
  public Vector<AngularVelocity> getAngularVelocity() {
    throw new NotImplementedException();
  }

  @Override
  public Vector<Quantity<MagneticFluxDensity>> getMagneticField() {
    throw new NotImplementedException();
  }

  @Override
  public Quantity<Temperature> getTemperature() {
    throw new NotImplementedException();
  }

}

