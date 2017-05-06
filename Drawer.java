import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;

public class Drawer extends JComponent implements Runnable {
    private final double FRAME_WIDTH, FRAME_HEIGHT;
    private Map map;
    private Thread roadThread, carThread, obstacleThread;
    private Car car;
    private Road road;
    private ArrayList<Obstacle> obstacles;
    private final Random random;
    private int carSpeed = 50, currOS = 0;
    private final int OS_FREQUENCY = 200; //OS = Obstacle Spawner

    public Drawer(int frameWidth, int frameHeight) {
        FRAME_WIDTH = frameWidth;
        FRAME_HEIGHT = frameHeight;
        
	random = new Random();
        addKeyListener(new SpeedChanger());
        addKeyListener(new DirectionChanger());
        start();
    }
    
    private void start() {
        map = new Map(FRAME_WIDTH, FRAME_HEIGHT);
        road = new Road(FRAME_WIDTH/8, 0, FRAME_WIDTH*3/4, FRAME_HEIGHT);
        map.setRoad(road);
        car = new Car(road);
        obstacles = new ArrayList<Obstacle>();
        
        roadThread = new Thread(this);
        carThread = new Thread(car);
	obstacleThread = new Thread(new HOMM()); 
        roadThread.start();
        carThread.start();
	obstacleThread.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        //RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHints(rh);
        
        map.draw(g2d);
        for(Obstacle o : obstacles) o.draw(g2d);
        car.draw(g2d);
    }

    @Override
    public void run() {
        while(true) {
            currOS = (currOS + 1) % OS_FREQUENCY;;
            if(currOS == 0) spawnObstacle();
            
            for(RoadLine line : map.getRoad().getLines()) line.moveDown();
            
            for(int i = 0; i < obstacles.size(); i++) {
                if(obstacles.get(i).getY() >= road.getYDisplacement() + road.getHeight()) {
                    obstacles.remove(i);
                    i--;
                }
                else obstacles.get(i).moveDown();
            }
            
            repaint();
            
            try {
                Thread.sleep(carSpeed);
            } catch (InterruptedException ex) {
                System.err.println("Error in Thread Sleeping");
            }
        }
    }
    private void spawnObstacle() {
        int lines = (int) road.getNumXLine() + 1;
        int position = random.nextInt(lines);
        boolean goods = false;            	
        if(random.nextInt(2) == 0) {
            boolean dir = random.nextInt(2) == 1;
            obstacles.add(new MovingObstacle(road.getXDisplacement() + position*road.getWidth()/lines + 25,
                                road.getYDisplacement() - 50,
                                50, 50, road, dir));
            return;
        }

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
            
            if(key == KeyEvent.VK_UP && carSpeed > 1) carSpeed--;
            else if(key == KeyEvent.VK_DOWN && carSpeed < 50) carSpeed++;
            System.out.println("road speed: " + (50 - carSpeed));
        }
    }
    
    private class HOMM implements Runnable {
        private final int inverseSpeed = 20;
        @Override
        public void run() {
            while(true) {
                for(int i = 0; i < obstacles.size(); i++) {
                    if(obstacles.get(i).getClass().equals(MovingObstacle.class)) {
                        ((MovingObstacle) obstacles.get(i)).moveHorizontally();
                    }
                }
                try {
                    Thread.sleep(inverseSpeed);
                }
                catch(InterruptedException ex) {
                    System.err.println("Error in Thread Sleping");
                }
            }
        }
    }
    
    private class DirectionChanger implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT) car.decreaseXSpeed();
            else if(key == KeyEvent.VK_RIGHT) car.increaseXSpeed();
            
            System.out.println("car speed: " + (200 - car.getSpeed()));
            System.out.println("");
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
