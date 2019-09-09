package me.jbuelow.rov.common.codec;

import me.jbuelow.rov.common.codec.StreamListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class StreamExceptionHandler extends SimpleChannelHandler {

  private final StreamListener streamListener;

  public StreamExceptionHandler(StreamListener streamListener) {
    this.streamListener = streamListener;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    streamListener.onException(e.getChannel(), e.getCause());
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    streamListener.onConnect(e.getChannel());
    super.channelConnected(ctx, e);
  }

  @Override
  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    streamListener.onDisconnect(e.getChannel());
    super.channelDisconnected(ctx, e);
  }
}
