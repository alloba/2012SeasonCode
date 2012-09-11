package team3329.util;

/**
 *
 * @author Noah Harvey
 */
public class CoupledData
{
    double x;
    double y;
    double z;

    public CoupledData(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoupledData(double x, double y)
    {
        this.x = x;
        this.y = y;
        this.z = Double.NaN;
    }

    public double getX(){ return x;}
    public double getY(){ return y;}
    public double getZ(){ return z;}
}
