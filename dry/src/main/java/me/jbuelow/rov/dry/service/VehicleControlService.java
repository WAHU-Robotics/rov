/**
 * 
 */
package me.jbuelow.rov.dry.service;

import java.util.List;
import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.VehicleCapabilities;

/**
 * @author Brian Wachsmuth
 *
 */
public interface VehicleControlService {
  boolean activeConnections();
  
  Response sendCommand(int vehicleId, Command command);
  
  List<VehicleCapabilities> getAttatchedVehicles();
}
