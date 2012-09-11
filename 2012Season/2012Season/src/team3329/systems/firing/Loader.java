/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3329.systems.firing;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Ben Davenport-Ray
 */
public class Loader {
    Relay loadingRelay;
    
    public Loader(){
        loadingRelay = new Relay(1);
    }
    public Loader(Relay relay){
        loadingRelay = relay;
        loadingRelay.setDirection(Relay.Direction.kForward);       
    }
    
    public void startLoading(){
        loadingRelay.set(Relay.Value.kOn);
    }
    public void stopLoading(){
        loadingRelay.set(Relay.Value.kOff);
    }
}
