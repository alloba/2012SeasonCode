package team3329.systems.vision;

/**
 * Processes all image tasks 
 * @author Noah Harvey
 */
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.Image;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;

//----------------SINGLETON--------------------------------------
public class ImageProcessor
{
    //singleton instance var
    private static ImageProcessor instance = null;

    //the distance from the target given the height of the 
    //reflected area
    private double distancePerPixelHeight;
    private int x_Center;
    private int y_Center;
    CriteriaCollection cc;      // the criteria for doing the particle filter operation

    public static void init()
    {
        instance = new ImageProcessor();    
    }

    private ImageProcessor()
    {
        //Set the Axis Camera Settings
        //TODO: set settings if needed.
        try
        { 
           AxisCamera.getInstance("10.33.29.2");
           cc = new CriteriaCollection();      // create the criteria for the particle filter
           cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
           cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
           
        }
       catch(Exception e){ e.printStackTrace();}

    }
    
    public void processImage()
    {
        try
        {
        ColorImage image;                           
                image =  getCameraImage();   //change threshold to blue!!!!
                BinaryImage thresholdImage = image.thresholdRGB(120, 255, 30, 80, 20, 70);   // keep only blue objects
                BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles
                
                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
                for (int i = 0; i < reports.length; i++) {                                // print results
                    ParticleAnalysisReport r = reports[i];
                    System.out.println("Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
                    x_Center = r.center_mass_x;
                    y_Center = r.center_mass_y;
                    System.out.println("Particle: " + i + ":  Center of mass x: " + r.center_mass_x + "\n Center of mass y: "+r.center_mass_y);

                }

                /**
                 * all images in Java must be freed after they are used since they are allocated out
                 * of C data structures. Not calling free() will cause the memory to accumulate over
                 * each pass of this loop.
                 */
                filteredImage.free();
                convexHullImage.free();
                bigObjectsImage.free();
                thresholdImage.free();
                image.free();
        }
        catch(NIVisionException e)
        {
            e.printStackTrace();
        }
    }

    public static ImageProcessor getInstance()
    {
        if(instance == null) init();
        return instance;
    }

    //get the raw image from the camera
    //if exception is created then it will rea
    public ColorImage getCameraImage()
    {
        ColorImage image;
        try
        {
            image = AxisCamera.getInstance("10.33.29.11").getImage();
        } 
        catch (Exception ex){ex.printStackTrace(); image = null;}
            
        return image;
    }
    
    //save an image to the cRIO
    //use ftp to get the image
    public void saveImage(Image image)
    {
        try
        {
            NIVision.setWriteFileAllowed(true);
            image.write("/"+Timer.getFPGATimestamp()+".png");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
