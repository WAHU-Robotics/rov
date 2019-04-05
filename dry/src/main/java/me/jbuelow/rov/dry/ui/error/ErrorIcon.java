package me.jbuelow.rov.dry.ui.error;

/**
 * Enumerator for icons used in GeneralError
 *
 * @see me.jbuelow.rov.dry.ui.error.GeneralError
 */
public enum ErrorIcon {
  GENERAL("error.png"),
  JOY_OK("joystick-icon.png"),
  JOY_ERROR("joystick-connection-error.png");


  private String filename;

  ErrorIcon(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
