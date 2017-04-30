import java.awt.Graphics2D;

public class Map {
    private double MAP_WIDTH, MAP_HEIGHT;
    private final Background background;
    private Road road;
    
    public Map(double w, double h) {
        MAP_WIDTH = w;
        MAP_HEIGHT = h;
        background = new Background(w, h);
        road = new Road(MAP_WIDTH/4, 0, MAP_WIDTH/2, MAP_HEIGHT); //default road
    }
    
    public void draw(Graphics2D g) {
        background.draw(g);
        road.draw(g);
    }
    
    public void setRoad(Road road) {
        this.road = road;
    }
    
    public Road getRoad() {
        return road;
    }
}
