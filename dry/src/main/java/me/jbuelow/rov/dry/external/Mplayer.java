package me.jbuelow.rov.dry.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for attempting to use mplayer for our video stream.
 *
 * @deprecated does not work
 */
public class Mplayer {

  private Process process = null;

  public Mplayer(String url) {
    String ip = url.split(":")[0];
    String port = url.split(":")[1];
    try {
      //process = Runtime.getRuntime().exec("cmd /c ubuntu run 'nc "+ip+" "+port+" | mplayer -");
      process = Runtime.getRuntime().exec("cmd /c ubuntu run cool-retro-term");
      new Thread(new Runnable() {
        public void run() {
          BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String line = null;

          try {
            while ((line = input.readLine()) != null)
              System.out.println(line);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }).start();
      process.waitFor();
    } catch (IOException ignored) {} catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
