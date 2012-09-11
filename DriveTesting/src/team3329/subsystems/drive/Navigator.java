package team3329.subsystems.drive;

/**
 * This class is to act as the navigator of the robot. It knows where the robot
 * is at all times and contains the coordinateList of where it needs to go next
 * @author Noah Harvey
 */

import team3329.util.vector.*;
import team3329.util.Queue;
import team3329.util.RobotDevices;
import team3329.util.RobotProperties;

//ALL FUNCTIONALITY OF THIS CLASS HAS BEEN TESTED AND VERIFIED TO WORK. 
public class Navigator
{
    //the robot's current heading
    private Vector2D heading;
    
    //the robots list of coordinateList that it needs to go to 
    private Queue coordinateList;
    
    //==========================SINGLETON==================================|
    private static Navigator instance = null;
    
    public static void init()
    {
        instance = new Navigator(new Vector2D());
    }

    public static void init(Vector2D h)
    {
	instance = new Navigator(h);
    }

    private Navigator(Vector2D h)
    {
        heading = h;
        coordinateList = new Queue();
    }

    public static Navigator getInstance()
    {
	return instance;
    }
    //=========================================================================|
    
    //reset the robot's current heading
    public void resetHeading()
    {
        heading = Vector2D.CartesianVector(0, 0);
    }
    
    //reset the list of coordinates so that the robot has no where to go
    public void resetCoordinateList()
    {
        coordinateList = new Queue();
    }
    
    //update the heading using the drive sensors
    private void updateHeading()
    {
        double lD = RobotDevices.leftEncoder.getDistance();//get distance from encoders
        double rD = RobotDevices.rightEncoder.getDistance();
        
        double angle = (rD - lD)/RobotProperties.wheelContactWidth;
        double distance = (rD + lD)/2;

        heading.setDistance(distance);
        heading.setAngle(angle);
    }
    
    //set the current heading manually 
    public void setHeading(Vector2D v)
    {
        heading = v;
    }
    
    //returns the current heading angle
    public Vector2D getHeading()
    {
	updateHeading();
        return heading;
    }
    
    //add another coordinate to the bottom of the coordinate list
    public void addCoordinate(Vector2D newHeading)
    {
        coordinateList.add(newHeading);
    }
    
    //pull the next available coordinate from the coordinate list 
    //if another coordinate can not be found then return null 
    public Vector2D getNextCoordinate()
    {
        Vector2D v = (Vector2D)coordinateList.pull();        
        if(v == null) return null;
        else return v;
        //return null;
    } 
}