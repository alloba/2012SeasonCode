package team3329.subsystems.drive;

/**
 * Handles the drive of the robot by pulling in the desired coordinates to
 * PID motor handlers
 * @author Noah Harvey
 */
import team3329.util.*;
import team3329.util.vector.*;
import team3329.util.RobotDevices;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Encoder;

public class CustomDrive
{
    //instance container

    private static CustomDrive instance = null;
    //describes whether the drive interface is in auto or direct drive
    private boolean m_override = false;
    private boolean m_enabled = false;
    //PID controller constants
    public static double Kp_left = .01;
    public static double Ki_left = .001;
    public static double Kd_left = 0;
    public static double Kp_right = .01;
    public static double Ki_right = .001;
    public static double Kd_right = 0;
    //PID controllers for each motor
    //only two are needed incase of a 4 motor drive
    //because two motors are coupled
    private PIDController m_leftMotorPID;
    private PIDController m_rightMotorPID;
    //errors for the pid controllers
    private double leftPIDError;
    private double rightPIDError;
    //store current left and right values for determining errors
    public static double leftSetPoint;
    public static double rightSetPoint;

    //===============SINGLETON========================================|
    public static CustomDrive getInstance()
    {
        if(instance == null) instance = new CustomDrive();
        return instance;
    }

    //init the left and right motor pid controllers
    //and the timer for the drive controllers
    protected CustomDrive()
    {
        
        //intialize encoders
        //sets the encoders to act as a distance process variable
        //for the PID controllers
        RobotDevices.leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        RobotDevices.rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);

        RobotDevices.leftEncoder.start();
        RobotDevices.rightEncoder.start();
        //create PID controllers
        m_leftMotorPID = new PIDController(Kp_left, Ki_left, Kd_left,
                RobotDevices.leftEncoder, RobotDevices.leftDriveMotor);
        m_rightMotorPID = new PIDController(Kp_right, Ki_right, Kd_right,
                RobotDevices.rightEncoder, RobotDevices.rightDriveMotor);

        m_leftMotorPID.enable();
        m_rightMotorPID.enable();
        
        m_leftMotorPID.setInputRange(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        m_rightMotorPID.setInputRange(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        
        m_leftMotorPID.setOutputRange(-1.0, 1.0);
        m_rightMotorPID.setOutputRange(-1.0, 1.0);
        
        leftSetPoint = 0;
        rightSetPoint = 0;
    }

    //====================================================================|
    //this method MUST be called externally and PERIODICALLY!!!
    //gets the next coordinate and sets the left and right motor values
    //if the current left and right values are within the correct tolerance
    //drive to the next coordinate
    public void driveNext()
    {
        if (onTarget() && m_enabled && !m_override)
        {
            //reset the encoder to compensate for error
            RobotDevices.leftEncoder.reset();
            RobotDevices.rightEncoder.reset();
            
            System.out.println("ON target");
            
            Vector2D c = Navigator.getInstance().getNextCoordinate();
            //if there is no other coordinates then reset and do nothing
            if(c == null) { reset();return;}
            
            double angle = c.getAngle();
            double distance = c.getDistance();
            double wheelWidth = RobotProperties.wheelContactWidth;

            //get left and right distances
            double rightDistance = ((wheelWidth * angle) + (2 * distance)) / 2;
            double leftDistance = ((2 * distance) - (wheelWidth * angle)) / 2;

            rightSetPoint = rightDistance;
            leftSetPoint = leftDistance;
            
            m_leftMotorPID.setSetpoint(leftDistance);  //send distances to PID
            m_rightMotorPID.setSetpoint(rightDistance); //loops that control the motor ouput
           
        }
       //System.out.println(); -->leave this here for PID loop testing. 
    }

    //drives the robot according to the current drive values
    public void drive(double l, double r)
    {
        if (m_override && m_enabled)
        {
            RobotDevices.leftDriveMotor.set(l);
            RobotDevices.rightDriveMotor.set(r);
        }
    }

    //returns whether the robot itself is at its current setPoints
    private boolean onTarget()
    {
        leftPIDError = Math.abs(m_leftMotorPID.getError());//(Math.abs(m_leftMotorPID.get() - leftSetPoint)) / leftSetPoint;
        rightPIDError = Math.abs(m_rightMotorPID.getError()); //(Math.abs(m_rightMotorPID.get()-rightSetPoint)) / rightSetPoint;
        
        System.out.println("left: "+leftPIDError);
        System.out.println("right: "+rightPIDError);


        return (leftPIDError <= 1 && rightPIDError <= 1);
    }
    
    //set whether the drive can drive or not
    public void setEnabled(boolean v){m_enabled = v;}
	//allow everyone to see whether we're driving
    public boolean isEnabled(){return m_enabled;}

    //reset the drive encoders and PID loops
    public void reset()
    {
        RobotDevices.leftEncoder.reset();
        RobotDevices.rightEncoder.reset();

        //set the setpoints to 0 so the system doesn't go wacky when it is
        //started back up
        m_leftMotorPID.setSetpoint(0);
        m_rightMotorPID.setSetpoint(0);
        rightSetPoint = 0;
        leftSetPoint = 0;

        Navigator.getInstance().resetCoordinateList();
        Navigator.getInstance().resetHeading();
    }
    
    //safety code that stops the PID loops immediately and clears the
    //drive coordinates
    public void stopNow()
    {
        setEnabled(false);
        m_leftMotorPID.disable();
        m_rightMotorPID.disable();

        RobotDevices.leftEncoder.stop();
        RobotDevices.rightEncoder.stop();

        reset();
    }
    
    //compliment of stopNow()
    //starts the drive again
    public void startNow()
    {
        setEnabled(true);
        RobotDevices.leftEncoder.start();
        RobotDevices.rightEncoder.start();

        m_leftMotorPID.enable();
        m_rightMotorPID.enable();
    }

    //allows usr to change the drive between automode (uses drive PID and coordinates)
    // or override mode(allows usr to directly specify motor output)
    public void setOverride(boolean setting)
    {
        //if we are setting to direct drive then stop the auto drive immediately
        if (!m_override && setting)
        {
            stopNow();
        }
        else
        {
            if (m_override && !setting)
            {
                startNow();
            }
        }

        m_override = setting;
    }
}
