package me.jbuelow.rov.common.command;

import lombok.Getter;

public class Shutdown extends Command {

  private static final long serialVersionUID = 4207802681924667139L;

  @Getter
  private final Option option;

  public Shutdown() {
    this.option = Option.REBOOT;
  }

  public Shutdown(Option option) {
    this.option = option;
  }

  public enum Option {
    NOTHING("Do Nothing"), //Does nothing
    SOFT_REBOOT(
        "Software Reboot"), //Exits wet side software. Its considered a reboot because the OS on wet side should be set up to restart software on exit
    REBOOT("Reboot"), //Reboots wet side OS
    POWEROFF("Shutdown"); //Shuts down wet side OS
    public String readable;

    Option(String readable) {
      this.readable = readable;
    }

    @Override
    public String toString() {
      return readable;
    }
  }
}
