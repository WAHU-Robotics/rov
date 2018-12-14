/**
 * 
 */
package me.jbuelow.rov.wet.vehicle;

import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jbuelow.rov.common.capabilities.Servo;

/**
 * @author Brian Wachsmuth
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ServoConfig extends Servo implements ActuatorConfig {
  private static final long serialVersionUID = -4496116153451016803L;

  @NotBlank
  private int pwmPort;
}
