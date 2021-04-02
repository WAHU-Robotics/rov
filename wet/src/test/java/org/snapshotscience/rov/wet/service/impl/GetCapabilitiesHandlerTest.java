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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.snapshotscience.rov.common.capabilities.Capability;
import org.snapshotscience.rov.common.command.GetCapabilities;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.common.response.VehicleCapabilities;
import org.snapshotscience.rov.wet.vehicle.AccessoryConfig;
import org.snapshotscience.rov.wet.vehicle.MotorConfig;
import org.snapshotscience.rov.wet.vehicle.ServoConfig;
import org.snapshotscience.rov.wet.vehicle.VehicleConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import(GetCapabilitiesHandler.class)
public class GetCapabilitiesHandlerTest {

  private static final UUID TEST_UUID = UUID.randomUUID();

  @MockBean
  VehicleConfiguration vehicleConfiguration;

  @Autowired
  GetCapabilitiesHandler handler;

  /**
   * Test method for {@link GetCapabilitiesHandler#execute(GetCapabilities)}.
   */
  @Test
  public void testExecute() {
    when(vehicleConfiguration.getId()).thenReturn(TEST_UUID);
    when(vehicleConfiguration.getName()).thenReturn("Test Vehicle");

    List<AccessoryConfig> accessories = new ArrayList<>();
    accessories.add(new MotorConfig());
    accessories.add(new ServoConfig());

    when(vehicleConfiguration.getAllConfiguration()).thenReturn(accessories);

    Response response = handler.execute(new GetCapabilities());

    assertThat(response, instanceOf(VehicleCapabilities.class));

    VehicleCapabilities vehicleCapabilities = (VehicleCapabilities) response;
    assertThat(vehicleCapabilities.getId(), is(TEST_UUID));
    assertThat(vehicleCapabilities.getName(), is("Test Vehicle"));

    List<Capability> capabilities = vehicleCapabilities.getCapabilities();
    assertThat(capabilities, instanceOf(ArrayList.class));
    assertThat(capabilities.size(), is(3));
  }

}
