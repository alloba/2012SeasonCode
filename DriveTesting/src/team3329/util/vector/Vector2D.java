package team3329.util.vector;

import com.sun.squawk.util.MathUtils;

/**
 * contains operations operations for vectors
 * @author Noah Harvey
 */
public class Vector2D
{
    //keep all data in polar form
    private double angle;
    private double distance;
    
    //make the main contstructor hidden
    //and use the following methods instead
    public Vector2D()
    {
        angle = 0;
        distance = 0;
    }
    
    public static Vector2D PolarVector(double mag, double angle)
    {
        Vector2D temp = new Vector2D();
        temp.setAngle(angle);
        temp.setDistance(mag);
        return temp;
    }
    
    public static Vector2D CartesianVector(double x, double y)
    {
        Vector2D temp = new Vector2D();
        temp.setAngle(MathUtils.atan2(y, x));
        temp.setDistance(MathUtils.pow(x*x + y*y, .5));
        return temp;
    }
    
    public void setX(double x)
    {
        this.angle = MathUtils.atan2(this.getY(),x);
        this.distance = x/Math.cos(angle);
    }
    
    public void setY(double y)
    {
        this.angle = MathUtils.atan2(y,this.getX());
        this.distance = y/Math.sin(angle);
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public void setDistance(double mag)
    {
        this.distance = mag;
    }
    
    public double getX()
    {
        return distance*Math.cos(angle);
    }
    
    public double getY()
    {
        return distance*Math.sin(angle);
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public double getDistance()
    {
        return distance;
    }
}
