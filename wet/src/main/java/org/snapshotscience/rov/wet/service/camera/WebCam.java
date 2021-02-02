package org.snapshotscience.rov.wet.service.camera;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class WebCam {

  final int width = 1280;
  final int height = 720;

  public void start() throws Exception, FrameRecorder.Exception {
    CanvasFrame frame = new CanvasFrame("Video Test");
    FrameGrabber grabber = new OpenCVFrameGrabber(0);
    grabber.setImageHeight(height);
    grabber.setImageWidth(width);
    grabber.start();
    Frame image = grabber.grab();
    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("udp://239.0.0.1:1234?ttl=130", width, height);
    recorder.setVideoCodecName("h264");
    recorder.setFormat("mpegts");
    recorder.setFrameRate(30);
    recorder.setVideoBitrate(10000000);
    recorder.start();

    while ((image = grabber.grab()) != null) {
      frame.showImage(image);
      recorder.record(image);
    }
    frame.dispose();
    recorder.stop();
    grabber.stop();
  }

}
