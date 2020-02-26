package me.jbuelow.rov.dry.ui.video;

import static com.xuggle.xuggler.IPixelFormat.Type.YUV420P;

import com.xuggle.ferry.IBuffer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.ConverterFactory.Type;
import com.xuggle.xuggler.video.IConverter;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.codec.StreamFrameListener;
import org.jboss.netty.buffer.ChannelBuffer;

@Slf4j
public class VideoDecoder {

  private final ExecutorService decodeWorker;
  private final StreamFrameListener streamFrameListener;
  private final IStreamCoder codec;
  private final Type converterType = ConverterFactory.findRegisteredConverter(ConverterFactory.XUGGLER_BGR_24);

  public VideoDecoder(StreamFrameListener streamFrameListener, IStreamCoder codec) {
    super();

    this.streamFrameListener = streamFrameListener;
    this.codec = codec;

    decodeWorker = Executors.newSingleThreadExecutor();
    initialize();
  }

  public void initialize() {
    codec.open(null, null);
  }

  private class DecodeTask implements Runnable {

    private final Object image;

    public DecodeTask(Object image) {
      super();
      this.image = image;
    }

    @Override
    public void run() {
      log.trace("Starting frame decode...");
      if (Objects.isNull(image)) {
        return;
      }

      ChannelBuffer frameBuffer = (ChannelBuffer) image;
      int size = frameBuffer.readableBytes();
      IBuffer iBuffer = IBuffer.make(null, size);
      IPacket packet = IPacket.make(iBuffer);
      packet.getByteBuffer().put(frameBuffer.toByteBuffer());

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
            IConverter converter = ConverterFactory.createConverter(converterType.getDescriptor(), picture);
            BufferedImage imgOut = converter.toImage(picture);

            if (Objects.nonNull(streamFrameListener)) {
              streamFrameListener.onFrameReceived(imgOut);
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
