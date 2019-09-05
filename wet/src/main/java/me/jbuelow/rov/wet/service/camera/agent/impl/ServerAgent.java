package me.jbuelow.rov.wet.service.camera.agent.impl;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.codec.IStreamAgent;
import me.jbuelow.rov.common.codec.impl.H264Encoder;
import me.jbuelow.rov.wet.service.camera.agent.StreamServerChannelPipelineFactory;
import me.jbuelow.rov.wet.service.camera.agent.StreamServerListener;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

//WARNING: This file is a dumpster fire and could really use a clean up so TODO i guess

@Slf4j
public class ServerAgent implements IStreamAgent {

  private final Webcam webcam;
  private final Dimension resolution;
  private final int fps;
  private final int bitrate;
  private final int quality;
  private final boolean quick;

  final ChannelGroup channelGroup = new DefaultChannelGroup();
  final ServerBootstrap serverBootstrap;

  final H264Encoder encoder;

  private volatile boolean streaming;
  private volatile long frameNum = 0;

  private ScheduledExecutorService timeWorker;
  private ExecutorService encodeWorker;

  private ScheduledFuture<?> imageGrabFuture;

  public ServerAgent(Webcam webcam, Dimension resolution, int fps, int bitrate, int quality, boolean quick) {
    super();
    this.webcam = webcam;
    this.resolution = resolution;
    this.fps = fps;
    this.bitrate = bitrate;
    this.quality = quality;
    this.quick = quick;

    this.serverBootstrap = new ServerBootstrap();
    this.serverBootstrap.setFactory(new NioServerSocketChannelFactory(
        Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool()));
    this.serverBootstrap.setPipelineFactory(new StreamServerChannelPipelineFactory(
        new StreamServerListenerServiceImpl(), resolution));

    this.encoder = new H264Encoder(resolution, fps, bitrate, quality, quick);

    this.timeWorker = new ScheduledThreadPoolExecutor(1);
    this.encodeWorker = Executors.newSingleThreadExecutor();
  }

  @Override
  public void start(SocketAddress socketAddress) {
    log.info("Started camera stream server on {}", socketAddress);
    Channel channel = serverBootstrap.bind(socketAddress);
    channelGroup.add(channel);
  }

  @Override
  public void stop() {
    log.info("Stopping camera stream server");
    channelGroup.close();
    timeWorker.shutdown();
    encodeWorker.shutdown();
    serverBootstrap.releaseExternalResources();
  }

  private class StreamServerListenerServiceImpl implements StreamServerListener {

    @Override
    public void onClientConnect(Channel channel) {
      webcam.open();
      channelGroup.add(channel);
      if (!streaming) {
        Runnable imageGrabber = new ImageGrabber();
        imageGrabFuture = timeWorker.scheduleWithFixedDelay(imageGrabber,
            0,
            1000/fps,
            TimeUnit.MILLISECONDS);
        streaming = true;
      }
      log.info("Camera stream client connected: {}", channel.getRemoteAddress());
      log.info("The number of connected clients is now {}", channelGroup.size());
    }

    @Override
    public void onClientDisconnect(Channel channel) {
      channelGroup.remove(channel);
      channel.close();
      int size = channelGroup.size();
      log.info("Camera stream client disconnected: {}", channel.getRemoteAddress());
      log.info("The number of connected clients is now {}", channelGroup.size());

      if (size == 1) {
        log.info("No stream clients are connected. Suspending stream...");
        imageGrabFuture.cancel(false);
        webcam.close();
        streaming = false;
      }
    }

    @Override
    public void onException(Channel channel, Throwable throwable) {
      log.error("An exception occurred that led to the disconnect of client at {}", channel.getRemoteAddress(), throwable);
      onClientDisconnect(channel);
    }
  }

  private class ImageGrabber implements Runnable {

    @Override
    public void run() {
      log.trace("Grabbing frame #{} from webcam", frameNum++); //trace level to prevent flooding console
      BufferedImage image = webcam.getImage();
      encodeWorker.execute(new EncoderTask(image));
    }
  }

  private class EncoderTask implements Runnable {
    private final BufferedImage image;

    public EncoderTask(BufferedImage image) {
      super();
      this.image = image;
    }

    @Override
    public void run() {
      try {
        Object data = encoder.encode(image);
        if (Objects.nonNull(data)) {
          channelGroup.write(data);
        }
      } catch (Exception e) {
        log.error("Codec error", e);
      }
    }
  }
}
