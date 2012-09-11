package team3329.systems.vision;

/**
 * Class to pan and tilt the camera as needed.
 * @author Noah Harvey
 */
import java.util.Timer;
import java.util.TimerTask;
import team3329.drive.controller.CustomJoystick;
import edu.wpi.first.wpilibj.Servo;

public class CameraServos {
    
    //hold the pan and tilt servos 
    private static Servo m_panServo;
    private static Servo m_tiltServo;
        
    //holds current anlges in radians
    private static double m_panAngle;
    private static double m_tiltAngle;
    
    public CameraServos(Servo tiltServo, Servo panServo)
    {
        this(tiltServo,panServo,90,90);
    }
    
    public CameraServos(Servo tiltServo, Servo panServo
            ,double panAngle, double tiltAngle)
    {
        Timer timer = new Timer();
        m_panServo = panServo;
        m_tiltServo = tiltServo;
        m_panAngle = panAngle;
        m_tiltAngle = tiltAngle;
        timer.schedule(new ServoTask(this),10,10);
    }
    
    //return the current angles of the servos in an array. 
    //index 0: panAngle index 1: tiltAngle;
    public double getTiltAngle()
    {
        return m_tiltServo.getAngle();
    }
    
    public double getPanAngle()
    {
        return m_panServo.getAngle();
    }
    
    //set the angles of the servos seperately 
    public static void setPanAngle(double angle)
    {
        m_panServo.setAngle(angle); 
    }
    
    public static void setTiltAngle(double angle)
    {
        m_tiltServo.setAngle(angle);
    }
    
    public void run()
    {
        if(CustomJoystick.getButton(CustomJoystick.leftJoystick,3) && getPanAngle() < 180) setPanAngle(1+getPanAngle());
        if(CustomJoystick.getButton(CustomJoystick.leftJoystick,2) && getPanAngle() > 0) setPanAngle(getPanAngle()-1);
        if(CustomJoystick.getButton(CustomJoystick.leftJoystick,5) && getTiltAngle() < 180) setTiltAngle(1+getTiltAngle());
        if(CustomJoystick.getButton(CustomJoystick.leftJoystick,4) && getTiltAngle() > 0) setTiltAngle(getTiltAngle()-1);
        //System.out.println("PAN "+getPanAngle());
        //System.out.println("TILT "+getTiltAngle());
    }
    

}
class ServoTask extends TimerTask
{
    private CameraServos ctrl;
    ServoTask(CameraServos ctrl)
    {
        this.ctrl = ctrl;
    }
    public void run()
    {
        ctrl.run();
    }
}