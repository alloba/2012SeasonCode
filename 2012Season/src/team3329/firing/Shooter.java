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
public class Shooter {
    
    Relay leftRelay;
    Relay rightRelay;
    
    public Shooter(){
        this.leftRelay = new Relay(2);
        this.rightRelay = new Relay(3);
        this.leftRelay.setDirection(Relay.Direction.kReverse);
    }
    
    public Shooter(Relay leftRelay, Relay rightRelay){
        this.leftRelay = leftRelay;
        this.rightRelay = rightRelay;   
        this.leftRelay.setDirection(Relay.Direction.kReverse);
    }
    
    public void startFiring(){
        leftRelay.set(Relay.Value.kOn);
        rightRelay.set(Relay.Value.kOn);
    }
    
    public void stopFiring(){
        leftRelay.set(Relay.Value.kOff);
        rightRelay.set(Relay.Value.kOff);
    }
}
