package me.jbuelow.rov.dry.exception;

import me.jbuelow.rov.common.exception.ROVException;

public class JinputNativesNotFoundException extends ROVException {
  public JinputNativesNotFoundException() {
    super("Could not find jinput natives in any subdirectory or in the $PATH var.");
  }
}
