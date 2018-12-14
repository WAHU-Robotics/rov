/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.GetCapabilities;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.VehicleCapabilities;
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

  public VehicleCapabilities getCapabilities() {
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

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(GetCapabilities command) {
    return getCapabilities();
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<GetCapabilities> getCommandType() {
    return GetCapabilities.class;
  }
}
