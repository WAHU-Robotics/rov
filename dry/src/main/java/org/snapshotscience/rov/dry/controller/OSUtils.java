package org.snapshotscience.rov.dry.controller;

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

/**
 * Used to compute the currently running operating system
 * Uses OSType enum
 *
 * @see OSType
 */
public abstract class OSUtils {
  private static String osName = System.getProperty("os.name").toLowerCase();

  public static OSType getOsType() {
      if (isWindows()) {
          return OSType.WINDOWS;
      }
      
      if (isMac()) {
          return OSType.MAC;
      }
      
      if (isLinux()) {
          return OSType.LINUX;
      }
      
      return OSType.UNKNOWN;
  }

  public static boolean isWindows() {
      return (osName.indexOf("win") >= 0);
  }

  public static boolean isMac() {
      return (osName.indexOf("mac") >= 0);
  }

  public static boolean isLinux() {
      return (osName.indexOf("nux") >= 0);
  }
  
  protected static void setOsName(String newOsName) {
      osName = newOsName;
  }
}
