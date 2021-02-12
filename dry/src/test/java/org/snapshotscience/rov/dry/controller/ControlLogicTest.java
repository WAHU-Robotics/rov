package org.snapshotscience.rov.dry.controller;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class ControlLogicTest {

  @Test
  public void testComputeDeadzonesZero() {
    PolledValues inVals = new PolledValues(6, -4, 8, 3, 0f);
    Object polledValues = ControlLogic.computeDeadzones(inVals, 10);

    assertThat(polledValues, instanceOf(PolledValues.class));
    PolledValues polledValues1 = (PolledValues) polledValues;
    assertThat(polledValues1.x, equalTo(0));
    assertThat(polledValues1.y, equalTo(0));
    assertThat(polledValues1.z, equalTo(0));
    assertThat(polledValues1.t, equalTo(3));
  }

  @Test
  public void testComputeDeadzonesOut() {
    PolledValues inVals = new PolledValues(100, 200, -200, -100, 0f);
    Object polledValues = ControlLogic.computeDeadzones(inVals, 10);

    assertThat(polledValues, instanceOf(PolledValues.class));
    PolledValues polledValues1 = (PolledValues) polledValues;
    assertThat(polledValues1.x, equalTo(100));
    assertThat(polledValues1.y, equalTo(200));
    assertThat(polledValues1.z, equalTo(-200));
    assertThat(polledValues1.t, equalTo(-100));
  }

  @Test
  public void genMotorValues() {
    //TODO Generate test
  }

  @Test
  public void computeNonLinearity() {
    PolledValues inVals = new PolledValues(100, 200, -200, -100, 0f);
    Object polledValues = ControlLogic.computeNonLinearity(inVals);

    assertThat(polledValues, instanceOf(PolledValues.class));
    PolledValues polledValues1 = (PolledValues) polledValues;
    assertThat(polledValues1.x, equalTo(10));
    assertThat(polledValues1.y, equalTo(40));
    assertThat(polledValues1.z, equalTo(-40));
    assertThat(polledValues1.t, equalTo(-100));
    assertThat(polledValues1.hat, equalTo(0f));
  }
}
