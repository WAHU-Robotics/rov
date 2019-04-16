package me.jbuelow.rov.wet.service.impl;

import static java.lang.Math.sqrt;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.capabilities.ThrustAxis;
import me.jbuelow.rov.wet.service.MotionService;
import me.jbuelow.rov.wet.service.MotorService;
import me.jbuelow.rov.wet.vehicle.MotorConfig;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import org.springframework.stereotype.Service;

/**
 * Handles vector math associated with converting 6-DoF values to motor power.
 */
@Service
@Slf4j
public class MotionServiceImpl implements MotionService {
  private static final int MAX_POWER_LVL = 1000;
  private static final double MAX_MAGNITUDE = 1.000d;
  private static final List<ThrustAxis> LINEAR_AXES = Arrays.asList(ThrustAxis.SURGE, ThrustAxis.SWAY, ThrustAxis.HEAVE);
  private static final List<ThrustAxis> ANGULAR_AXES = Arrays.asList(ThrustAxis.PITCH, ThrustAxis.ROLL, ThrustAxis.YAW);

  private final MotorService motorService;
  private final List<MotorConfig> motorConfiguration;

  private MotionServiceImpl(MotorService motorService, VehicleConfiguration configuration) {
    this.motorService = motorService;
    this.motorConfiguration = configuration.getMotorConfiguration();
  }

  @Override
  public void setMotion(Map<ThrustAxis, Integer> thrustVectors) {
    log.debug("setMotion: {}", thrustVectors.toString());
    Map<UUID, Integer> powerLevels = new HashMap<>(motorConfiguration.size());

    for (MotorConfig motorConfig : motorConfiguration) {
      int linearPower = getLinearPower(motorConfig, thrustVectors);
      int angularPower = getAngularPower(motorConfig, thrustVectors);

      powerLevels.put(motorConfig.getId(), linearPower + angularPower);
    }

    log.debug("Calculated PowerLevels: {}", powerLevels.toString());

    normalizePowerLevels(powerLevels);

    log.debug("Normalized PowerLevels: {}", powerLevels.toString());

    //Set Motors
    for (Map.Entry<UUID, Integer> entry : powerLevels.entrySet()) {
      try {
	    motorService.setMotorPower(entry.getKey(), entry.getValue());
	  } catch (IOException e) {
	    throw new RuntimeException("Error setting motor power levels.", e);
	  }
    }
  }

  private int getLinearPower(MotorConfig motorConfig, Map<ThrustAxis, Integer> thrustVectors) {
    int powerLevel = 0;

    List<ThrustAxis> axes = getLinearAxes(motorConfig);

    if (axes.size() == 1) {
      powerLevel = getSingleAxisPower(motorConfig, thrustVectors, axes.get(0));
    } else {
      double[] motionVector = getMotionVectors(thrustVectors, axes);
      double[] motorFactorVector = getMotorFactors(motorConfig.getThrustFactors(), axes);

      double magnitude = getMagnitude(motionVector);
      double[] normalizedVector = getNorm(magnitude, motionVector);

      if (magnitude > MAX_MAGNITUDE) {
        magnitude = MAX_MAGNITUDE;
      }

      powerLevel = ((int) (dotProduct(normalizedVector, motorFactorVector) * magnitude * MAX_POWER_LVL));
    }

    return powerLevel;
  }

  /**
   * Calculates the power needed for a motor influencing a single degree of freedom (axis).
   *
   * @param motorConfig motorConfig
   * @param thrustVectors thrustVectors
   * @param thrustAxis thrustAxis
   * @return power
   */
  private int getSingleAxisPower(MotorConfig motorConfig, Map<ThrustAxis, Integer> thrustVectors, ThrustAxis thrustAxis) {
    BigDecimal factor = motorConfig.getThrustFactors().get(thrustAxis);
    Integer magnitude = thrustVectors.get(thrustAxis);

    if (magnitude == null) {
      magnitude = Integer.valueOf(0);
    }
    BigDecimal powerLevel = factor.multiply(new BigDecimal(magnitude.intValue()));

    return powerLevel.setScale(0, RoundingMode.HALF_UP).intValueExact();
  }

  /**
   * Returns the list of Linear degrees of freedom influence by this motor configuration.
   *
   * @param motorConfig motorConfig
   * @return List of Thrust Axis
   */
  private List<ThrustAxis> getLinearAxes(MotorConfig motorConfig) {
    List<ThrustAxis> axes = new ArrayList<>(3);

    for (ThrustAxis axis : motorConfig.getThrustFactors().keySet()) {
      if (LINEAR_AXES.contains(axis)) {
        axes.add(axis);
      }
    }

    return axes;
  }

  /**
   * Returns the requested vector needed to be produced by our single motor
   *
   * @param thrustVectors thrustVectors
   * @param motorAxes motorAxes
   * @return array of double for motion vectors
   */
  private double[] getMotionVectors(Map<ThrustAxis, Integer> thrustVectors, List<ThrustAxis> motorAxes) {
    double[] motionVector = new double[motorAxes.size()];

    for (int index=0; index < motorAxes.size(); index++) {
      ThrustAxis axis = motorAxes.get(index);

      if (thrustVectors.containsKey(axis)) {
        motionVector[index] = (double) thrustVectors.get(axis) / (double) MAX_POWER_LVL;
      } else {
        motionVector[index] = 0d;
      }
    }

    return motionVector;
  }

