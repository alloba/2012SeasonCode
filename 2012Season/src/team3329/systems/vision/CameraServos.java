package team3329.systems.vision;

/**
 * Class to pan and tilt the camera as needed.
 * @author Noah Harvey
 */

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
        m_panServo = panServo;
        m_tiltServo = tiltServo;
        m_panAngle = panAngle;
        m_tiltAngle = tiltAngle;
    }
    
    //return the current angles of the servos in an array. 
    //index 0: panAngle index 1: tiltAngle;
    public static double[] getAngles()
    {
        double[] angles = {m_panAngle, m_tiltAngle};
        return angles;
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
}
