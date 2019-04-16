package me.jbuelow.rov.common.command;

import lombok.Getter;

public class Shutdown extends Command {
    private static final long serialVersionUID = 4207802681924667139L;

    @Getter
    private boolean reboot;

    public Shutdown() {
        this.reboot = false;
    }

    public Shutdown(boolean reboot) {
        this.reboot = reboot;
    }
}
