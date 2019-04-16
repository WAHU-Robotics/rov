package me.jbuelow.rov.common.command;

import lombok.Getter;

public class Shutdown extends Command {
    private static final long serialVersionUID = 4207802681924667139L;

    @Getter
    private Option option;

    public Shutdown() {
        this.option = Option.REBOOT;
    }

    public Shutdown(Option option) {
        this.option = option;
    }

    public enum Option {
        SOFT_REBOOT, //Exits wet side software. Its considered a reboot because the OS on wet side should be set up to restart software on exit
        REBOOT, //Reboots wet side OS
        POWEROFF //Shuts down wet side OS
    }
}
