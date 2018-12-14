/**
 * 
 */
package me.jbuelow.rov.dry.discovery;

import java.net.InetAddress;
import org.springframework.context.ApplicationEvent;
import lombok.Getter;

/**
 * @author Brian Wachsmuth
 *
 */
@Getter
public class VehicleDiscoveryEvent extends ApplicationEvent {
  private InetAddress vehicleAddress;
  
  public VehicleDiscoveryEvent(Object source, InetAddress vehicleAddress) {
    super(source);
    this.vehicleAddress = vehicleAddress;
  }

}