  /**
   * Returns the vector representing the thrust produced by the fixed motor.
   *
   * @param thrustFactors thrustFactors
   * @param motorAxes motorAxes
   * @return array of double for motion vectors
   */
  private double[] getMotorFactors(Map<ThrustAxis, BigDecimal> thrustFactors, List<ThrustAxis> motorAxes) {
    double[] factorVector = new double[motorAxes.size()];

    for (int index=0; index < motorAxes.size(); index++) {
      ThrustAxis axis = motorAxes.get(index);

      if (thrustFactors.containsKey(axis)) {
        factorVector[index] = thrustFactors.get(axis).doubleValue();
      } else {
        factorVector[index] = 0d;
      }
    }

    return factorVector;
  }

  /**
   * Returns a list of all applicable rotational degreed of freedom (axes) influenced by a given motor configuration.
   *
   * @param motorConfig motorConfig
   * @return List of ThrustAxis
   */
  private List<ThrustAxis> getAngularAxes(MotorConfig motorConfig) {
    List<ThrustAxis> axes = new ArrayList<>(3);

    for (ThrustAxis axis : motorConfig.getThrustFactors().keySet()) {
      if (ANGULAR_AXES.contains(axis)) {
        axes.add(axis);
      }
    }

    return axes;
  }

  /**
   * Calculated the amount of power needed to be applied to a single motor to fulfill a requested
   * rotational thrust.
   *
   * @param motorConfig motorConfig
   * @param thrustVectors thrustVectors
   * @return power
   */
  private int getAngularPower(MotorConfig motorConfig, Map<ThrustAxis, Integer> thrustVectors) {
    List<ThrustAxis> axes = getAngularAxes(motorConfig);
    //noinspection UnusedAssignment
    int powerLevel = 0;
    if (axes.size() == 1) {
      powerLevel = getSingleAxisPower(motorConfig, thrustVectors, axes.get(0));
    } else {

      double[] motionVector = getMotionVectors(thrustVectors, axes);
      double[] motorFactorVector = getMotorFactors(motorConfig.getThrustFactors(), axes);

      double magnitude = getMagnitude(motionVector);
      double[] normalizedVector = getNorm(magnitude, motionVector);

      if (magnitude > MAX_MAGNITUDE) {
        magnitude = MAX_MAGNITUDE;
      }

      powerLevel = ((int) (dotProduct(normalizedVector, motorFactorVector) * magnitude * MAX_POWER_LVL));
    }

    return powerLevel;
  }

  /**
   * Calculate the "dotProduct" between the two given vectors
   *
   * @param vectorOne array of double representing a vector
   * @param vectorTwo array of double representing a vector
   * @return dot product result
   */
  private double dotProduct(double[] vectorOne, double[] vectorTwo) {
    assert(vectorOne.length == vectorTwo.length);

    double dotProduct = 0d;

    for (int index=0; index < vectorOne.length; index++) {
      dotProduct = dotProduct + (vectorOne[index] * vectorTwo[index]);
    }

    return dotProduct;
  }

  /**
   * Calculate the vector Norm (magitude) of the given vector
   *
   * @param vector input double array vector
   * @return output magnitude
   */
  private double getMagnitude(double[] vector) {
    double magnitude = 0d;

    for (int index=0; index < vector.length; index++) {
      magnitude = magnitude + (vector[index] * vector[index]);
    }

    return sqrt(magnitude);
  }

  /**
   * Normalize the power levels across all motors to not exceed the max allowed power.
   *
   * @param powerLevels powerLevels to normalize
   */
  private void normalizePowerLevels(Map<UUID, Integer> powerLevels) {
    int maxPower = 0;

    //Find the maximum amount of power requested accross all motors
    for (Integer powerLevel : powerLevels.values()) {
      int powerOffset = Math.abs(powerLevel);

      if (powerOffset > maxPower) {
        maxPower = powerOffset;
      }
    }

    if (maxPower > MAX_POWER_LVL) {
      double powerAdjustmentFactor = (double) MAX_POWER_LVL / (double) maxPower;
      for (Map.Entry<UUID, Integer> powerEntry : powerLevels.entrySet()) {
        int adjustedPower = ((int) (powerEntry.getValue() * powerAdjustmentFactor));
        powerEntry.setValue(adjustedPower);
      }
    }
  }

  /**
   * @param magnitude input magnitude
   * @param vector input vector
   * @return output vector
   */
  private double[] getNorm(double magnitude, double[] vector) {
    double[] result = new double[vector.length];

    for (int index=0; index < vector.length; index++) {
      if (magnitude != 0d) {
        result[index] = vector[index] / magnitude;
      } else {
        result[index] = 0d;
      }
    }

    return result;
  }
}
