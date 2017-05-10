
public class Adapter {
    private static final int sonarMax = 240, sonarMin = 30;
    private static final int middle = 511, stationaryRadius = 10, movableRadius = 200;
    private static final int maxRoadThreadTime = 50, minRoadThreadTime = 1;
    private static final int maxHorizontalThreadTime = 200, minHorizontalThreadTime = 1;
    
    private static int horizontalSpeedPercentage() {
        int potentiometer = ArduinoReader.potentialVal;
        
        if(potentiometer < middle - movableRadius) potentiometer = middle - movableRadius;
        else if(potentiometer > middle + movableRadius) potentiometer = middle + movableRadius;
        else if(Math.abs(potentiometer - middle) <= stationaryRadius) potentiometer = 1; 
        else {
            double denominator = movableRadius - stationaryRadius;
            
            double numerator;
            if(potentiometer < middle) numerator = potentiometer - (middle - movableRadius);
            else numerator = potentiometer - (middle + stationaryRadius);
            
            potentiometer = (int) (numerator * 100 / denominator);
        }
        return potentiometer;
    }

    private static int verticalSpeedPercentage() {
        double sonar = ArduinoReader.sonarSpeed;

        if(sonar > sonarMax) sonar = sonarMax;
        if(sonar < sonarMin) sonar = sonarMin;

        int sonarRange = sonarMax - sonarMin;
        int percent = (int) ((sonar - sonarMin)*100/sonarRange);
        
        return percent;
    }
    
    public static char getCarDirection() {
        int temp = ArduinoReader.potentialVal;
        
        if(Math.abs(temp - middle) <= stationaryRadius) return Car.MIDDLE;
        if(temp <= middle) return Car.LEFT;
        return Car.RIGHT;
    }
    
    public static int getVerticalThreadTime() {
        int speed = verticalSpeedPercentage() * (maxRoadThreadTime - minRoadThreadTime) / 100;

        return speed + 1;
    }
    
    public static int getHorizontalThreadTime() {
        int speed = horizontalSpeedPercentage() * (maxHorizontalThreadTime - minHorizontalThreadTime) / 100;
        
//        return speed + 1;
//        return 10;
        return speed + 1;
    }
}
