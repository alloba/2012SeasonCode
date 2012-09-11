package team3329.systems.vision;

/**
 * Processes all image tasks 
 * @author Noah Harvey
 */
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class ImageProcessor
{

    private ColorImage currentImage = null;
    private ColorImage processedImage = null;

    public ImageProcessor()
    {
        ;//later
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
}
