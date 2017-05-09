/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Obstacle {
    private double xPos, yPos, Y_DISPLACEMENT, width, height;
    private Road road;
    
    public Obstacle(double x, double y, double width, double height, Road road) {
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        this.road = road;
        
        Y_DISPLACEMENT = 1;
    }

    public void draw(Graphics2D g) {
        Rectangle2D.Double circle = new Rectangle2D.Double(xPos, yPos, width, height);
        
        g.setPaint(Color.BLUE);
        g.fill(circle);
    }
    
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public void moveDown() {
        yPos += Y_DISPLACEMENT;
    }
    
    public void setX(double x) {
        xPos = x;
    }
    
    public void setY(double y) {
        yPos = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    
    public Road getRoad() {
        return road;
    }
}

