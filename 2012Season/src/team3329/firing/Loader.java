/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3329.systems.firing;

import edu.wpi.first.wpilibj.Relay;

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
    }
    
    public void beginLoading(){
        loadingRelay.set(Relay.Value.kOn);
    }
    public void endLoading(){
        loadingRelay.set(Relay.Value.kOff);
    }
}
