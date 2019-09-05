package me.jbuelow.rov.common.codec;

import java.awt.image.BufferedImage;

public interface StreamFrameListener {
	public void onFrameReceived(BufferedImage image);
}
