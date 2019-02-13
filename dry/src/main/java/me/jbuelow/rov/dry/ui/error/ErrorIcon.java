package me.jbuelow.rov.dry.ui.error;

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
