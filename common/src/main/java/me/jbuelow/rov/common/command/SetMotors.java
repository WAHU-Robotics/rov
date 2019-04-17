/**
 *
 */
package me.jbuelow.rov.common.command;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jbuelow.rov.common.object.MotorPower;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SetMotors extends Command {

  private static final long serialVersionUID = -8856009547284313004L;

  private List<MotorPower> powerLevels = new ArrayList<>();
}
