package org.snapshotscience.rov.dry.ui;

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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class LiveIndicator extends JPanel {

  LiveIndicator() {
    // set a preferred size for the custom panel.
    setPreferredSize(new Dimension(128,128));
    setLayout(new BorderLayout());
  }

  public abstract void draw(Graphics g);

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }
}
