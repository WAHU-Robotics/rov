package me.jbuelow.rov.dry.controller;

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

import static java.lang.Math.sqrt;
/**
 * Class for calculating dot products of input values
 *
 * @deprecated no longer used. These calculations have been moved to wet side.
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
