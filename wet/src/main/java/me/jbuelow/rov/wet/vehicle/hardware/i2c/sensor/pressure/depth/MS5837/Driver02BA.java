package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837;

import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837.Constants.ADC_READ;
import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837.Constants.CONVERT_D1_4096;
import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837.Constants.CONVERT_D2_4096;
import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837.Constants.DEFAULT_ADDRESS;
import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837.Constants.PROM_READ_START;
import static me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837.Constants.RESET;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Pressure;
import javax.measure.quantity.Temperature;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.DepthDevice;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

@Slf4j
@Component
@Profile("useDepth")
public class Driver02BA implements DepthDevice {

  private final I2CDevice device;

  @Getter
  private final int address;

  @Getter
  private final int bus;

  private byte[] prom;
  private int[] calibration;

  private Quantity<Pressure> zeroPoint = Quantities.getQuantity(101325, Units.PASCAL);
  private Quantity<Length> zeroOffset = Quantities.getQuantity(0, Units.METRE);

  public Driver02BA() throws IOException, UnsupportedBusNumberException {
    this(I2CBus.BUS_1, DEFAULT_ADDRESS);
  }

  public Driver02BA(int address, int bus) throws IOException, UnsupportedBusNumberException {
    device = I2CFactory.getInstance(bus).getDevice(address);
    this.address = address;
    this.bus = bus;
  }

  @PostConstruct
  public void initialize() throws IOException {
    log.debug("Initializing MS5837-02BA Driver...");
    write(RESET); //Reset sequence

    sleep(100);

    //Read the 112 bit PROM into a byte array
    log.trace("Reading PROM");
    prom = new byte[14];
    for (byte i = 0; i < 7; i++) {
      device.read(i + PROM_READ_START, prom, i*2, 2);
    }
    
    //Convert raw PROM values to calibration values
    log.trace("Converting PROM");
    calibration = new int[7];
    for (int i = 0; i < 7; i++) {
      calibration[i] = 
          (prom[i*2] << 8) & 0x0000FF00 |
              prom[(i*2)+1] & 0x000000FF;
    }
    
    log.debug("Calibration values: {}", calibration);
  }

  @Override
  public Quantity<Length> getDepth() throws IOException {
    Quantity<Pressure> pressure = getPressure();

    Quantity<Pressure> deltaP = pressure.subtract(zeroPoint);

    double mms = deltaP.to(Units.PASCAL).getValue().doubleValue() / 10000;

    Quantity<Length> distanceToZero = Quantities.getQuantity(mms, Units.METRE);

    return distanceToZero.subtract(zeroOffset);
  }

  @Override
  public void setZero() {
    setZero(Quantities.getQuantity(0, Units.METRE));
  }

  @Override
  public void setZero(Quantity<Length> offset) {
    try {
      zeroPoint = getPressure();
      zeroOffset = offset;
    } catch (IOException e) {
      log.error("Exception getting pressure: ", e);
    }
  }

  @Override
  public Quantity<Pressure> getPressure() throws IOException {
    log.debug("Getting pressure...");

    //Start T conversion
    log.trace("Starting T conversion");
    write(CONVERT_D2_4096);

    sleep(10);

    //Read adcT data
    log.trace("Reading adcT data");
    byte[] rawTData = new byte[3];
    device.read(ADC_READ, rawTData, 0, 3);
    int adcT = calculateADC(rawTData);
    log.trace("Raw adcT value: {}", adcT);

    //Start P conversion
    log.trace("Starting P conversion");
    write(CONVERT_D1_4096);

    sleep(10);

    //Read adcP data
    log.trace("Reading adcP data");
    byte[] rawPData = new byte[3];
    device.read(ADC_READ, rawPData, 0, 3);
    int adcP = calculateADC(rawPData);
    log.trace("Raw adcP value: {}", adcP);

    //Calculate difference between reference temp (Tref) and adc temp
    int dT = adcT - (calibration[4] * (2^8));

    //Calculate actual temperature (100/1 degree celsius)
    int temp = 2000 + dT * calibration[5] / (2^23);

    //Calculate offset at actual temperature (OFF)
    double off = (double) calibration[1] * (2^17) + ((double) calibration[3] * dT) / (2^6);

    //Calculate sensitivity at actual temperature (SENS)
    double sens = (double) calibration[0] * (2^16) + ((double) calibration[2] * dT) / (2^7);

    double pressure = (adcP * sens / (2^21) - off) / (2^15);
    
    log.trace("Pressure calculation debug info:");
    log.trace("C1   = {}", calibration[0]);
    log.trace("C2   = {}", calibration[1]);
    log.trace("C3   = {}", calibration[2]);
    log.trace("C4   = {}", calibration[3]);
    log.trace("C5   = {}", calibration[4]);
    log.trace("C6   = {}", calibration[5]);
    log.trace("D1   = {}", adcP);
    log.trace("D2   = {}", adcT);
    log.trace("dT   = {}", dT);
    log.trace("TEMP = {}", temp);
    log.trace("OFF  = {}", off);
    log.trace("SENS = {}", sens);
    log.trace("P    = {}", pressure);

    //Convert primitive numbers to Quantity objects for unitless data transfer
    Quantity<Pressure> pressureQ = Quantities.getQuantity(pressure, Units.PASCAL);
    log.debug("Pressure was measured at {}", pressureQ);
    return pressureQ;
  }

  public Quantity<Temperature> getTemperature() throws IOException {
    log.debug("Getting temperature...");

    //Start T conversion
    log.trace("Starting T conversion");
    write(CONVERT_D2_4096);

    sleep(10);

    //Read adcT data
    log.trace("Reading adcT data");
    byte[] rawTData = new byte[3];
    device.read(ADC_READ, rawTData, 0, 3);
    int adcT = calculateADC(rawTData);
    log.trace("Raw adcT value: {}", adcT);


    //Calculate difference between reference temp (Tref) and adc temp
    int dT = adcT - (calibration[4] * (2^8));

    //Calculate actual temperature (100/1 degree celsius)
    int temp = 2000 + dT * calibration[5] / (2^23);

    //Convert primitive numbers to Quantity objects for unitless data transfer
    Quantity<Temperature> temperatureQ = Quantities.getQuantity((((double)temp)/100), Units.CELSIUS);
    log.debug("Temperature was measured at {}", temperatureQ);
    return temperatureQ;
  }

  private int calculateADC(byte[] rawData) {
    //Convert triple byte array to an int
    //Required step as java does not have unsigned bytes
    return (rawData[0]<<16)&0x00ff0000|
              (rawData[1]<< 8)&0x0000ff00|
              (rawData[2]) & 0x000000ff;
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      // Don't care
    }
  }

  private void write(int address, byte b) throws IOException {
    device.write(address, b);
  }

  private void write(byte b) throws IOException {
    device.write(b);
  }

  private int read(int address) throws IOException {
    return device.read(address);
  }

}
