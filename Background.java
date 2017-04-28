import java.awt.*;
import java.awt.geom.*;

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
