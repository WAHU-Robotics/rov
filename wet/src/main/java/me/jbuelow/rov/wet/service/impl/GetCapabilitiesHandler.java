/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.GetCapabilities;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.VehicleCapabilities;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.vehicle.AccessoryConfig;
import me.jbuelow.rov.wet.vehicle.CapabilityFactory;
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
