package team3329;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import team3329.util.DriverScreen;
import team3329.drive.*;
import team3329.systems.firing.*;
import team3329.vector.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot
{

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    //config vars
    public final double wheelContactWidth = 20.5;
    public final double dpc = .038226705;
    public final int digitalChannel = 1;

    //non-device variables
    public CustomDrive customDrive;
    public FiringSystem firingMechanism;
    public Shooter shootingMech;
    public Loader loadingMech;
    
    //device variables
    //sensors
    Encoder lEncoder;
    Encoder rEncoder;
    
    //drive speedControllers
    public DriveMotor lDriveMotor;
    public DriveMotor rDriveMotor;

    //shooting mechanism devices
    public Relay l_shootingRelay;
    public Relay r_shootingRelay;
    public Relay loadingRelay;
    
    //human interface devices (HID)
    public Joystick joystick1;

    /*
     * Robot init code. Override from IterativeRobot
     */
    public void robotInit()
    {
        DriverScreen.printLog("---INIT ROBOT DEVICES---");
        DriverScreen.printLog("---Configuring Drive Devices---");

        //init devices
        lEncoder = new Encoder(5,6);
        rEncoder = new Encoder(7,8);

        lDriveMotor = new DriveMotor(new Jaguar(1),true);
        rDriveMotor = new DriveMotor(new Jaguar(2),false);
        
        //init HID
        this.joystick1 = new Joystick(1);  //later the HID will be a custom joystick
                                      	   //that will work with the customDrive
        
        //init program controllers
        //custom drive code
	customDrive = new CustomDrive(lDriveMotor, rDriveMotor
                ,lEncoder,rEncoder);
        
       //start navigation class
       Navigation.init(wheelContactWidth, lEncoder, rEncoder);
        
       //firing and loading sytems 
       //firingMechanism = new FiringSystem();
        shootingMech = new Shooter(new Relay(2),new Relay(3));
        loadingMech = new Loader(new Relay(1));  
        //init Axis camera
        AxisCamera.getInstance().writeResolution(AxisCamera.ResolutionT.k640x480);   
    }

   /*
    * This function is called periodically during operator control
    */
    public void teleopPeriodic()
    {
        //customDrive.arcadeDrive(joystick1);  
        //Navigation.getInstance().addNextCoordinate(new CartesianVector(80,80));
        //System.out.println();
        lDriveMotor.set(1);
        rDriveMotor.set(1);
        
        //DO NOT REMOVE THIS METHOD CALL
        //Update the driver station
        DriverScreen.updateDriverStation();
    }
 	
    /*
     * call just before running init
     */
    public void teleopInit()
    {
        //customDrive.setOverride(true); //allow direct voltage control
        //Navigation.getInstance().addNextCoordinate(new CartesianVector(100,100));
       
    }

    public void robotConfig()
    {
        ;//later mess with robot config stuff
    }
}
