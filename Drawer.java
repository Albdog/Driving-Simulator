import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Drawer extends JComponent implements Runnable {
    private final int FRAME_WIDTH, FRAME_HEIGHT;
    private final Map map;
    private Thread thread;
    private int speed = 50;
    
    public Drawer(int frameWidth, int frameHeight) {
        FRAME_WIDTH = frameWidth;
        FRAME_HEIGHT = frameHeight;
        map = new Map(frameWidth, frameHeight);
        addKeyListener(new SpeedChanger());
        start();
    }
    
    private void start() {
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        map.draw(g2d);
    }

    @Override
    public void run() {
        while(true) {
            ArrayList<RoadLine> lines = map.getRoad().getLines();
            for(RoadLine line : lines) line.moveDown();
            repaint();
            
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                System.err.println("Error in Thread Sleeping");
            }
        }
    }
    
    private class SpeedChanger implements KeyListener {
        public SpeedChanger() {
            setFocusable(true);
            requestFocusInWindow();
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
        
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_UP && speed > 0) speed--;
            else if(key == KeyEvent.VK_DOWN && speed < 50) speed++;
            System.out.println("speed: " + (50 - speed));
        }
    }
}
