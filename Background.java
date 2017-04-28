import java.awt.*;
import java.awt.geom.*;

public class Background {
    private final int MAP_WIDTH, MAP_HEIGHT;
    private final Color BACKGROUND_COLOR;
    
    public Background(int frameWidth, int frameHeight) {
        MAP_WIDTH = frameWidth;
        MAP_HEIGHT = frameHeight;
        BACKGROUND_COLOR = Color.BLACK;
    }

    public void draw(Graphics2D g) {
        Rectangle2D.Double bg = new Rectangle2D.Double(0, 0, MAP_WIDTH, MAP_HEIGHT);
        
        g.setPaint(BACKGROUND_COLOR);
        g.fill(bg);
    }
}
