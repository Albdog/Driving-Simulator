public class MovingObstacle extends Obstacle {
    private final double X_DISPLACEMENT;
    private boolean direction; //false = left; true = right
    
    public MovingObstacle(double x, double y, double width, double height, Road road, boolean direction) {
        super(x, y, width, height, road);
        X_DISPLACEMENT = 1;
        this.direction = direction;
    }
    
    public void moveHorizontally() {
        if(getX() <= getRoad().getXDisplacement()) direction = true;
        if(getX() + getWidth() >= getRoad().getXDisplacement() + getRoad().getWidth()) direction = false;
        
        if(direction) setX(getX() + X_DISPLACEMENT);
        else setX(getX() - X_DISPLACEMENT);
    }
}
