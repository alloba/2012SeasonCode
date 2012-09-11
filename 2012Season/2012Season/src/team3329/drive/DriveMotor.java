package team3329.drive;

/**
 * Required to invert direction on motor ouput
 * Also couples motors to allow a 4 motor drive 
 * @author Noah Harvey
 */

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.PIDOutput;

public class DriveMotor implements PIDOutput
{
    //different motor sides
    public static final int kLeftMotor = 1;
    public static final int kRightMotor = 2;

    //different speed controller types available
    public static final int kJag = 3;
    public static final int kVictor = 4;

    //allow external classes to use a Victor or jaguar for motor controllers
    //store controllers in an array of size
    private Jaguar[] m_Jag;
    private Victor[] m_Victor;
    
    //current motor side
    private int m_motorSide;

    //current speed controller type
    private int m_speedControllerType;

    //for debugging purposes store the current motor value into a var
    private double currentMotorValue = 0;

    //two constuctors are used to compensate for the
    //choice of speed controller type
    public DriveMotor(Jaguar spdCtlr, boolean leftMotor)
    {
        m_Jag = new Jaguar[1];
        if(spdCtlr == null) System.out.println("NO MOTOR!");
        m_Jag[0] = spdCtlr;
        m_speedControllerType = kJag;
        init(leftMotor);
    }

    public DriveMotor(Victor spdCtlr, boolean leftMotor)
    {
        m_Victor[0] = spdCtlr;
        m_speedControllerType = kVictor;
        init(leftMotor);
    }

    public DriveMotor(Jaguar spdCtlr1, Jaguar spdCtlr2, boolean leftMotor)
    {
        m_Jag[0] = spdCtlr1;
        m_Jag[1] = spdCtlr2;
        m_speedControllerType = kJag;
        init(leftMotor);
    }

    public DriveMotor(Victor spdCtlr1, Victor spdCtlr2, boolean leftMotor)
    {
        m_Victor[0] = spdCtlr1;
        m_Victor[1] = spdCtlr2;
        m_speedControllerType = kVictor;
        init(leftMotor);
    }

    private void init(boolean leftMotor)
    {
        //to restrict calling classes from passing incorrect values
        //determine whether the motor is the left one or the right one
        //using a boolean value: l = true r = false
        if(leftMotor)  m_motorSide = kLeftMotor;
        if(!leftMotor) m_motorSide = kRightMotor;
    }

    public synchronized void set(double value)
    {
        //if the motor is on the reverse side then negate the value
        //the default reverse side is the left side
        if(m_motorSide == kRightMotor) value = -value;

        //set the current motor var
        currentMotorValue = value;

        //determine what type of speed controller is being used
        if(m_speedControllerType == kJag)
        {
            //if two motors are coupled then set the second one as well
            //as the first one, else just set the first
            if(m_Jag.length == 2) m_Jag[1].set(value);
            m_Jag[0].set(value);
        }
        else if(m_speedControllerType == kVictor)
        {
            if(m_Victor.length == 2) m_Victor[1].set(value);
            m_Victor[0].set(value);
        }
    }
    
    //return the current motor value
    public synchronized double getMotorValue()
    {
        return currentMotorValue;
    }

    //sets the motor value given an output from an external PIDController
    public void pidWrite(double value)
    {
        set(value);
    }
}
