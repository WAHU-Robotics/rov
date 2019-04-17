package me.jbuelow.rov.wet.service.impl;

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

import java.util.HashSet;
import java.util.Set;
import me.jbuelow.rov.common.capabilities.MotionCapabilities;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.common.command.GetCapabilities;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.VehicleCapabilities;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.vehicle.AccessoryConfig;
import me.jbuelow.rov.wet.vehicle.CapabilityFactory;
import me.jbuelow.rov.wet.vehicle.MotorConfig;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class GetCapabilitiesHandler implements CommandHandler<GetCapabilities> {

  private VehicleConfiguration vehicleConfiguration;
  private VehicleCapabilities capabilities = null;

  public GetCapabilitiesHandler(VehicleConfiguration vehicleConfiguration) {
    this.vehicleConfiguration = vehicleConfiguration;
  }

  private VehicleCapabilities getCapabilities() {
    //Lazy load and cache the vehicle capabilities
    if (capabilities == null) {
      capabilities = new VehicleCapabilities();

      capabilities.setId(vehicleConfiguration.getId());
      capabilities.setName(vehicleConfiguration.getName());

      for (AccessoryConfig config : vehicleConfiguration.getAllConfiguration()) {
        capabilities.getCapabilities().add(CapabilityFactory.getCapability(config));
      }

      Set<ThrustAxis> thrustAxes = new HashSet<>();
      for (MotorConfig config : vehicleConfiguration.getMotorConfiguration()) {
        thrustAxes.addAll(config.getThrustFactors().keySet());
      }

      capabilities.getCapabilities().add(new MotionCapabilities(thrustAxes));
    }

    return capabilities;
  }


  /**
   * Handles the GetCapabilities command
   *
   * @param command GetCapabilities instance
   * @return Capabilities instance
   */
  @Override
  public Response execute(GetCapabilities command) {
    VehicleCapabilities response = getCapabilities();
    return response;
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of GetCapabilities
   */
  @Override
  public Class<GetCapabilities> getCommandType() {
    return GetCapabilities.class;
  }
}
