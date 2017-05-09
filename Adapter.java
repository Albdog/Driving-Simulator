
public class Adapter {
    private static final int sonarMax = 240, sonarMin = 30;
    private static final int middle = 511, stationaryRadius = 10, movableRadius = 200;
    
    
    private int horizontalSpeed() {
//        int temp = ArduinoReader.potentialVal;
        int temp = 0;

        if(temp < stationaryRadius - movableRadius) temp = stationaryRadius - movableRadius;
        else if(temp > stationaryRadius + movableRadius) temp = stationaryRadius + movableRadius;
        else {
            int range = movableRadius - stationaryRadius;

            int dog = Math.abs(temp - middle)*200/range;

            temp = dog;
        }

    }

    private static void verticalSpeed() {
//        double sonarThing = ArduinoReader.sonarSpeed;
        double sonarThing = 0;

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

//        roadSpeed = (int) sonarThing - 4;
    }
    
    public char getCarDirection() {
//        int temp = ArduinoReader.potentialVal;
        int temp = 0; //potentiometer reading
        
        if(Math.abs(temp - middle) <= stationaryRadius) return Car.MIDDLE;
        if(temp <= middle) return Car.LEFT;
        return Car.RIGHT;
    }
    
    public static int getVerticalThreadTime() {
        return 0;
    }
    
    public static int getHorizontalThreadTime() {
        return 0;
    }
}
