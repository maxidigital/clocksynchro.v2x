package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Global;
import de.dlr.ts.clocksynchro.v2x.Module;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines the source of time 
 * 
 */
public class ClockSource implements Module
{
    private static final ClockSource INSTANCE = new ClockSource();
    private ClockSourceInterface csi;
    
    
    /**
     * 
     */
    private ClockSource() {
    }
    
   /**
    * Differentiates between generator message and terminal message
    * 
    */
    @Override
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
    
    public int getDeltaTime() {
        return csi.getDeltaTime();
    }
    
    public long getCurrentTime() {
        return csi.getDeltaTime() + System.currentTimeMillis();
    }
    
    public String getCurrentTimeString() {
        return Global.getInstance().getOnlyTime().format(new Date(getCurrentTime()));
    }
    
   /**
    * returns instance of Clocksource
    * @return 
    */   
    public static ClockSource getInstance() {
        return INSTANCE;
    }
    
}
