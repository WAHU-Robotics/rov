/**
 * 
 */
package me.jbuelow.rov.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jbuelow.rov.common.capabilities.Capability;

/**
 * @author Brian Wachsmuth
 * 
 * This class is used to respond to the Hello broadcast.
 * 
 * We will respond with our address and our vehicles capabilities.
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VehicleCapabilities implements Response {
  private static final long serialVersionUID = 8772769635819601589L;
  
  private UUID id;
  private String name;
  private List<Capability> capabilities = new ArrayList<>();
}
