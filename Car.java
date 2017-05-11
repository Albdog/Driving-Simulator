import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Car {
    private double xPos, yPos;
    private static final double WIDTH = 50, LENGTH = 75;
    private final Road road;
    
    public Car(Road road) {
        this.road = road;
        xPos = road.getXDisplacement() + (road.getWidth() - WIDTH)/2;
        yPos = road.getYDisplacement() + 3*road.getHeight()/4;
    }
    
    public void draw(Graphics2D g) {
        Rectangle2D.Double car = new Rectangle2D.Double(xPos, yPos, WIDTH, LENGTH);
        
        g.setPaint(Color.RED);
        g.fill(car);
    }
    
    public static double getWidth() {
        return WIDTH;
    }
    
    public static double getLength() {
        return LENGTH;
    }
    
    public void setXPosition(double position) {
        xPos = position;
    }
    
    public double getX() {
        return xPos;
    }
    
    public double getY() {
        return yPos;
    }
        
    public Road getRoad() {
        return road;
    }
}
