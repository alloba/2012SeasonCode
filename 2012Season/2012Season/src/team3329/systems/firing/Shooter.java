/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3329.systems.firing;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Ben Davenport-Ray
 */
public class Shooter {
    
    Victor leftControl;
    Victor rightControl;
    
    public Shooter(){
        this.leftControl = new Victor(6);
        this.rightControl = new Victor(7);
    }
    
    public Shooter(Victor leftControl, Victor rightControl)
    {
        this.leftControl = leftControl;
        this.rightControl = rightControl;   
        System.out.println(this);
    }
    
    public void startFiring()
    {
        leftControl.set(1.0);
        rightControl.set(-1.0);
        System.out.println("Started firing");
    }
    
    public void stopFiring()
    {
        leftControl.set(0.0);
        rightControl.set(0.0);
        System.out.println("Stopped firing");
    }
}
