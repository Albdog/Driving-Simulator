import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RoadLine {
    private double x, y;
    private final double LINE_WIDTH, LINE_HEIGHT, Y_DISPLACEMENT;
    private static double ROAD_HEIGHT;
    private Color LINE_COLOR;
    
    public RoadLine(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        LINE_COLOR = Color.YELLOW;
        LINE_WIDTH = width;
        LINE_HEIGHT = height;
        Y_DISPLACEMENT = 1;
    }
    
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double line = new Rectangle2D.Double(x - LINE_WIDTH/2, y, LINE_WIDTH, LINE_HEIGHT);
        
        g2d.setPaint(LINE_COLOR);
        g2d.fill(line);
    }
    
    public void moveDown() {
        y = (y + Y_DISPLACEMENT + LINE_HEIGHT)%(ROAD_HEIGHT + LINE_HEIGHT) - LINE_HEIGHT;
    }
    
    public static void setRoadHeight(double height) {
        ROAD_HEIGHT = height;
    }
}
