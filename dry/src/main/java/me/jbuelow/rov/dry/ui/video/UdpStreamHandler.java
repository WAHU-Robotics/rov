package me.jbuelow.rov.dry.ui.video;

import static com.xuggle.xuggler.IPixelFormat.Type.YUV420P;

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.ConverterFactory.Type;
import com.xuggle.xuggler.video.IConverter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.codec.StreamFrameListener;

@Slf4j
public class UdpStreamHandler {

  private final StreamFrameListener frameListener;
  private final Type converterType = ConverterFactory.findRegisteredConverter(ConverterFactory.XUGGLER_BGR_24);

  public UdpStreamHandler(StreamFrameListener frameListener) {
    this.frameListener = frameListener;
  }

  public void start(String source, String codec) {
    IContainer container = IContainer.make();
    IContainerFormat format = IContainerFormat.make();

    format.setInputFormat(codec);

    String fName = format.getInputFormatShortName();
    IPacket packet = IPacket.make();
    Map<Integer, IStreamCoder> knownStreams = new HashMap<>();

    try {
      int opened = container.open(source, IContainer.Type.READ,
          format, true, false);
    } catch (Exception e) {
      log.error("Exception in opening video container:", e);
    }

    Object image;
    while (container.readNextPacket(packet) >= 0) {
      log.trace("reading packet");
      if (packet.isComplete()) {
        log.trace("got complete packet");
        if (knownStreams.get(packet.getStreamIndex()) == null) {
          container.queryStreamMetaData();
          // stream should now be set up correct
          IStream stream =
              container.getStream(packet.getStreamIndex());
          knownStreams.put(packet.getStreamIndex(),
              stream.getStreamCoder());
        }

        log.trace("Starting decode of packet");
        IStreamCoder coder =
            knownStreams.get(packet.getStreamIndex());
        new DecodeTask(packet, coder).run();
      }
    }
  }

  private class DecodeTask implements Runnable {

    private final IPacket image;
    private final IStreamCoder codec;

    public DecodeTask(IPacket image, IStreamCoder codec) {
      super();
      this.image = image;
      this.codec = codec;
    }

    @Override
    public void run() {
      log.trace("Starting frame decode...");
      if (Objects.isNull(image)) {
        return;
      }

      IPacket packet = image;

      codec.open(null, null);

      if (!packet.isComplete()) {
        return;
      }

      IVideoPicture picture = IVideoPicture.make(YUV420P, codec.getWidth(), codec.getHeight());

      try {
        int position = 0;
        int packetSize = packet.getSize();

        while (position < packetSize) {
          position += codec.decodeVideo(picture, packet, position);

          if (position < 0) {
            throw new RuntimeException("Codec error");
          }

          if (picture.isComplete()) {
            IConverter converter = ConverterFactory
                .createConverter(converterType.getDescriptor(), picture);
            BufferedImage imgOut = converter.toImage(picture);

            if (Objects.nonNull(frameListener)) {
              frameListener.onFrameReceived(imgOut);
            }
            converter.delete();
          } else {
            picture.delete();
            packet.delete();
            return;
          }

          picture.getByteBuffer().clear();
        }
      } finally {
        if (Objects.nonNull(picture)) {
          picture.delete();
        }
        packet.delete();
      }
    }
  }

}
