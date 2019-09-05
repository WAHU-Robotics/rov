package me.jbuelow.rov.wet.service.camera.agent;

import org.jboss.netty.channel.Channel;

public interface StreamServerListener {
  void onClientConnect(Channel channel);
  void onClientDisconnect(Channel channel);
  void onException(Channel channel, Throwable throwable);
}
