import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Drawer extends JComponent implements Runnable {
    private Thread roadThread, obstacleThread;
    private final double FRAME_WIDTH, FRAME_HEIGHT;
    private int currOS = 0;
    private ArrayList<Obstacle> obstacles;
    private final int OS_FREQUENCY = 400; //OS = Obstacle Spawner
    private final Random random;
    private Timer timer;
    private Road road;
    private Map map;
    private Car car;
    private long passedObstacles, timeAlive;
    private boolean dead;
    
    public Drawer(int frameWidth, int frameHeight) {
        FRAME_WIDTH = frameWidth;
        FRAME_HEIGHT = frameHeight;
        
	random = new Random();
        start();
    }
    
    
    private void start() {
        passedObstacles = 0;
        timeAlive = 0;
        dead = false;
        
        timer = new Timer(1, new Tick());
        timer.start();
        
        map = new Map(FRAME_WIDTH, FRAME_HEIGHT);
        road = new Road(FRAME_WIDTH/8, 0, FRAME_WIDTH*3/4, FRAME_HEIGHT);
        map.setRoad(road);
        car = new Car(road);
        obstacles = new ArrayList<Obstacle>();
        
        roadThread = new Thread(this);
	obstacleThread = new Thread(new HOMM()); 
        
        roadThread.start();
	obstacleThread.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if(dead) {
            Rectangle2D.Double bg = new Rectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            g2d.setPaint(Color.BLACK);
            g2d.fill(bg);
            g2d.setPaint(Color.WHITE);
            new Text(20, 100, "You died.", 50).draw(g2d);
            new Text(20, 200, "You passed " + passedObstacles + " obstacles.", 30).draw(g2d);
            new Text(20, 300, "You lasted for " + (timeAlive/1000) + "." + (timeAlive%1000) + " seconds.", 25).draw(g2d);
        }
        else {
            map.draw(g2d);
            for(Obstacle o : obstacles) o.draw(g2d);
            car.draw(g2d);
        }
    }

    @Override
    public void run() {
        while(true) {
            car.setXPosition(Adapter.getCarPosition(road));
            
            currOS = (currOS + 1) % OS_FREQUENCY;
            if(currOS == 0) spawnObstacle();
            
            for(RoadLine line : map.getRoad().getLines()) line.moveDown();
            
            for(int i = 0; i < obstacles.size(); i++) {
                if(obstacles.get(i).getY() >= road.getYDisplacement() + road.getHeight()) {
                    obstacles.remove(i);
                    passedObstacles++;
                    i--;
                }
                else {
                    obstacles.get(i).moveDown();
                    if(Adapter.collided(car, obstacles.get(i))) {
                        System.out.println("You died.");
                        System.out.println("You passed " + passedObstacles + " obstacles.");
                        System.out.println("You lasted for " + (timeAlive/1000) + "." + (timeAlive%1000) + " seconds.");
                        //System.exit(0);
                        dead = true;
                        repaint();
                        roadThread.stop();
                        obstacleThread.stop();
                        
                    }
                }
            }
            
            repaint();
            
            try {
                Thread.sleep(Adapter.getVerticalThreadTime());
            } catch (InterruptedException ex) {
                System.err.println("Error in Thread Sleeping");
            }
        }
    }
    private void spawnObstacle() {
        int lines = (int) road.getNumXLines() + 1;
        int position = random.nextInt(lines);
        
        if(random.nextInt(2) == 0) {
            boolean dir = random.nextInt(2) == 1;
            obstacles.add(new MovingObstacle(road.getXDisplacement() + position*road.getWidth()/lines + 25,
                                road.getYDisplacement() - 50,
                                50, 50, road, dir));
            return;
        }
        
        boolean goods = false;     
        for(int i = 0; i < lines; i++) {
            if(i == position || random.nextInt(2) == 1) continue;
            obstacles.add(new Obstacle(road.getXDisplacement() + i*road.getWidth()/lines + 25,
                                road.getYDisplacement() - 50,
                                50, 50, road));
            goods = true;
        }
        if(!goods) {
            obstacles.add(new Obstacle(road.getXDisplacement() + position*road.getWidth()/lines + 25,
                                road.getYDisplacement() - 50,
                                50, 50, road));
        }
    }

    private void die() {
        
    }

    private class HOMM implements Runnable {
        private final int obstacleMovementRate = 20; //inverse
        
        @Override
        public void run() {
            while(true) {
                for(int i = 0; i < obstacles.size(); i++) {
                    if(obstacles.get(i).getClass().equals(MovingObstacle.class)) {
                        ((MovingObstacle) obstacles.get(i)).moveHorizontally();
                    }
                }
                try {
                    Thread.sleep(obstacleMovementRate);
                }
                catch(InterruptedException ex) {
                    System.err.println("Error in Thread Sleping");
                }
            }
        }
    }
    
    private class Tick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeAlive++;
        }
    }
}
