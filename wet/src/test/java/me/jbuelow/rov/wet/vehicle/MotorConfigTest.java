package me.jbuelow.rov.wet.vehicle;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import org.junit.Test;
import me.jbuelow.rov.common.capabilities.MotorOrientation;
import me.jbuelow.rov.common.capabilities.ThrustAxis;

public class MotorConfigTest {

  @Test
  public void testValidateConfigurtion_X_AxisHappyPath() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.000"));
    config.getThrustFactors().put(ThrustAxis.YAW, new BigDecimal("-1.000"));
    
    config.validateConfigurtion();
  }

  @Test(expected=InvalidThrustAxisException.class)
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

  @Test(expected=InvalidFactorException.class)
  public void testValidateConfigurtion_FactorOutOfRange() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.X_AXIS);
    config.getThrustFactors().put(ThrustAxis.SURGE, new BigDecimal("1.0001"));
    
    config.validateConfigurtion();
  }

}
