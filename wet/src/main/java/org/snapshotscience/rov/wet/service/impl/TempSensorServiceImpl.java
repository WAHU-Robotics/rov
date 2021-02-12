package org.snapshotscience.rov.wet.service.impl;

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

import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;
import lombok.extern.slf4j.Slf4j;
import org.snapshotscience.rov.wet.service.TempSensorService;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.sensor.temp.TempDevice;
import org.springframework.stereotype.Service;

/**
 * Handles servos as a service.
 */
@Service
@Slf4j
public class TempSensorServiceImpl implements TempSensorService {

  private TempDevice tempDevice;

  public TempSensorServiceImpl(TempDevice tempDevice) {
    this.tempDevice = tempDevice;
  }

  @Override
  public Measurable<Temperature> getTemp() {
    try {
      return tempDevice.getTemp();
    } catch (IOException e) {
      return Measure.valueOf(0, SI.KELVIN);
    }
  }
}
