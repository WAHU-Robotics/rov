package me.jbuelow.rov.wet.service.connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ObjectDecoder;
import org.springframework.stereotype.Component;

@Component
public class ConnectionPipelineInitializer extends ChannelInitializer<Channel> {

  private final CommandClassResolver resolver;
  private final ConnectionChannelHandler processorService;

  public ConnectionPipelineInitializer(
      CommandClassResolver resolver,
      ConnectionChannelHandler processorService) {
    this.resolver = resolver;
    this.processorService = processorService;
  }

  @Override
  protected void initChannel(Channel channel) throws Exception {
    ChannelPipeline pipeline = channel.pipeline();
    pipeline.addLast(new ObjectDecoder(resolver));
    pipeline.addLast(processorService);
  }

}
