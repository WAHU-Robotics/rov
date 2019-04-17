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

public class ControlMapper {

  private PolledValues joyA;
  private PolledValues joyB;

  public ControlMapper(PolledValues joyA, PolledValues joyB) {
    this.joyA = joyA;
    this.joyB = joyB;
  }

  public int getAxis(JoystickAxis axis) {
    switch (axis) {
      case PRIMARY_X: return joyA.x;
      case PRIMARY_Y: return joyA.y;
      case PRIMARY_Z: return joyA.z;
      case PRIMARY_T: return joyA.t;
      case SECONDARY_X: return joyB.x;
      case SECONDARY_Y: return joyB.y;
      case SECONDARY_Z: return joyB.z;
      case SECONDARY_T: return joyB.t;
      default: return 0;
    }
  }

  public boolean getButton(JoystickButton button) {
    switch (button) {
      case PRIMARY_0: return joyA.buttons[0];
      case PRIMARY_1: return joyA.buttons[1];
      case PRIMARY_2: return joyA.buttons[2];
      case PRIMARY_3: return joyA.buttons[3];
      case PRIMARY_4: return joyA.buttons[4];
      case PRIMARY_5: return joyA.buttons[5];
      case PRIMARY_6: return joyA.buttons[6];
      case PRIMARY_7: return joyA.buttons[7];
      case PRIMARY_8: return joyA.buttons[8];
      case PRIMARY_9: return joyA.buttons[9];
      case SECONDARY_0: return joyB.buttons[0];
      case SECONDARY_1: return joyB.buttons[1];
      case SECONDARY_2: return joyB.buttons[2];
      case SECONDARY_3: return joyB.buttons[3];
      case SECONDARY_4: return joyB.buttons[4];
      case SECONDARY_5: return joyB.buttons[5];
      case SECONDARY_6: return joyB.buttons[6];
      case SECONDARY_7: return joyB.buttons[7];
      case SECONDARY_8: return joyB.buttons[8];
      case SECONDARY_9: return joyB.buttons[9];
      default: return false;
    }
  }

  public static boolean wasButtonPress(ControlMapper newJoy, ControlMapper oldJoy, JoystickButton button) {
    return !newJoy.getButton(button) && oldJoy.getButton(button);
  }
}