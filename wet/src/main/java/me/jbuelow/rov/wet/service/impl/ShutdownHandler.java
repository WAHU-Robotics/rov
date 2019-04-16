package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.Shutdown;
import me.jbuelow.rov.common.response.Goodbye;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.MotorService;
import me.jbuelow.rov.wet.service.ShutdownThreads;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShutdownHandler implements CommandHandler<Shutdown> {

  ShutdownThreads shutdownThreads;
  private final MotorService motorService;

  public ShutdownHandler(MotorService motorService) {
    this.motorService = motorService;
    setShutdownThreads();
  }

  void setShutdownThreads() {
    this.shutdownThreads = new ShutdownThreadsImpl();
  }

  /**
   * Handles the Shutdown command
   *
   * @param command Shutdown instance
   * @return Goodbye instance
   */
  @Override
  public Response execute(Shutdown command) {
    boolean failsafed = false;
    for (int i = 0; i < 5; i++) {
      try {
        motorService.failsafe();
      } catch (IOException e) {
        continue;
      }
      failsafed = true;
      break;
    }

    Thread shutdownThread;

    switch (command.getOption()) {
      case SOFT_REBOOT:
        shutdownThread = shutdownThreads.getSoftRebootThread();
        break;

      case REBOOT:
      default:
        shutdownThread = shutdownThreads.getRebootThread();
        break;

      case POWEROFF:
        shutdownThread = shutdownThreads.getShutdownThread();
        break;
    }

    shutdownThread.start();
    return new Goodbye(failsafed, shutdownThread);
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of Shutdown
   */
  @Override
  public Class<Shutdown> getCommandType() {
    return Shutdown.class;
  }
}
