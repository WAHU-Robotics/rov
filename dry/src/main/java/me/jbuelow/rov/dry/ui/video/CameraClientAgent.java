package me.jbuelow.rov.dry.ui.video;

import java.awt.Dimension;
import java.net.ConnectException;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.codec.IStreamAgent;
import me.jbuelow.rov.common.codec.StreamExceptionHandler;
import me.jbuelow.rov.common.codec.StreamFrameListener;
import me.jbuelow.rov.common.codec.StreamListener;
import me.jbuelow.rov.common.codec.impl.H264Decoder;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

@Slf4j
@Deprecated
public class CameraClientAgent implements IStreamAgent {

  private final Dimension resolution;
  private final ClientBootstrap bootstrap;
  private final StreamFrameListener streamFrameListener;
  private final StreamListener streamClientListener;
  private Channel channel;

  public CameraClientAgent(StreamFrameListener streamFrameListener, Dimension resolution) {
    super();

    this.resolution = resolution;
    this.streamFrameListener = streamFrameListener;

    this.bootstrap = new ClientBootstrap();
    this.bootstrap.setFactory(new NioClientSocketChannelFactory(
        Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool()
    ));

    this.streamClientListener = new StreamListenerImpl();

    this.bootstrap.setPipelineFactory(new StreamClientChannelPipelineFactory(
        streamClientListener,
        streamFrameListener,
        resolution
    ));
  }

  @Override
  public void start(SocketAddress socketAddress) {
    log.info("Connecting to camera server at {}...", socketAddress);
    bootstrap.connect(socketAddress);
  }

  @Override
  public void stop() {
    log.info("Video stream connection stopping...");
    channel.close();
    bootstrap.releaseExternalResources();
  }

  private class StreamClientChannelPipelineFactory implements ChannelPipelineFactory {

    private final StreamListener streamListener;
    private final StreamFrameListener streamFrameListener;
    private final Dimension resolution;

    public StreamClientChannelPipelineFactory(StreamListener streamListener,
        StreamFrameListener streamFrameListener, Dimension resolution) {
      super();
      this.streamListener = streamListener;
      this.streamFrameListener = streamFrameListener;
      this.resolution = resolution;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
      ChannelPipeline pipeline = Channels.pipeline();

      pipeline.addLast("stream client handler", new StreamExceptionHandler(streamListener));
      pipeline.addLast("frame decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
      pipeline.addLast("stream handler", new H264Decoder(streamFrameListener, resolution));
      return pipeline;
    }
  }

  private class StreamListenerImpl implements StreamListener {

    @Override
    public void onConnect(Channel ch) {
      channel = ch;
      log.info("Video stream connected");
    }

    @Override
    public void onDisconnect(Channel channel) {
      log.info("Video stream disconnected");
    }

    @Override
    public void onException(Channel channel, Throwable throwable) {
      log.error("Exception encountered in video stream", throwable);
      if (throwable.getClass() == ConnectException.class) {
        throw new RuntimeException("Could not connect video stream");
      }
    }
  }
}
