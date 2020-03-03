package me.jbuelow.rov.dry.ui.video;

import java.util.Timer;
import java.util.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

@Slf4j
public class VideoTransport {

  private final FFmpegFrameGrabber streamFrameGrabber;
  private final VideoFrameReceiver frameReceiver;
  private final Timer timer = new Timer();
  private final Java2DFrameConverter converter = new Java2DFrameConverter();

  public VideoTransport(String source, VideoFrameReceiver frameReceiver) {
    this.streamFrameGrabber = new FFmpegFrameGrabber(source);
    this.frameReceiver = frameReceiver;
  }

  public void start() {
    try {
      log.info("Starting frame grabber...");
      streamFrameGrabber.start();
      log.info("Scheduling timer...");
      timer.scheduleAtFixedRate(new GrabTask(), 0, 10);
    } catch (Exception e) {
      log.error("Exception: ", e);
    }
  }

  private class GrabTask extends TimerTask {
    Frame frame;
    @Override
    public void run() {
      try {
        if ((frame = streamFrameGrabber.grab()) != null) {
          //log.info("Grabbed frame from stream at {}.", streamFrameGrabber.getTimestamp());
          frameReceiver.newFrame(converter.convert(frame));
        }
      } catch (Exception e) {
        log.error("Exception: ", e);
      }
    }
  }

}
