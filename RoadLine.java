/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RoadLine {
    private double xPos, yPos;
    private final double LINE_WIDTH, LINE_HEIGHT, Y_SPEED;
    private final Color LINE_COLOR;
    private final Road road;
    
    public RoadLine(double x, double y, double width, double height, Road road) {
        xPos = x;
        yPos = y;
        LINE_WIDTH = width;
        LINE_HEIGHT = height;
        this.road = road;
        
        Y_SPEED = 1;
        LINE_COLOR = Color.YELLOW;
        
    }
    
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double line = new Rectangle2D.Double(xPos, yPos, LINE_WIDTH, LINE_HEIGHT);
        
        g2d.setPaint(LINE_COLOR);
        g2d.fill(line);
    }
    
    public void moveDown() {
        yPos = (yPos + Y_SPEED + LINE_HEIGHT)%(road.getHeight() + LINE_HEIGHT) - LINE_HEIGHT + road.getYDisplacement();
    }
}
