package team3329.systems.arm;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;

public class BridgeArm{

    private Victor bridgeArm;
    private boolean bridgeState;
    
    public BridgeArm(Victor arm){
        bridgeArm = arm;
        bridgeState = false;
    }

    /*public void changeBridgeState()
    {
        if(bridgeState)
        {
            bridgeState = false;
            bridgeArm.set(0.5);
            Timer.delay(1);
            bridgeArm.set(0);
        }
        else if(!bridgeState)
        {
            bridgeState = true;
            bridgeArm.set(-0.5);
            Timer.delay(.5);
            bridgeArm.set(0);
        }
    }*/
    
    public void setUpBridge()
    {
         bridgeArm.set(0.5);
            Timer.delay(.25);
            bridgeArm.set(0);
    }
    public void setDownBridge()
    {
        
            bridgeArm.set(-0.5);
            Timer.delay(.25); 
            bridgeArm.set(0);
    }
}
