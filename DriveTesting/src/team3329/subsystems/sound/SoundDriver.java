/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3329.subsystems.sound;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PWM;

/**
 *
 * @author Developer
 */
public class SoundDriver
{
    PWM port;
    
    public SoundDriver()
    {
        port = new PWM(6);
    }
    
    public void sendPulse(){
        ;
    }
    
    public void send(int i)
    {
        port.setRaw(i);
    }
}
