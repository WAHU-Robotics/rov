package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import me.jbuelow.rov.wet.service.ShutdownThreads;

public class ShutdownThreadsImpl implements ShutdownThreads {

  /**
   * This is a really bad idea.
   * Don't do this.
   * This is only to prevent the computer running unit tests from shutting down in the middle of it.
   * If you do anything more that that i won't like you, and I'll send Mr. Wachsmuth to hunt you down.
   * @deprecated very very bad, no use pls.
   */
  @SuppressWarnings("DeprecatedIsStillUsed")
  protected boolean isJUnitTest() {
    return false;
  }

  @Override
  public Thread getSoftRebootThread() {
    return new SoftRebootThread();
  }

  @Override
  public Thread getRebootThread() {
    return new RebootThread();
  }

  @Override
  public Thread getShutdownThread() {
    return new ShutdownThread();
  }

  class BaseShutdownThread extends Thread {
    @SuppressWarnings("deprecation")
    public void run() {
      try {
        sleep(2500); //Pause so we can send back a response
      } catch (InterruptedException e) {
        //eat it
      }
      if (!isJUnitTest()) { //Bask in its glorious stupidity!
        otherStuff();
        System.exit(0);
      }
    }

    void otherStuff() {}
  }

  class SoftRebootThread extends BaseShutdownThread {}

  class RebootThread extends BaseShutdownThread {
    @Override
    void otherStuff() {
      Runtime r = Runtime.getRuntime();
      try {
        r.exec(formCommand(true));
      } catch (IOException e) {
        //oh well
      }
    }
  }

  class ShutdownThread extends BaseShutdownThread {
    @Override
    void otherStuff() {
      Runtime r = Runtime.getRuntime();
      try {
        r.exec(formCommand(false));
      } catch (IOException e) {
        //oh well
      }
    }
  }

  private String formCommand(boolean rebooting) {
    if (System.getProperty("os.name").contains("win")) {
      //windows
      return rebooting ? "shutdown /r" : "shutdown";
    } else if (System.getProperty("os.name").contains("mac")) {
      //Mac OS
      return rebooting ? "reboot" : "shutdown";
    } else if (System.getProperty("os.name").contains("nux")) {
      //linux
      return rebooting ? "reboot" : "poweroff";
    } else {
      //other
      //try commands that have some glimmering hope of working
      return rebooting ? "reboot" : "shutdown";
    }
  }
}
