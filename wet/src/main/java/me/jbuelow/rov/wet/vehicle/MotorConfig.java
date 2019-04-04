/**
 *
 */
package me.jbuelow.rov.wet.vehicle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jbuelow.rov.common.capabilities.Motor;
import me.jbuelow.rov.common.capabilities.MotorOrientation;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MotorConfig extends Motor implements ActuatorConfig {
  private static final long serialVersionUID = -771657360105867278L;
  private static final List<ThrustAxis> X_AXIS_VALID_THRUSTS = Arrays.asList(ThrustAxis.SURGE, ThrustAxis.YAW);
  private static final List<ThrustAxis> Y_AXIS_VALID_THRUSTS = Arrays.asList(ThrustAxis.SWAY, ThrustAxis.YAW);
  private static final List<ThrustAxis> Z_AXIS_VALID_THRUSTS = Arrays.asList(ThrustAxis.HEAVE, ThrustAxis.PITCH, ThrustAxis.ROLL);
  private static final List<ThrustAxis> MULTI_AXIS_VALID_THRUSTS = Arrays.asList(ThrustAxis.values());
  private static final BigDecimal MAX_FACTOR = new BigDecimal("1.000");
  

  @NotBlank
  private int pwmPort;
  
  private Map<ThrustAxis, BigDecimal> thrustFactors = new HashMap<>(6);  //We have at most six degrees of freedom
  
  public void validateConfigurtion() {
    switch (getMotorOrientation()) {
      case X_AXIS:
        validateFactors(thrustFactors, X_AXIS_VALID_THRUSTS, getMotorOrientation());
        break;
      case Y_AXIS:
        validateFactors(thrustFactors, Y_AXIS_VALID_THRUSTS, getMotorOrientation());
        break;
      case Z_AXIS:
        validateFactors(thrustFactors, Z_AXIS_VALID_THRUSTS, getMotorOrientation());
        break;
      case MULTI_AXIS:
        validateFactors(thrustFactors, MULTI_AXIS_VALID_THRUSTS, getMotorOrientation());
        break;
    }
  }
  
  private void validateFactors(Map<ThrustAxis, BigDecimal> thrustFactors, Collection<ThrustAxis> validAxes, MotorOrientation orientation) {
    for (Map.Entry<ThrustAxis, BigDecimal> entry : thrustFactors.entrySet()) {
      if (!validAxes.contains(entry.getKey())) {
        throw new InvalidThrustAxisException("Motor Configuration Error.  Thrust vector " + entry.getKey() + " is invalid for orientation " + orientation);
      }
      
      if (entry.getValue().abs().compareTo(MAX_FACTOR) > 0) {
        throw new InvalidFactorException("Thrust factor " + entry.getValue() + " is out of range.  Must be between +/- 1.000");
      }
    }
  }
}
