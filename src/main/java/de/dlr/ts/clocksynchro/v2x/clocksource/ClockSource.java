package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.Config;

/**
 * Defines the source of time 
 * 
 */
public class ClockSource
{
    private static final ClockSource INSTANCE = new ClockSource();
    private ClockSourceInterface csi;
    
    private long deltaTime;
    
    
    private ClockSource() {
    }
    
   /**
    * Differentiates between generator message and terminal message
    * 
    */
    public void start()
    {
        if(Config.getInstance().getStationType() == Config.StationType.GENERATOR)
        {
            csi = new GPSSource();
            csi.start();
        }
        else
        {
            csi = new V2XSource();
            csi.start();
        }
        
    }
   /**
    * gets the cuurent time (System time + time difference)
    *
    */    
    public long getCurrentTime()
    {
        return System.currentTimeMillis() + csi.getDeltaTime();
        
        //return System.currentTimeMillis();
    }
   /**
    * returns instance of Clocksource
    * @reutrn INSTANCE
    */   
    public static ClockSource getInstance() {
        return INSTANCE;
    }
    
}
