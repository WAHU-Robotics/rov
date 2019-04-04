/**
 *
 */
package me.jbuelow.rov.dry.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.Command;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.VehicleCapabilities;
import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import me.jbuelow.rov.dry.exception.JinputNativesNotFoundException;
import me.jbuelow.rov.dry.service.VehicleControlService;
import me.jbuelow.rov.dry.ui.error.GeneralError;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
@Slf4j
public class VehicleControlServiceImpl implements VehicleControlService {

  private Map<UUID, ControllHandler> handlers = new HashMap<>(2);

  /* (non-Javadoc)
   * @see VehicleControlService#activeConnections()
   */
  @Override
  public boolean activeConnections() {
    return handlers.size() > 0;
  }

  /* (non-Javadoc)
   * @see VehicleControlService#sendCommand(int, Command)
   */
  @Override
  public Response sendCommand(UUID vehicleId, Command command) {
    ControllHandler handler = handlers.get(vehicleId);

    if (handler == null) {
      throw new IllegalArgumentException(
          "No vehicle with ID " + vehicleId + " is attatched to the controller.");
    }

    try {
      return handler.sendCommand(command);
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException("Error sending vehicle command.", e);
    }
  }

  /* (non-Javadoc)
   * @see VehicleControlService#getAttatchedVehicles()
   */
  @Override
  public List<VehicleCapabilities> getAttatchedVehicles() {
    List<VehicleCapabilities> capabilities = new ArrayList<>(handlers.size());

    for (ControllHandler handler : handlers.values()) {
      capabilities.add(handler.getVehicleCapabilities());
    }

    return capabilities;
  }

  @EventListener
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public void handleVehicleDiscovery(VehicleDiscoveryEvent event) {
    log.info("Handling vehicle attatchment.");
    try {
      ControllHandler handler = new ControllHandler(event.getVehicleAddress());

      handlers.put(handler.getId(), handler);
      event.setVehicleID(handler.getId());
    } catch (ClassNotFoundException | IOException e) {
      log.error("Error opening communications to vehicle", e);
    } catch (JinputNativesNotFoundException e) {
      GeneralError.display("<html><b>An exception occoured: " + e.getClass().getSimpleName() +
          "</b><br>Ensure that the required natives are either<br>in a subdirectory of the working directory,<br>or add them to $PATH.</html>");
      System.exit(1);
    }
  }
}
