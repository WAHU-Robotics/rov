package me.jbuelow.rov.common.codec;

import java.awt.image.BufferedImage;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public abstract class VideoStreamEncoder extends OneToOneEncoder implements VideoStreamCodec {

  @Override
  protected Object encode(ChannelHandlerContext ctx, Channel channel, Object o) {
    return this.encode((BufferedImage) o);
  }

  public abstract Object encode(BufferedImage img);

}
