/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driving.simulator.physics;

import java.awt.Graphics2D;

/**
 *
 * @author Joaquin
 */
public class Map {
    private int width, height;
    private Background background;
    private Road road;
    
    public Map(int w, int h) {
        width = w;
        height = h;
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
