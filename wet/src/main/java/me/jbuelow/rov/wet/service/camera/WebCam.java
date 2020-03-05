package me.jbuelow.rov.wet.service.camera;

import java.util.Timer;
import java.util.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameGrabber;

@Slf4j
public class WebCam {

  final int width = 1280;
  final int height = 720;
  final int framerate = 30;

  private FrameGrabber grabber;
  private FFmpegFrameRecorder recorder;
  private final Timer timer = new Timer();

  public void start() throws Exception, FrameRecorder.Exception {
    grabber = new OpenCVFrameGrabber(0);
    grabber.setImageHeight(height);
    grabber.setImageWidth(width);
    grabber.start();
    Frame image = grabber.grab();
    recorder = new FFmpegFrameRecorder("udp://239.0.0.1:1234?ttl=130", width, height);
    recorder.setVideoCodecName("mpeg4_mmal");
    recorder.setFormat("mpegts");
    recorder.setFrameRate(30);
    recorder.start();

    timer.scheduleAtFixedRate(new FrameCaptureTask(), 0, 1000/framerate);
  }

  private class FrameCaptureTask extends TimerTask {

    @Override
    public void run() {
      try {
        recorder.record(grabber.grabFrame());
      } catch (FrameRecorder.Exception | Exception e) {
        log.error("Exception while attempting to record frame: ", e);
      }
    }

  }

}
