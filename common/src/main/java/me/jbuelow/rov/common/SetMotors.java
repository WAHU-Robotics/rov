/**
 *
 */
package me.jbuelow.rov.common;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SetMotors extends Command {

  private static final long serialVersionUID = -8856009547284313004L;

  private List<MotorPower> powerLevels;
}
