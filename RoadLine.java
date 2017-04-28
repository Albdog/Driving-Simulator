import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class RoadLine {
    private double x, y;
    private static double WIDTH, HEIGHT, speed;
    private static Color color;
    private static double ROAD_HEIGHT;
    
    public RoadLine(double x, double y) {
        this.x = x;
        this.y = y;
        color = Color.WHITE;
        WIDTH = 10;
        HEIGHT = 30;
        speed = 10;
    }
    
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double line = new Rectangle2D.Double(x - WIDTH/2, y, WIDTH, HEIGHT);
        
        g2d.setPaint(color);
        g2d.fill(line);
    }
    public void moveDown() {
        y = (y + speed)%ROAD_HEIGHT;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public static void setRoadHeight(double height) {
        ROAD_HEIGHT = height;
    }
}
