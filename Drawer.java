import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Drawer extends JComponent implements Runnable {
    private int width, height;
    private Map map;
    private Thread thread, otherThread;
    
    public Drawer(int w, int h) {
        width = w;
        height = h;
        map = new Map(w, h);
        start();
    }
    
    private void start() {
        thread = new Thread(this);
        otherThread = new Thread(new Input());
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
                Thread.sleep(100);
                System.out.println("dog");
            } catch (InterruptedException ex) {
                System.err.println("Error in Thread Sleeping");
            }
        }
    }
}
