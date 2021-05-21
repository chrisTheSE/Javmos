/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components;

import java.awt.Graphics2D;
import javmos.JavmosGUI;

public abstract class JavmosComponent {

    protected final JavmosGUI gui;

    public JavmosComponent(JavmosGUI gui) {
        this.gui = gui;
    }

    public abstract void draw(Graphics2D graphics2D);
}
