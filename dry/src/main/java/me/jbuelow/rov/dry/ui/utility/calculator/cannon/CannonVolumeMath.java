package me.jbuelow.rov.dry.ui.utility.calculator.cannon;

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

public class CannonVolumeMath {

  public static Double calculate(Double r1, Double r2, Double r3, Double length) {
    double centerCylinderValue = Math.PI * Math.pow(r2, 2d) * length;
    double fullConeLength = length/(1- r1/r3);
    double fullConeVolume = Math.PI * Math.pow(r3, 2d) * fullConeLength / 3;
    double coneTipLength = fullConeLength - length;
    double coneTipVolume = Math.PI * Math.pow(r1, 2d) * coneTipLength / 3;

    double cannonVolume = fullConeVolume - centerCylinderValue - coneTipVolume;
    return cannonVolume;
  }
}
