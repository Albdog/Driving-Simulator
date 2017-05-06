
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Car implements Runnable {

    private double xPos, yPos;
    private int xSpeed, ySpeed;
    private static final double width = 20, length = 30;
    private final double X_DISPLACEMENT;
    private Road road;
    private boolean direction;
    
    public Car(Road road) {
        this.road = road;
        xPos = road.getXDisplacement() + (road.getWidth() - width)/2;
        yPos = road.getYDisplacement() + 3*road.getHeight()/4;
        xSpeed = 10;
        ySpeed = 0;
        X_DISPLACEMENT = 1;
        direction = true; //false if left, true if right
    }
    
    public void draw(Graphics2D g) {
        Rectangle2D.Double car = new Rectangle2D.Double(xPos, yPos, width, length);
        
        g.setPaint(Color.RED);
        g.fill(car);
    }

    @Override
    public void run() {
        while(true) {
            if(xPos <= road.getXDisplacement()) direction = true;
            else if(xPos + width >= road.getXDisplacement() + road.getWidth()) direction = false;
            
            if(direction) xPos += X_DISPLACEMENT;
            else xPos -= X_DISPLACEMENT;
            
            try {
                Thread.sleep(xSpeed);
            } catch (InterruptedException ex) {
                System.err.println("Error in Thread Sleeping");
            }
        }
    }
    
    public void increaseXSpeed() {
        if(xSpeed > 1) xSpeed--;
    }
    
    public void decreaseXSpeed() {
        if(xSpeed < 200) xSpeed++;
    }
    
    public static double getWidth() {
        return width;
    }

    public int getSpeed() {
        return xSpeed;
    }
}
