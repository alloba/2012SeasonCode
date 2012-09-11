/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3329.subsystems.firing;

import team3329.util.RobotDevices;
import edu.wpi.first.wpilibj.Relay;
/**
 *
 * @author Ben Davenport-Ray
 */
public class Loader {
    
    public Loader()
    {
        RobotDevices.loadingRelay.setDirection(Relay.Direction.kForward);
    }
    
    
    public void beginLoading(){
        RobotDevices.loadingRelay.set(Relay.Value.kOn);
    }
    public void endLoading(){
        RobotDevices.loadingRelay.set(Relay.Value.kOff);
    }
}
