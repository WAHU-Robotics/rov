package me.jbuelow.rov.common.codec;

import org.jboss.netty.channel.Channel;

public interface StreamListener {
  void onConnect(Channel channel);
  void onDisconnect(Channel channel);
  void onException(Channel channel, Throwable throwable);
}
