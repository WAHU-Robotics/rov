package me.jbuelow.rov.common.codec;

import java.net.SocketAddress;

/**
 * Interface for IStreamServerAgent and IStreamClientAgent
 */
public interface IStreamAgent {
  void start(SocketAddress socketAddress);
  void stop();
}
