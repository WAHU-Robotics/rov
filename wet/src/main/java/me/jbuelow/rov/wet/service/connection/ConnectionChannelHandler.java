package me.jbuelow.rov.wet.service.connection;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.service.connection.event.ControllerConnectedEvent;
import me.jbuelow.rov.wet.service.connection.event.ControllerDisconnectedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConnectionChannelHandler implements ChannelHandler {

  private final ApplicationEventPublisher eventPublisher;

  public ConnectionChannelHandler(
      ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @Override
  public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
    log.info("Controller connection started from {}", channelHandlerContext.channel().remoteAddress());
    eventPublisher.publishEvent(new ControllerConnectedEvent(this));
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
    log.info("Controller disconnected at {}", channelHandlerContext.channel().remoteAddress());
    eventPublisher.publishEvent(new ControllerDisconnectedEvent(this));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable)
      throws Exception {
    log.error("Controller communication exception with client at {}", channelHandlerContext.channel().remoteAddress(), throwable);
    channelHandlerContext.channel().close();
  }
}
