package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.temp;

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
import javax.measure.Quantity;
import javax.measure.quantity.Temperature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

@Component
@Profile("!useTemp")
public class MockTemperatureSensor implements TempDevice{
  @Override
  public Quantity<Temperature> getTemp() throws IOException {
    return Quantities.getQuantity(0, Units.KELVIN);
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
