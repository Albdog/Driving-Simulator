/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Road {
    private final double ROAD_WIDTH, ROAD_HEIGHT, LINES_X, LINES_Y, LINE_HEIGHT, LINE_WIDTH;
    private ArrayList<RoadLine> lines;
    private final double X_DISPLACEMENT, Y_DISPLACEMENT;
    
    public Road(double xDisplacement, double yDisplacement, double roadWidth, double roadHeight) {
        ROAD_WIDTH = roadWidth;
        ROAD_HEIGHT = roadHeight;
        X_DISPLACEMENT = xDisplacement;
        Y_DISPLACEMENT = yDisplacement;
        
        LINES_X = 2;
        LINES_Y = 10;
        LINE_HEIGHT = 30;
        LINE_WIDTH = 10;
        
        createLines();
    }
    private void createLines() {
        lines = new ArrayList<RoadLine>();
        
        for(int i = 0; i < LINES_X; i++) {
            double x = X_DISPLACEMENT + (i+1)*ROAD_WIDTH/(LINES_X + 1) - LINE_WIDTH/2;
            for(int j = 0; j < LINES_Y; j++) {
                double y = Y_DISPLACEMENT + j*(ROAD_HEIGHT + LINE_HEIGHT)/(LINES_Y);
                lines.add(new RoadLine(x, y, LINE_WIDTH, LINE_HEIGHT, this));
            }
        }
    }
    
    public void draw(Graphics2D g) {
        Rectangle2D.Double rect = new Rectangle2D.Double(X_DISPLACEMENT, Y_DISPLACEMENT, ROAD_WIDTH, ROAD_HEIGHT);
        g.setPaint(Color.GRAY);
        g.fill(rect);
        
        for(RoadLine line : lines) line.draw(g);
    }
    
    public ArrayList<RoadLine> getLines() {
        return lines;
    }
    
    public double getXDisplacement() {
        return X_DISPLACEMENT;
    }
    
    public double getYDisplacement() {
        return Y_DISPLACEMENT;
    }

    public double getHeight() {
        return ROAD_HEIGHT;
    }
    
    public double getWidth() {
        return ROAD_WIDTH;
    }
    
    public double getNumXLine() {
        return LINES_X;
    }
}
