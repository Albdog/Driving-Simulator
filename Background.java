/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driving.simulator.physics;

import java.awt.*;
import java.awt.geom.*;

/**
 *
 * @author Joaquin
 */
public class Background {
    private int mapWidth, mapHeight;
    
    public Background(int w, int h) {
        mapWidth = w;
        mapHeight = h;
    }

    public void draw(Graphics2D g) {
        Rectangle2D.Double bg = new Rectangle2D.Double(0, 0, mapWidth, mapHeight);
        
        g.setPaint(Color.CYAN);
        
        g.fill(bg);
    }
    
}
