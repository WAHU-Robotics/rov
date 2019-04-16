package me.jbuelow.rov.common;

import java.nio.charset.Charset;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public abstract class RovConstants {

  public static final int DISCOVERY_BCAST_PORT = 4444;
  public static final Charset CHARSET = Charset.forName("UTF-8");
  public static final byte[] DISCOVERY_BYTES = "YEET".getBytes(CHARSET);
  public static final int DISCOVERY_PACKET_SIZE = DISCOVERY_BYTES.length;
  public static final int ROV_PORT = 8888;
}
