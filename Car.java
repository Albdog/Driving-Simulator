import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Car implements Runnable {
    public static final char RIGHT = 'R', LEFT = 'L', MIDDLE = 'B';
    private double xPos, yPos;
    private int xSpeed, ySpeed;
    private static final double width = 50, length = 75;
    private static final int minSpeed = 0, maxSpeed = 200;
    private final double X_DISPLACEMENT;
    private Road road;
    private char direction;
    
    
    public Car(Road road) {
        this.road = road;
        xPos = road.getXDisplacement() + (road.getWidth() - width)/2;
        yPos = road.getYDisplacement() + 3*road.getHeight()/4;
        xSpeed = 10;
        ySpeed = 0;
        X_DISPLACEMENT = 1;
        direction = RIGHT;
    }
    
    public void draw(Graphics2D g) {
        Rectangle2D.Double car = new Rectangle2D.Double(xPos, yPos, width, length);
        
        g.setPaint(Color.RED);
        g.fill(car);
    }

    @Override
    public void run() {
        while(true) {
            direction = Adapter.getCarDirection();
            if(isRight() && xPos + getWidth() < road.getXDisplacement() + road.getWidth()) xPos += X_DISPLACEMENT;
            else if(isLeft() && xPos >= road.getXDisplacement()) xPos -= X_DISPLACEMENT;
            
            try {
                System.out.println("time: " + Adapter.getHorizontalThreadTime());
                System.out.println("dir:  " + Adapter.getCarDirection());
//                Thread.sleep(maxSpeed - xSpeed);
                Thread.sleep(Adapter.getHorizontalThreadTime());
            } catch (InterruptedException ex) {
                System.err.println("Error in Thread Sleeping");
            }
        }
    }
    
    public void moveLeft(){
        direction = LEFT;
    }
    
    public void moveRight(){
        direction = RIGHT;
    }
    
    public void moveMiddle() {
        direction = MIDDLE;
    }
    
    public boolean isRight() {
        return direction == RIGHT;
    }
    
    public boolean isLeft() {
        return direction == LEFT;
    }
    
    public boolean isMiddle() {
        return direction == MIDDLE;
    }
    
    public void increaseXSpeed() {
        if(xSpeed < maxSpeed - 1) xSpeed++;
    }
    
    public void decreaseXSpeed() {
        if(xSpeed > minSpeed) xSpeed--;
    }
    
    public static double getWidth() {
        return width;
    }

    public int getSpeed() {
        return xSpeed;
    }
    
    public void setSpeed(int speed) {
        xSpeed = speed;
    }
    
    public static int getMaxSpeed() {
        return maxSpeed;
    }
    
    public static int getMinSpeed() {
        return minSpeed;
    }
}
