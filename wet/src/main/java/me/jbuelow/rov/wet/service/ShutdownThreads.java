package me.jbuelow.rov.wet.service;

public interface ShutdownThreads {
  public Thread getSoftRebootThread();
  public Thread getRebootThread();
  public Thread getShutdownThread();
}
