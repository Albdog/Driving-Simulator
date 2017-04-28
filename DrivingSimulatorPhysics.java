/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driving.simulator.physics;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Joaquin
 */
public class DrivingSimulatorPhysics extends JFrame {
    private Drawer drawer;
    private static int width, height;
    
    public DrivingSimulatorPhysics(int w, int h) {
        drawer = new Drawer(w, h);
        width = w;
        height = h;
        this.add(drawer);
    }
    
    public static void main(String[] args) {
        DrivingSimulatorPhysics dsp = new DrivingSimulatorPhysics(400, 600);
        dsp.getContentPane().setPreferredSize(new Dimension(width, height));
        dsp.setResizable(false);
        dsp.pack();
        dsp.setTitle("Driving Simulator");
        dsp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dsp.setLocationRelativeTo(null);
        dsp.setVisible(true); 
    }    
}
