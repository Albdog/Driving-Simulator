import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Road {
    private int mapWidth, mapHeight, LINES_X, LINES_Y;
    private double FRACTION_OF_MAP;
    private ArrayList<RoadLine> lines;
    private double xDisplacement;
    
    public Road(int w, int h) {
        mapWidth = w;
        mapHeight = h;
        FRACTION_OF_MAP = 3.0/4;
        LINES_X = 2;
        LINES_Y = 10;
        xDisplacement = mapWidth*(1-FRACTION_OF_MAP)/2;
        createLines();
    }
    private void createLines() {
        lines = new ArrayList<RoadLine>();
        
        for(int i = 0; i < LINES_X; i++) {
            double x = xDisplacement + (i+1)*FRACTION_OF_MAP*mapWidth/(LINES_X + 1);
            for(int j = 0; j <= LINES_Y; j++) {
                double y = (j)*1.0*mapHeight/(LINES_Y + 1);
                lines.add(new RoadLine(x, y));
            }
        }
        RoadLine.setRoadHeight(mapHeight);
    }

    public void draw(Graphics2D g) {
        Rectangle2D.Double rect = new Rectangle2D.Double(xDisplacement, 0, mapWidth*FRACTION_OF_MAP, mapHeight);
        g.setPaint(Color.GRAY);
        g.fill(rect);
        
        for(RoadLine line : lines) line.draw(g);
    }
    
    public ArrayList<RoadLine> getLines() {
        return lines;
    }
}
