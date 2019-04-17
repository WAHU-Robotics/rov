/**
 *
 */
package me.jbuelow.rov.dry.discovery;

import java.net.InetAddress;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Getter
public class VehicleDiscoveryEvent extends ApplicationEvent {

  /**
   *
   */
  private static final long serialVersionUID = -4288932705637545744L;

  private InetAddress vehicleAddress;
  private UUID vehicleID;

  public VehicleDiscoveryEvent(Object source, InetAddress vehicleAddress) {
    super(source);
    this.vehicleAddress = vehicleAddress;
  }

  public void setVehicleID(UUID vehicleID) {
    this.vehicleID = vehicleID;
  }

}
