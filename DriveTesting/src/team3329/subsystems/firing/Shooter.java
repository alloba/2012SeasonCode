/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3329.subsystems.firing;

import edu.wpi.first.wpilibj.Victor;
import team3329.util.RobotDevices;

/**
 *
 * @author Ben Davenport-Ray
 */
public class Shooter {
    
    public void startFiring(){
        RobotDevices.leftShootingVictor.set(-1.0);
        RobotDevices.rightShootingVictor.set(1.0);
    }
    
    public void stopFiring(){
        RobotDevices.leftShootingVictor.set(0);
        RobotDevices.rightShootingVictor.set(0);
    }
}
