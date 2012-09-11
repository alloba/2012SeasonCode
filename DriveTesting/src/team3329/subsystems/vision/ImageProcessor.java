package team3329.subsystems.vision;

/**
 * Processes all image tasks 
 * @author Noah Harvey
 */
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.NIVision;

public class ImageProcessor
{

    private ColorImage currentImage = null;
    private ColorImage processedImage = null;

    public ImageProcessor()
    {
        //later
    }

    //get the raw image from the camera
    //if exception is created then it will rea
    public ColorImage getCameraImage()
    {
        ColorImage image;
        try
        {
            image = AxisCamera.getInstance().getImage();
        } 
        catch (Exception ex){ex.printStackTrace(); image = null;}
            
        return image;
    }
    
    public void saveImage(String name)
    {
        ColorImage image = getCameraImage();
        try
        {
            NIVision.setWriteFileAllowed(true);
            image.write(name);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveImage(){saveImage("/"+Timer.getFPGATimestamp()+".png");}
}
