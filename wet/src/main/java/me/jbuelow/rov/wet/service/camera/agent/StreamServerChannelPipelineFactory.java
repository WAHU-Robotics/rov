package me.jbuelow.rov.wet.service.camera.agent;

import java.awt.Dimension;
import me.jbuelow.rov.wet.service.camera.agent.handler.StreamServerExceptionHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

public class StreamServerChannelPipelineFactory implements ChannelPipelineFactory {

  private final StreamServerListener streamServerListener;
  private final Dimension resolution;

  public StreamServerChannelPipelineFactory (
      StreamServerListener streamServerListener,
      Dimension resolution
  ) {
    super();
    this.streamServerListener = streamServerListener;
    this.resolution = resolution;
  }

  @Override
  public ChannelPipeline getPipeline() throws Exception {
    ChannelPipeline pipeline = Channels.pipeline();
    pipeline.addLast("frame encoder", new LengthFieldPrepender(4,false));
    pipeline.addLast("stream frame handler", new StreamServerExceptionHandler(streamServerListener));

    return pipeline;
  }
}
