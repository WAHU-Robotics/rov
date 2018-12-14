/**
 *
 */
package me.jbuelow.rov.common.capabilities;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Motor extends AbstractCapability {

  private static final long serialVersionUID = -973957131240688883L;

  private MotorType motorType;
  private MotorOrientation motorOrientation;
}
