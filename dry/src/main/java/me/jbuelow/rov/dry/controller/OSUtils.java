package me.jbuelow.rov.dry.controller;

/**
 * Used to compute the currently running operating system
 * Uses OSType enum
 *
 * @see me.jbuelow.rov.dry.controller.OSType
 */
abstract class OSUtils {
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

  private static boolean isWindows() {
      return (osName.indexOf("win") >= 0);
  }

  private static boolean isMac() {
      return (osName.indexOf("mac") >= 0);
  }

  private static boolean isLinux() {
      return (osName.indexOf("nux") >= 0);
  }
  
  protected static void setOsName(String newOsName) {
      osName = newOsName;
  }
}
