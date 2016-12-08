package model;

/**
 * Created by venik on 08.12.16.
 */
public class SplitFood extends Food {
    private DoubleVector velocity = new DoubleVector();
    private long emergeTime;
    private double lastUpdate;

    public SplitFood(int x, int y) {
        super(x, y);
        emergeTime = System.currentTimeMillis();
    }

    public DoubleVector getVelocity(){return velocity;}
    public void setVelocity(DoubleVector velocity){this.velocity = velocity;}
    public boolean update(){
        if(System.currentTimeMillis() - emergeTime > GameConstants.SPLIT_FOOD_LIFETIME)
            return true;
        double dTime = System.currentTimeMillis() - lastUpdate;

        int newX = (int)(getX() + dTime*getVelocity().getX());
        int newY = (int)(getY() + dTime*getVelocity().getY());

        if (newX < 0){
            newX = 0;
        }
        if (newX > GameConstants.FIELD_WIDTH)
            newX = GameConstants.FIELD_WIDTH;

        if (newY < 0)
            newY = 0;
        if (newY > GameConstants.FIELD_HEIGHT)
            newY = GameConstants.FIELD_HEIGHT;

        setX(newX);
        setY(newY);

        lastUpdate = System.currentTimeMillis();
        return false;
    }
}
