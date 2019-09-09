package me.jbuelow.rov.wet.service.camera.agent;

import java.awt.Dimension;
import me.jbuelow.rov.common.codec.StreamListener;
import me.jbuelow.rov.common.codec.StreamExceptionHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

public class StreamServerChannelPipelineFactory implements ChannelPipelineFactory {

  private final StreamListener streamListener;
  private final Dimension resolution;

  public StreamServerChannelPipelineFactory (
      StreamListener streamListener,
      Dimension resolution
  ) {
    super();
    this.streamListener = streamListener;
    this.resolution = resolution;
  }

  @Override
  public ChannelPipeline getPipeline() throws Exception {
    ChannelPipeline pipeline = Channels.pipeline();
    pipeline.addLast("frame encoder", new LengthFieldPrepender(4,false));
    pipeline.addLast("stream frame handler", new StreamExceptionHandler(streamListener));

    return pipeline;
  }
}
