package team3329.drive.controller;

/**
 *
 * @author Noah Harvey
 */

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

import team3329.vector.*;
import team3329.util.*;

import java.util.Timer;
import java.util.TimerTask;

//----------------SINGLETON-----------------
public class CustomJoystick
{
    
    //singleton var
    private static CustomJoystick instance = null;
    
    //axis index constants
    public static final int kLEFT_X_AXIS = 1;
    public static final int kLEFT_Y_AXIS = 2;
    public static final int kRIGHT_X_AXIS = 4;
    public static final int kRIGHT_Y_AXIS = 5;
    public static final int kZ_AXIS = 3;
    public static final int kDPAD = 6;
    
    //axis to use for tank drive
    public static Joystick rightJoystick = null;
    public static Joystick leftJoystick = null;
    private static int leftAxis = kLEFT_Y_AXIS;
    private static int rightAxis = kRIGHT_Y_AXIS;
    
    //thresholds for axis
    //inex 0: min; index 1: max;
    public static double leftThreshold = 0;
    public static double rightThreshold = 0;
    private static boolean m_leftInvert = false;
    private static boolean m_rightInvert = false;
    
    //max distance per interval
    private static double m_distancePerInterval;
    private static long timePerInterval;
    
    //stores the input values in a queue to be read and used externally
    private static Queue coordinates = new Queue();
    
    //timer to keep track 
    public static Timer timer;  //timer task controller for joysticks
    
    public static CustomJoystick getInstance()
    {
       if(instance == null) instance = new CustomJoystick();
       return instance;
    }
    
    protected CustomJoystick()
    {;//dont do anything 
    
    }
    
    public static void setRightJoystick(int port)
    {
        rightJoystick = new Joystick(port);
    }
    
    public static void setLeftJoystick(int port)
    {
        leftJoystick = new Joystick(port);
    }
    
    public static void setTimePerInterval(long timePerInterval_seconds)
    {
        timePerInterval =  (long)timePerInterval_seconds*1000;
        m_distancePerInterval = RobotProperties.maxSpeed * timePerInterval;
    }
    
    public void startTimer()
    {
        timer = new Timer();
        timer.schedule(new JoystickTask(this), 0L, timePerInterval);
    }
    
    public static void stopTimer()
    {
        timer.cancel();
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
    
    //return a delta coordinate to go to
    //to calculate the actual coordinate add this methods return
    //to a logged coordinate
    public synchronized PolarVector calculateCoordinate()
    {
        double leftDistance = m_distancePerInterval*limit(leftJoystick.getRawAxis(leftAxis),leftThreshold);
        double rightDistance = m_distancePerInterval*limit(rightJoystick.getRawAxis(rightAxis),rightThreshold);
        
        if(m_leftInvert) leftDistance = -leftDistance;
        if(m_rightInvert) rightDistance = -rightDistance;
        
        double angle = (rightDistance - leftDistance)/RobotProperties.wheelContactWidth;
        double distance = (rightDistance+leftDistance)/2;
        
        return new PolarVector(distance,angle);
    }
    
    //pulls and returns the next coordinate in the queue 
    public PolarVector getNextCoordinate()
    {
        return (PolarVector)coordinates.pull();
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
    
    //return a button value given a joystick 
    public static boolean getButton(Joystick joystick, int button)
    {
        return joystick.getRawButton(button);
    }
    //periodically runs and stores the joystick input vectors 
    public synchronized void run()
    {
        coordinates.add(calculateCoordinate());       
    }
}

//have this class run its self 
class JoystickTask extends TimerTask
{
    private CustomJoystick jStick;
    public JoystickTask(CustomJoystick j)
    {
        this.jStick = j;
    }
    
    public void run()
    {
        jStick.run();
    }
}
