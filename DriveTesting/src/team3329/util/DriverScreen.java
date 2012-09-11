package team3329.util;

/**
 * This class allows all classes to access the driver screen and print as needed
 * @author Noah Harvey
 */
import edu.wpi.first.wpilibj.DriverStationLCD;
import java.io.DataOutputStream;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStreamReader;

import javax.microedition.io.Connector;
import java.util.Vector;


public class DriverScreen
{
     //================SINGLETON=============================|
    private static DriverScreen instance = null;

    public static DriverScreen getInstance()
    {
        return instance == null ? instance = new DriverScreen() : instance;
    }

    private DriverScreen()
    {
    }

    //=======================================================|
    //for now this class only prints to the driverLCD
    public static void printLog(String log, int line)
    {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, log);

    }

    //call this method periodically to update the driver station
    public static void updateDriverStation()
    {
        DriverStationLCD.getInstance().updateLCD();
    }

    //write a data string to the cRIO
    public static void writeData(String data,String fileName)
    {
        printLog("Attempting to write data to CRIO",3);
        DataOutputStream file;
        try
        {
            FileConnection fc = (FileConnection)Connector.open("file:///"+fileName,Connector.WRITE);
            fc.create();
            file = fc.openDataOutputStream();
            file.writeChars(data);
            file.flush();
            file.close();
            fc.close();
        }
        catch(Exception e){ e.printStackTrace(); printLog(e.getMessage(),3);}
    }
    
    //read a csv file from the cRIO and return it as a string
    public static String readData(String fileName)
    {
       printLog("READING DATA FROM: /"+fileName,3);
       DataInputStream file;
       Vector values = new Vector();
       //open the file, and read the characters from the file and store them in
       //a vector
       try
       {
           file = (DataInputStream)Connector.openInputStream("file:///"+fileName);
           InputStreamReader reader = new InputStreamReader(file);
           
           int data = reader.read();
           while(data != -1)
           {
               char value = (char)reader.read();
               values.addElement(new Character(value));
               System.out.print(value);
               data = reader.read();
           }
           reader.close();
           file.close();
           //fc.close();
       }
       catch(Exception e)
       {
           e.printStackTrace(); printLog(e.getMessage(),3);
           //if the exception is something other than the end of file exception
           //-which signifies that the file reading is completed-then return a null string
           
           if(e.getClass() != EOFException.class) return null;
       }
       
       //then read the chars from the vector and concenate them into a string
       String text;
       char[] buffer = new char[values.size()];
       for(int i=0; i<values.size(); i++)
       {
           buffer[i] = ((Character)values.elementAt(i)).charValue();
       }
       text = new String(buffer);
       return text;
    }
}
