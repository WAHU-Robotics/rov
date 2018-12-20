/**
 *
 */
package me.jbuelow.rov.common;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class VideoStreamAddress implements Response {

  public int port;

  public VideoStreamAddress(int port) {
    try {
      Runtime.getRuntime().exec(
          "raspivid -o - -t 9999999 |cvlc -vvv stream:///dev/stdin --sout '#rtp{sdp=rtsp://:" + port
              + "/}' :demux=h264");
    } catch (Exception e) {
    }
  }

}
