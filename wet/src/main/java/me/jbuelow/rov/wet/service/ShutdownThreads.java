package me.jbuelow.rov.wet.service;

public interface ShutdownThreads {
  Thread getSoftRebootThread();
  Thread getRebootThread();
  Thread getShutdownThread();
}
