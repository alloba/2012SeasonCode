package team3329.util;

/**
 * This class is used to contain each device on the robot that is needed in the 
 * program. This is to keep all devices static and neatly organized 
 * @author Noah Harvey
 */
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Relay;

import team3329.subsystems.drive.DriveMotor;

public class RobotDevices
{
    //DRIVE MOTORS
    public static DriveMotor leftDriveMotor;
    public static DriveMotor rightDriveMotor;
    
    //DRIVE ENCODERS
    public static Encoder leftEncoder;
    public static Encoder rightEncoder;
    
    //BRIDGE ARM
    public static Victor bridgeArm;
    
    //LOADING CONTROLL
    public static Relay loadingRelay;
    
    //SHOOTING CONTROLL
    public static Victor leftShootingVictor;
    public static Victor rightShootingVictor;
}
