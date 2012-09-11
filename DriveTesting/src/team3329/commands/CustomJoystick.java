package team3329.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * 
@author Noah Harvey
 */
public class CustomJoystick
{
    //axis index constants for 360 controller
    //DOUBLE CHECK THESE VALUES BEFORE USING CODE FOR 2012-2013 SEASON
    public static final int kLEFT_X_AXIS = 1;
    public static final int kLEFT_Y_AXIS = 2;
    public static final int kRIGHT_X_AXIS = 4;
    public static final int kRIGHT_Y_AXIS = 5;
    public static final int kZ_AXIS = 3;
    public static final int kDPAD = 6;
    
    //axis to use for tank drive
    public static Joystick rightJoystick = null;
    public static Joystick leftJoystick = null;
    private static int leftAxis;
    private static int rightAxis;
    
    //thresholds for axis
    //inex 0: min; index 1: max;
    public static double leftThreshold = .023;
    public static double rightThreshold = .015;
    private static boolean m_leftInvert = true;
    private static boolean m_rightInvert = true;
    
    //==========================SINGLETON============================|
    private static CustomJoystick instance = null;
    
    public static CustomJoystick getInstance()
    {
       if(instance == null) instance = new CustomJoystick();
       return instance;
    }
    
    protected CustomJoystick()
    {;//dont do anything 
    }
    //===================================================================|
    
    public static void setRightJoystick(int port)
    {
        rightJoystick = new Joystick(port);
    }
    
    public static void setLeftJoystick(int port)
    {
        leftJoystick = new Joystick(port);
    }
    
    public static void setLeftAxis(int axis)
    {
        leftAxis = axis;
    }
    
    public static void setRightAxis(int axis)
    {
        rightAxis = axis;
    }
    
    //set the threshold of the left axis 
    public static void setLeftThreshold(double thrsld)
    {
        leftThreshold = thrsld;
    }
    
    //set the threshold of the right axis
    public static void setRightThreshold(double thrsld)
    {
        rightThreshold = thrsld;
    }
    
    //allow the left and right values to be inverted 
    //depending on the raw input of the joystick
    public static void leftInvert(boolean invert)
    {
        m_leftInvert = invert;
    }
    
    public static void rightInvert(boolean invert)
    {
        m_rightInvert = invert; 
    }
   
    //threshold the value 
    public double limit(double value,double thrsld)
    {        
        //if Threshold is 0 then return the proper value
        if(thrsld == 0) return value;
        
        thrsld = Math.abs(thrsld);
        
        double sign = 1.0;
        //stamp sign and compute absolute value of the input
        if(value < 0 )
        {
            sign = -1.0;
            value = -value;
        }

        //compute a filter on the value. if it is inside the threshold
        //then return 0
        //proportion the value to the new range and return as signed.
        if(value-thrsld <= 0){return 0;}
        else
        {
            return sign*(1/(1-thrsld))*(value-thrsld);
        }
    }
    
    //return the left and right axis for direct drive (if needed)
    public double getLeft()
    {
        double l = limit(leftJoystick.getRawAxis(leftAxis),leftThreshold);
        
        if(m_leftInvert) l =-l;
        return l;
    }
    
    public double getRight()
    {
        double r = limit(rightJoystick.getRawAxis(rightAxis),rightThreshold);
        if(m_rightInvert) r =-r;
        return r;
    }
    
    public static Joystick getRightJoystick() {return rightJoystick;}
    public static Joystick getLeftJoystick() {return leftJoystick;}
    
    //return a button value given a joystick 
    public static boolean getButton(Joystick joystick, int button)
    {
        return joystick.getRawButton(button);
    }
}

