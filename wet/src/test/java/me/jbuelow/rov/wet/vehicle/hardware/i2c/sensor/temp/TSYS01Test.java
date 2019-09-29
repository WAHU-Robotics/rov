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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TSYS01Test {

  @Test
  public void calculateTemperature() {
    List<Integer> k = new ArrayList<>();

    k.add(40781);
    k.add(32791);
    k.add(36016);
    k.add(24926);
    k.add(28446);

    float result = TSYS01.calculateTemperature(9378708, k);
    assertThat(result, is(10.577273f));
  }
}