package team3329;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import team3329.commands.CustomJoystick;
import team3329.util.RobotDevices;
import team3329.util.vector.*;
import team3329.util.*;
import team3329.subsystems.drive.*;
import team3329.subsystems.firing.*;
import team3329.util.DriverScreen;
import team3329.util.RobotProperties;
import team3329.subsystems.vision.ImageProcessor;
import edu.wpi.first.wpilibj.camera.AxisCamera;
//import team3329.commands.CustomJoystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot
{

    double PI = Math.PI;
    Shooter shooter;
    Loader loader;
    
    private static ImageProcessor imager;
    private static String teleopNotice;
    private static String autoNotice;
    private static CoupledDataContainer teleopData;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

    public void robotInit()
    {
        // instantiate robot devices
        RobotDevices.rightEncoder = new Encoder(1, 2);
        RobotDevices.leftEncoder = new Encoder(3, 4);
        RobotDevices.leftEncoder.setDistancePerPulse(RobotProperties.lDistancePerCount);
        RobotDevices.rightEncoder.setDistancePerPulse(RobotProperties.rDistancePerCount);
        RobotDevices.leftEncoder.setReverseDirection(true);
        
        RobotDevices.leftDriveMotor = new DriveMotor(new Jaguar(1), true);
        RobotDevices.rightDriveMotor = new DriveMotor(new Jaguar(2), false);
        
        RobotDevices.leftShootingVictor = new Victor(8);
        RobotDevices.rightShootingVictor = new Victor(7);
        RobotDevices.loadingRelay = new Relay(1);
        shooter = new Shooter();
        loader = new Loader();

        // Initialize all subsystems
		//Navigator
        Navigator.getInstance().init();
		//CustomDrive
        CustomDrive.getInstance();
		//Joystick
        CustomJoystick.getInstance().setLeftJoystick(1);
        CustomJoystick.getInstance().setRightJoystick(2);
        CustomJoystick.getInstance().setLeftAxis(2);
        CustomJoystick.getInstance().setRightAxis(2);
        
        AxisCamera.getInstance();
        imager = new ImageProcessor();
        DriverScreen.getInstance();
        teleopData = new CoupledDataContainer("LeftJoystickValues","RightJoystickValues","Time");
        teleopNotice ="";
    }
    
    public void disabledInit()
    {
		//keep the drive system from skrewing up when going into disabled mode
        CustomDrive.getInstance().stopNow();
        
    }
    
    public void autonomousInit()
    {
            for(int i=0;i<6;i++)
            {
                imager.saveImage("/RelayImage"+i+".png");
                Timer.delay(.8);
            }
        //DriverScreen.writeData(teleopData.getCVS(),"JoystickValues.csv");
        
        //DriverScreen.printLog(DriverScreen.readData("test.csv"),1);
		//if the drive is disabled then go ahead and re-enable it
        //if(!CustomDrive.getInstance().isEnabled()) CustomDrive.getInstance().startNow();
		//set the drive to autonomous enhanced driving :D
       /* CustomDrive.getInstance().setOverride(false);
       
        //set list of coordinates for diagnostic test
        //first move foward
        Navigator.getInstance().addCoordinate(Vector2D.PolarVector(12, 0));//drive forward
        Navigator.getInstance().addCoordinate(Vector2D.PolarVector(-12, 0)); //then backward
        //Navigator.getInstance().addCoordinate(Vector2D.PolarVector(0, PI/2)); //face right
        //Navigator.getInstance().addCoordinate(Vector2D.PolarVector(0, 0));//return
        //Navigator.getInstance().addCoordinate(Vector2D.PolarVector(0, -PI/2));//face left
        //Navigator.getInstance().addCoordinate(Vector2D.PolarVector(0, 0));//return*/
    }
    
    

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        //run the automated drive system
       // CustomDrive.getInstance().driveNext();
        //DriverScreen.printLog("AUTON: "+autoNotice, 2);
    }

    public void teleopInit()
    {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        CustomDrive.getInstance().setOverride(true);
        CustomDrive.getInstance().setEnabled(true);
        teleopData.resetData();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
	//allow the user to drive the robot manually
	//This will be replaced with code to allow auto-enhanced driving for the bot
        double l = CustomJoystick.getInstance().getLeft();
        double r = CustomJoystick.getInstance().getRight();
        CustomDrive.getInstance().drive(l,r);
        
        //controller mappings
        //SHOOTER, BUTTON 1 = trigger
        if(CustomJoystick.getButton(CustomJoystick.getRightJoystick(), 1))
            shooter.startFiring();
        else shooter.stopFiring();
        //LOADER
        if(CustomJoystick.getButton(CustomJoystick.getLeftJoystick(), 1))
            loader.beginLoading();
        else loader.endLoading();
        
        teleopData.appendData(new CoupledData(l,r,Timer.getFPGATimestamp()));
        //DriverScreen.getInstance().printLog("TELEOP: "+teleopNotice,1);
        DriverScreen.getInstance().updateDriverStation();
    }
}
