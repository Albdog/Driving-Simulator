import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Road {
    private final double ROAD_WIDTH, ROAD_HEIGHT, LINES_X, LINES_Y, LINE_HEIGHT, LINE_WIDTH, FRACTION_OF_MAP, X_DISPLACEMENT;
    private ArrayList<RoadLine> lines;
    
    public Road(int mapWidth, int mapHeight) {
        ROAD_WIDTH = mapWidth;
        ROAD_HEIGHT = mapHeight;
        FRACTION_OF_MAP = 3.0/4;
        LINES_X = 2;
        LINES_Y = 10;
        LINE_HEIGHT = 30;
        LINE_WIDTH = 10;
        X_DISPLACEMENT = ROAD_WIDTH*(1-FRACTION_OF_MAP)/2;
        createLines();
    }
    private void createLines() {
        lines = new ArrayList<RoadLine>();
        
        for(int i = 0; i < LINES_X; i++) {
            double x = X_DISPLACEMENT + (i+1)*FRACTION_OF_MAP*ROAD_WIDTH/(LINES_X + 1);
            for(int j = 0; j < LINES_Y; j++) {
                double y = j*(ROAD_HEIGHT + LINE_HEIGHT)/(LINES_Y);
                lines.add(new RoadLine(x, y, LINE_WIDTH, LINE_HEIGHT));
            }
        }
        RoadLine.setRoadHeight(ROAD_HEIGHT);
    }
    
    public void draw(Graphics2D g) {
        Rectangle2D.Double rect = new Rectangle2D.Double(X_DISPLACEMENT, 0, ROAD_WIDTH*FRACTION_OF_MAP, ROAD_HEIGHT);
        g.setPaint(Color.GRAY);
        g.fill(rect);
        
        for(RoadLine line : lines) line.draw(g);
    }
    
    public ArrayList<RoadLine> getLines() {
        return lines;
    }
}
