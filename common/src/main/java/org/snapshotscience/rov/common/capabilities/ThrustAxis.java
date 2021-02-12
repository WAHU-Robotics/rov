package org.snapshotscience.rov.common.capabilities;

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

public enum ThrustAxis {
  SWAY,      //Left/Right - x axis
  SURGE,     //Forwards/Backwards - y axis
  HEAVE,     //Up/Down - z axis
  PITCH,     //Rotation about the x axis
  ROLL,      //Rotation about the y axis
  YAW        //Rotation about the z axis
}
