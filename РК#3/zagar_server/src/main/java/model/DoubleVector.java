package model;

/**
 * Created by venik on 08.12.16.
 */
public class DoubleVector {
    private  double x;
    private  double y;

    public DoubleVector(){}

    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DoubleVector(DoubleVector doubleVector) {
        this.x = doubleVector.x;
        this.y = doubleVector.y;
    }

    public double getX(){return x;}
    public double getY(){return y;}

    public DoubleVector setX(double x){
        this.x = x;
        return this;
    }

    public DoubleVector setY(double y){
        this.y = y;
        return this;
    }
}

