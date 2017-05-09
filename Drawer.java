/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

public class Drawer extends JComponent implements Runnable {
    private final double FRAME_WIDTH, FRAME_HEIGHT;
    private Map map;
    private Thread roadThread, carThread, obstacleThread, controlThread;
    private Car car;
    private Road road;
    private ArrayList<Obstacle> obstacles;
    private final Random random;
    private int roadSpeed = 50, currOS = 0;
    private final int OS_FREQUENCY = 400; //OS = Obstacle Spawner
    private final double sonarMax = 240, sonarMin = 30, roadSpeedMax = 49;
    private final int middle = 511, directionRange = 200, middleRange = 10;
    
    public Drawer(int frameWidth, int frameHeight) {
        FRAME_WIDTH = frameWidth;
        FRAME_HEIGHT = frameHeight;
        
	random = new Random();
        //addKeyListener(new SpeedChanger());
        //addKeyListener(new DirectionChanger());
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
        controlThread = new Thread(new DirectionChanger());
        roadThread.start();
        carThread.start();
	obstacleThread.start();
        controlThread.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        
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
            
            //repaint();
            
            try {
                Thread.sleep(roadSpeed);
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
            
            if(key == KeyEvent.VK_UP && roadSpeed > 1) roadSpeed--;
            else if(key == KeyEvent.VK_DOWN && roadSpeed < 50) roadSpeed++;
            System.out.println("road speed: " + (50 - roadSpeed));
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
    
    private class Dog implements KeyListener {
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
    
    
    
    private class DirectionChanger implements Runnable {

        @Override
        public void run() {
            while(true) {
                sonarSpeed();
                wheel();
                repaint();
                
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Drawer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        private void wheel() {
            int temp = ArduinoReader.potentialVal;
            
            if(Math.abs(temp - middle) <= middleRange) car.moveMiddle();
            else if(temp <= middle) car.moveLeft();
            else car.moveRight();
            
            
            if(temp < middleRange - directionRange) temp = middleRange - directionRange;
            else if(temp > middleRange + directionRange) temp = middleRange + directionRange;
            else {
                int range = directionRange - middleRange;
                
                int dog = Math.abs(temp - middle)*200/range;
                
                temp = dog;
            }
            
        }
        
        private void sonarSpeed() {
            double sonarThing = ArduinoReader.sonarSpeed;

            if(sonarThing > sonarMax) sonarThing = sonarMax;
            if(sonarThing < sonarMin) sonarThing = sonarMin;

            //1 to 49   roadspeedmax
            //1 to 250  sonarmax
            sonarThing = sonarThing*roadSpeedMax/sonarMax;
            /*
            sonarThing = sonarMin     30*49/250


            sonarMin : slowest        49

            sonarMax : 1

            */

            roadSpeed = (int) sonarThing - 4;
        }
        
    }
    
    
}
