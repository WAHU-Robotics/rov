package me.jbuelow.rov.dry.ui.video;

import java.awt.image.BufferedImage;

public abstract class VideoFrameReceiver {

  public abstract void newFrame(BufferedImage frame);

}
