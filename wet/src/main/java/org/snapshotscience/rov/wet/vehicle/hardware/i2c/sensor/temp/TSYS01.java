package org.snapshotscience.rov.wet.vehicle.hardware.i2c.sensor.temp;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("useTemp")
public class TSYS01 implements TempDevice {
  private final int address;
  private final I2CDevice i2cDevice;
  private final int bus;

  private final static int _TSYS01_ADDR = 0x77;
  private final static int _TSYS01_PROM_READ = 0xA0;
  private final static byte _TSYS01_RESET = 0x1E;
  private final static byte _TSYS01_CONVERT = 0x48;
  private final static byte _TSYS01_READ = 0x00;

  List<Integer> k = new ArrayList<>();

  public TSYS01() throws IOException, I2CFactory.UnsupportedBusNumberException {
    this(I2CBus.BUS_1, _TSYS01_ADDR);
  }

  public TSYS01(int bus, int address) throws IOException, I2CFactory.UnsupportedBusNumberException {
    this.bus = bus;
    this.address = address;
    i2cDevice = I2CFactory.getInstance(bus).getDevice(address);
    initialize();
  }

  @Override
  public Measurable<Temperature> getTemp() throws IOException {
    log.debug("Attempting to get temperature...");
    write(_TSYS01_CONVERT);
    sleep(10);
    byte[] data = new byte[3];
    i2cDevice.read(_TSYS01_READ, data, 0, 3);

    StringBuilder sb = new StringBuilder();
    for (byte b : data) {
      sb.append(String.format("%02X ", (int) b & 0xFF));
    }
    log.debug("Read hexadecimal values: " + sb.toString());

    int adc =
      (data[0]<<16)&0x00ff0000| //Convert our 3 unsigned bytes to one big int.
      (data[1]<< 8)&0x0000ff00| //Why does it look so messy and complicated?
          (data[2] << 0) & 0x000000ff; //Because Java! That's why.

    log.debug("Got ADC value: " + adc);

    float tempCelsius = calculateTemperature(adc, k);
    Measurable<Temperature> temp = Measure.valueOf(tempCelsius, SI.CELSIUS);

    log.debug("Calculated temperature to be {}", temp.toString());

    return temp;
  }

  static float calculateTemperature(int adc24, List<Integer> k) {
    double adc16 = adc24 / 256;

    double result =
      (-2)   * k.get(4) * 1e-21 * adc16*adc16*adc16*adc16 +
        4    * k.get(3) * 1e-16 * adc16*adc16*adc16       +
      (-2)   * k.get(2) * 1e-11 * adc16*adc16             +
        1    * k.get(1) * 1e-6  * adc16                   +
      (-1.5) * k.get(0) * 1e-2;

    return (float) result;
  }

  private void initialize() throws IOException {
    log.debug("Initializing TSYS01 module...");
    write(_TSYS01_RESET);
    sleep(10);
    int[] reads = {0xAA, 0xA8, 0xA6, 0xA4, 0xA2, 0xA0};
    for (int prom : reads) {
      byte[] bytes = new byte[2];
      i2cDevice.read(prom, bytes, 0, 2);

      int value =
        (bytes[0]<< 8)&0x0000ff00|
            (bytes[1] << 0) & 0x000000ff;

      k.add(value);
    }
    log.debug("Got calibration values: " + k);
  }

  @Override
  public int getBus() {
    return bus;
  }

  @Override
  public int getAddress() {
    return address;
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      // Don't care
    }
  }

  private void write(int address, byte b) throws IOException {
    i2cDevice.write(address, b);
  }

  private void write(byte b) throws IOException {
    i2cDevice.write(b);
  }

  private int read(int address) throws IOException {
    return i2cDevice.read(address);
  }
}