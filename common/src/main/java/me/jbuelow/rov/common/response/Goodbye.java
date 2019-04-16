package me.jbuelow.rov.common.response;

import lombok.Getter;

public class Goodbye extends Response {
  private static final long serialVersionUID = -6435681174573067030L;

  @Getter
  private final boolean safe;

  @Getter
  private final Thread thread;

  public Goodbye() {
    this(false);
  }

  public Goodbye(boolean safe) {
    this(safe, null);
  }

  public Goodbye(Thread thread) {
    this(false, thread);
  }

  public Goodbye(boolean safe, Thread thread) {
    this.safe = safe;
    this.thread = thread;
  }
}
