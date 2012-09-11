package team3329.util;

/**
 * container to hold coupled data in CVS form for 3d data
 * @author Noah harvey 
 */

public class CoupledDataContainer
{
    String dataXHead;
    String dataYHead;
    String dataZHead;
    String cvs;

    public CoupledDataContainer(String xHead, String yHead, String zHead)
    {
        this.dataXHead = xHead;
        this.dataYHead = yHead;
        this.dataZHead = zHead;
        this.cvs = dataXHead + "," + dataYHead + "," + dataZHead+"\n";
    }
    //adds data to the end of the current data list

    public void appendData(CoupledData d)
    {
        cvs += d.getX() + "," + d.getY() + "," + d.getZ() + "\n";
    }
    //gets the CVS formated string for the data

    public String getCVS()
    {
        return cvs;
    }
    
    public void resetData(){this.cvs = new String();}
}
