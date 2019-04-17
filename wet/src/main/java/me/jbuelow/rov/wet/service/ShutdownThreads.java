package me.jbuelow.rov.wet.service;

import java.io.Serializable;

public interface ShutdownThreads extends Serializable {
  Thread getSoftRebootThread();
  Thread getRebootThread();
  Thread getShutdownThread();
}
