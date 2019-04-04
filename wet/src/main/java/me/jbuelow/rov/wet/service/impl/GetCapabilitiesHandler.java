/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

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
import java.util.HashSet;
import java.util.Set;
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

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(GetCapabilities command) {
    VehicleCapabilities response = getCapabilities();
    return response;
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<GetCapabilities> getCommandType() {
    return GetCapabilities.class;
  }
}
