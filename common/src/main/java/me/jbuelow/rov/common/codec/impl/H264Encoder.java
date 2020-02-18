package me.jbuelow.rov.common.codec.impl;

import com.xuggle.xuggler.ICodec.ID;
import com.xuggle.xuggler.IMetaData;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat.Type;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IStreamCoder.Direction;
import com.xuggle.xuggler.IStreamCoder.Flags;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.codec.VideoStreamEncoder;
import org.jboss.netty.buffer.ChannelBuffers;

@Slf4j
public class H264Encoder extends VideoStreamEncoder {

  private final IStreamCoder codec = IStreamCoder.make(Direction.ENCODING, ID.CODEC_ID_MPEG2VIDEO);
  private final IPacket packet = IPacket.make();

  private long startTime;

  private final Dimension resolution;
  private final int fps;
  private final int bitrate;
  private final int quality;
  private final boolean quick;

  private final int picturesInGroup = 10;
  private final int bitrateTolerance = 1000000;
  private final Type pixelType = Type.YUV420P;

  public H264Encoder(Dimension resolution, int fps, int bitrate, int quality, boolean quick) {
    super();
    this.resolution = resolution;
    this.fps = fps;
    this.bitrate = bitrate;
    this.quality = quality;
    this.quick = quick;

    initialize();
  }

  @Override
  public void initialize() {
    codec.setNumPicturesInGroupOfPictures(picturesInGroup);
    codec.setBitRateTolerance(bitrateTolerance);
    codec.setPixelType(pixelType);

    codec.setBitRate(bitrate);
    codec.setHeight(resolution.height);
    codec.setWidth(resolution.width);
    codec.setGlobalQuality(quality);

    IRational rate = IRational.make(fps, 1);
    codec.setFrameRate(rate);

    codec.setFlag(Flags.FLAG2_FAST, quick);
    codec.setFlag(Flags.FLAG_LOW_DELAY, quick);

    IMetaData codecOptions = IMetaData.make();
    if (quick) {
      codecOptions.setValue("tune", "zerolatency");
    }

    codec.setTimeBase(IRational.make(rate.getDenominator(), rate.getNumerator()));

    int val = codec.open(codecOptions, null);
    if (val < 0) {
      throw new RuntimeException("ffmpeg failed to open");
    }
  }

  @Override
  public Object encode(BufferedImage imgIn) {
    log.trace("Encoding frame"); //trace to keep it from flooding console when not needed
    long time = System.currentTimeMillis();

    //this part is kinda dumb but is necessary to use the ancient xuggler api
    BufferedImage img = new BufferedImage(imgIn.getWidth(), imgIn.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
    img.getGraphics().drawImage(imgIn, 0, 0,null);

    IConverter converter = ConverterFactory.createConverter(img, pixelType);

    long now = System.currentTimeMillis();
    if (startTime == 0) {
      startTime = now;
    }

    IVideoPicture frame = converter.toPicture(img, (now - startTime)*1000);
    codec.encodeVideo(packet, frame, 0);

    frame.delete();
    converter.delete();

    log.trace("Frame encode took {}ms", System.currentTimeMillis()-time);
    if (packet.isComplete()) {
      try {
        ByteBuffer bb = packet.getByteBuffer();
        return ChannelBuffers.copiedBuffer(bb.order(ByteOrder.BIG_ENDIAN));
      } finally {
        packet.reset();
      }
    } else {
      return null;
    }
  }
}
