import java.awt.Graphics2D;

public class Map {
    private static int MAP_WIDTH, MAP_HEIGHT;
    private final Background background;
    private final Road road;
    
    public Map(int w, int h) {
        MAP_WIDTH = w;
        MAP_HEIGHT = h;
        background = new Background(w, h);
        road = new Road(w, h);
    }
    
    public void draw(Graphics2D g) {
        background.draw(g);
        road.draw(g);
    }
    
    public Road getRoad() {
        return road;
    }
}
