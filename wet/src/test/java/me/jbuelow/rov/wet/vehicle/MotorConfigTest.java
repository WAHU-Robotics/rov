package me.jbuelow.rov.wet.vehicle;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.math.BigDecimal;
import me.jbuelow.rov.common.capabilities.MotorOrientation;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.wet.exception.InvalidFactorException;
import me.jbuelow.rov.wet.exception.InvalidThrustAxisException;
import org.junit.Test;

public class MotorConfigTest {

  @Test
  public void testValidateConfigurtion_X_AxisHappyPath() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected= InvalidThrustAxisException.class)
  public void testValidateConfigurtion_X_AxisNoSway() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SWAY, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_X_AxisNoHeave() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.HEAVE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_X_AxisNoPitch() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.PITCH, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_X_AxisNoRoll() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.ROLL, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test
  public void testValidateConfigurtion_Y_AxisHappyPath() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Y_AXIS);
    config.getThrustFactors().put(ThrustAxis.SWAY, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Y_AxisNoSurge() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Y_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Y_AxisNoHeave() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Y_AXIS);
    config.getThrustFactors().put(ThrustAxis.HEAVE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Y_AxisNoPitch() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SWAY, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.PITCH, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Y_AxisNoRoll() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Y_AXIS);
    config.getThrustFactors().put(ThrustAxis.SWAY, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.ROLL, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test
  public void testValidateConfigurtion_Z_AxisHappyPath() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Z_AXIS);
    config.getThrustFactors().put(ThrustAxis.HEAVE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.PITCH, new BigDecimal("-1.000"));
    config.getThrustFactors().put(ThrustAxis.ROLL, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Z_AxisNoSurge() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Z_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.PITCH, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Z_AxisNoSway() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Z_AXIS);
    config.getThrustFactors().put(ThrustAxis.SWAY, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.ROLL, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
  public void testValidateConfigurtion_Z_AxisNoYaw() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Z_AXIS);
    config.getThrustFactors().put(ThrustAxis.HEAVE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected= InvalidFactorException.class)
  public void testValidateConfigurtion_FactorOutOfRange() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.0001"));
    
    config.validateConfigurtion();
  }

}
