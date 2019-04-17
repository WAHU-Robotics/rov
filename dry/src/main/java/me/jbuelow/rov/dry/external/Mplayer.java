package me.jbuelow.rov.dry.external;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for attempting to use mplayer for our video stream.
 *
 * @deprecated does not work
 */
public class Mplayer {

  Process process = null;

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
