/**
 *
 */
package me.jbuelow.rov.common.capabilities;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Motor extends AbstractCapability {

  private static final long serialVersionUID = -973957131240688883L;

  public Motor() {
    super();
  }

  public Motor(UUID id) {
    super(id);
  }

  private MotorType motorType;
  private MotorOrientation motorOrientation;
}
