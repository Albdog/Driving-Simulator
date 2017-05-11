
public class Adapter {
    private static final int sonarMax = 240, sonarMin = 30;
    private static final int middle = 511, movableRadius = 200;
    private static final int maxRoadThreadTime = 15, minRoadThreadTime = 1;
    
    private static int horizontalPercentage() {
        int potentiometer = ArduinoReader.potentialVal;
        
        if(potentiometer < middle - movableRadius) potentiometer = middle - movableRadius;
        else if(potentiometer > middle + movableRadius) potentiometer = middle + movableRadius;
        
        double denominator = movableRadius * 2;
        double numerator = potentiometer - (middle - movableRadius);
        
        potentiometer = (int) (numerator * 100 / denominator);
        
        return potentiometer;
    }

    private static int verticalPercentage() {
        double sonar = ArduinoReader.sonarSpeed;

        if(sonar > sonarMax) sonar = sonarMax;
        if(sonar < sonarMin) sonar = sonarMin;

        int sonarRange = sonarMax - sonarMin;
        int percent = (int) ((sonar - sonarMin)*100/sonarRange);
        
        return percent;
    }
    
    public static int getVerticalThreadTime() {
        int speed = verticalPercentage() * (maxRoadThreadTime - minRoadThreadTime) / 100 + 1;

        return speed;
    }
    
    public static double getCarPosition(Road road) {
        double position = horizontalPercentage() * (road.getWidth() - Car.getWidth()) / 100;
        
        if(position < 0) position = 0;
        else if(position > road.getWidth() - Car.getWidth()) position = road.getWidth() - Car.getWidth();
        
        position += road.getXDisplacement();
        
        return position;
    }
    
    public static boolean collided(Car car, Obstacle obstacle) {
        return !(car.getX() + Car.getWidth() < obstacle.getX() ||
                car.getY() + Car.getLength() < obstacle.getY() ||
                car.getX() > obstacle.getX() + obstacle.getWidth() ||
                car.getY() > obstacle.getY() + obstacle.getHeight());
    }
}
