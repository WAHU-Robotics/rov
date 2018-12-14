/**
 *
 */
package me.jbuelow.rov.wet.vehicle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jbuelow.rov.common.capabilities.Motor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MotorConfig extends Motor implements ActuatorConfig {

  private static final long serialVersionUID = -771657360105867278L;

  @NotBlank
  private int pwmPort;
}
