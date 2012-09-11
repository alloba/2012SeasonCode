package team3329.subsystems.arm;

import edu.wpi.first.wpilibj.Timer;
import team3329.util.RobotDevices;

public class BridgeArm{
    
    public void setUpBridge()
    {
         RobotDevices.bridgeArm.set(0.5);
            Timer.delay(.25);
            RobotDevices.bridgeArm.set(0);
    }
    public void setDownBridge()
    {
        
            RobotDevices.bridgeArm.set(-0.5);
            Timer.delay(.25); 
            RobotDevices.bridgeArm.set(0);
    }
}
