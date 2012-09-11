package team3329;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;

import edu.wpi.first.wpilibj.Victor;
import team3329.util.DriverScreen;
import team3329.drive.*;
import team3329.drive.controller.CustomJoystick;
import team3329.systems.firing.*;
import team3329.util.RobotProperties;
import team3329.vector.*;
import team3329.systems.vision.*;
import team3329.systems.arm.*;
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
    public final double distance_per_count = .038226705;
    public final int digitalChannel = 1;

    //non-device variables
    public CustomDrive customDrive;
    public Loader loader;
    public Shooter shooter;
    public BridgeArm arm;
    //allow the driver to lower sensitivity
    public boolean lowerSensitivity = false;
    
    //device variables
    //sensors
    Encoder lEncoder;
    Encoder rEncoder;
    
    CustomJoystick joystick;
    
    //drive speedControllers
    public DriveMotor lDriveMotor;
    public DriveMotor rDriveMotor;
    
    //vision 
    CameraServos camServos;

    //is autonomous complete?
    private boolean auto;
    
    /*
     * Robot init code. Override from IterativeRobot
     */
    public void robotInit()
    {
        DriverScreen.printLog("---INIT ROBOT DEVICES---");
        DriverScreen.printLog("---Configuring Drive Devices---");

        lDriveMotor = new DriveMotor(new Jaguar(1),true);
        rDriveMotor = new DriveMotor(new Jaguar(2),false);
        
        //init HID
        joystick = CustomJoystick.getInstance();
        joystick.setLeftJoystick(1);
        joystick.setRightJoystick(2);
        joystick.setLeftAxis(2);
        joystick.setRightAxis(2);
        joystick.setRightThreshold(0.0390625);
        joystick.setLeftThreshold(0.0390625);
        joystick.rightInvert(false);
        joystick.leftInvert(false);
        
        //init program controllers
        //custom drive code
        Navigation.init(new Encoder(3,4,true),new Encoder(1,2,false));
	customDrive = new CustomDrive(lDriveMotor, rDriveMotor);
        
       //firing and loading sytems 
        //DO NOT CHANGE VALUES!!!!!!!!!!!!
       shooter = new Shooter(new Victor(7), new Victor(6));
       loader = new Loader(new Relay(1));
       arm = new BridgeArm(new Victor(5));

       //init servos for camera


       camServos = new CameraServos(new Servo(4), new Servo(3),90,90);
       //init Axis camera
       //ImageProcessor.init();
       
    }

   /*
    * This function is called periodically during operator control
    */
    public void teleopPeriodic()
    {
        //robotConfig(-1);
        //because the controller only returns a delta vector
        
        //Navigation.getInstance().addNextCoordinate(joystick.getNextCoordinate());
        
        //lower the sensitivity if the driver wants to
        double l = joystick.getLeft();
        double r = joystick.getRight();
        
        if(joystick.getButton(CustomJoystick.rightJoystick, 3));
        {
            l=l*.5;
            r=r*.5;
        }
        customDrive.drive(l,r);
        
        //SHOOTING AND LOADING
        if(CustomJoystick.getButton(CustomJoystick.rightJoystick,1)) 
        { 
            shooter.startFiring(); 
        }
        else
        { 
            shooter.stopFiring();  
        }
        
        if(CustomJoystick.getButton(CustomJoystick.leftJoystick,1)) 
        {
            loader.startLoading();
        }
        
        else
        {  
            loader.stopLoading(); 
        }
        
        //BRIDGE ARM CONTROL
        if(CustomJoystick.getButton(CustomJoystick.rightJoystick, 3))
        {
            arm.setUpBridge();
        }
        
        if(CustomJoystick.getButton(CustomJoystick.rightJoystick, 2))
        {
            arm.setDownBridge();
        }
        
        //System.out.println(Navigation.m_rightEncoder.getDistance());

        
        //DO NOT REMOVE THIS METHOD CALL
        //Update the driver station
        // DriverScreen.updateDriverStation();
    }
    
    public void teleopInit()
    {
        DriverScreen.printLog("-------TELEOP INIT------");
        Navigation.getInstance().reset();
        customDrive.setOverride(true);
       
        //for(int i=0;i<51;i++)
        //{
         //   ImageProcessor.getInstance().saveImage(ImageProcessor.getInstance().getCameraImage());
        //    Timer.delay(50);
        //}
       
        //config the robot properties
        //robotConfig(1);
        //System.out.println("Phase 2");
        /*Timer.delay(2);
        robotConfig(2);
        customDrive.setOverride(false);*/
    }
 
    public void autonomousInit(){
        DriverScreen.printLog("Autonomous initialized.");
        Navigation.getInstance().reset();
        Navigation.getInstance().addNextCoordinate(new CartesianVector(-(12*5), 0));
        
    }
    
    public void autonomousPeriodic(){
        customDrive.driveNext();
    }
    
    //use a statemachine to config the robot
    //  phase 0 is to be done manually!: config encoders
    //  1. enable teleop and push the robot a certain distance
    //  2. determine distance per count by dividing distance by encoder count
    //  REPEAT A NUMBER OF TIMES THEN AVERAGE RESULTS

    public void robotConfig(int phase)
    {
        customDrive.setOverride(true);
        if(isEnabled())
        {
            if(phase == -1)
            {
                System.out.println("RightEncoder: "+Navigation.m_leftEncoder.getRaw());
            }
            
            if(phase == 0)
            {
                System.out.println("RightEncoder: "+Navigation.m_rightEncoder.getRaw());
            }
            //phase one: determine the max speed of the robot
            if(phase == 1)
            {
                //run the motors for 20 seconds and get the speed from the encoders
                //average the speeds to get max speed
                
                double speedData = 0;
                for(int i=0;i<80;i++)
                {
                    customDrive.drive(1, -1);
                    Timer.delay(.05);
                    System.out.println("l: "+Navigation.m_leftEncoder.getRate());
                    System.out.println("R: "+Navigation.m_rightEncoder.getRate());
                    speedData += Navigation.getInstance().getHeadingSpeed();
                }
                
                customDrive.drive(0, 0);

                RobotProperties.maxSpeed = speedData/200;
            }

            //phase 2: determine max accel
            else if(phase == 2)
            {
                //almost the same as phase one. Max accel is determined
                //through starting at 0 velocity and quickly going up to max speed
                //and measuring the time it takes to get there 
                double accelData = 0;

                for(int i = 0;i<20;i++)
                {
                    //go from 0 to max and record the change in time
                    customDrive.drive(0, 0);
                    //time 1 in seconds
                    long t2 = 0;
                    long t1 = System.currentTimeMillis()/1000;
                    customDrive.drive(1, 1);

                    double speed = Navigation.getInstance().getHeadingSpeed();

                    //only set time 2 if we're at max speed
                    if(speed >= RobotProperties.maxSpeed-.01 && speed <= RobotProperties.maxSpeed+.01)
                    {
                        t2 = System.currentTimeMillis()/1000;
                        accelData += RobotProperties.maxSpeed/(t2-t1);
                    }
                    
                    customDrive.drive(0, 0);
                    Timer.delay(1);
                }

                RobotProperties.maxAccel = accelData/21;
                System.out.println(RobotProperties.maxAccel);
            }

        }
    }
}
