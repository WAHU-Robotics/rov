package me.jbuelow.rov.dry.controller;

import static java.lang.Math.sqrt;

/**
 * Class for calculating dot products of input values
 */
public class VectorMath {

  public static double dotProduct(double[] vecOne, double[] vecTwo) {
    return vecOne[0] * vecTwo[0] + vecOne[1] * vecTwo[1];
  }

  public static double getMag(double[] vec) {
    return sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
  }

  public static double[] getNorm(double mag, double[] vec) {
    double[] result = new double[]{0, 0};
    if (mag != 0f) {
      result[0] = vec[0] / mag;
      result[1] = vec[1] / mag;
    }
    return result;
  }

  public static double[] motorFrontRight = new double[]{-0.707, 0.707};
  public static double[] motorFrontLeft = new double[]{0.707, 0.707};
  public static double[] motorBackRight = new double[]{-0.707, -0.707};
  public static double[] motorBackLeft = new double[]{0.707, -0.707};

}
