package me.jbuelow.rov.wet.service.camera.agent.handler;

import me.jbuelow.rov.wet.service.camera.agent.StreamServerListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class StreamServerExceptionHandler extends SimpleChannelHandler {

  private final StreamServerListener streamServerListener;

  public StreamServerExceptionHandler(StreamServerListener streamServerListener) {
    this.streamServerListener = streamServerListener;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    streamServerListener.onException(e.getChannel(), e.getCause());
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    streamServerListener.onClientConnect(e.getChannel());
    super.channelConnected(ctx, e);
  }

  @Override
  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    streamServerListener.onClientDisconnect(e.getChannel());
    super.channelDisconnected(ctx, e);
  }
}
