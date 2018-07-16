/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.MyTools;
import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.utils.print.Color;
import de.dlr.ts.commons.utils.print.ColorString;
import de.dlr.ts.commons.utils.print.Effect;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author bott_ma
 */
public class Measurement
{    
    private static int _id = 0;
    private final LatencyLinkbird linkbird;
        
    @Getter @Setter private int destinationStationId;
    @Getter @Setter private int measurementId;
    @Getter private long startTime;
    @Getter private long endTime;
    @Getter @Setter private int messagesCount;
    @Getter @Setter private int messageSize;    
    @Getter @Setter private int packetLoss;   
    
    private boolean active = false;
    private boolean failed = true;

    
    /**
     * 
     * @param linkbird 
     */
    public Measurement(LatencyLinkbird linkbird) {
        this.linkbird = linkbird;
        measurementId = _id++;                
    }    

    /**
     * 
     */
    public void start()
    {                        
        LatencyMessage mess = new LatencyMessage();
        mess.setOriginStationId(Config.getInstance().getMyStationId());        
        mess.setDesTinationStationId(this.destinationStationId);                        
        mess.setMeasurementId(measurementId);
                
        startTime = System.currentTimeMillis();        
        
        String string = ColorString.string("Starting", Color.RED, Effect.BOLD);
        DLRLogger.info(this, string + " measurement " + this.measurementId + 
                " with station " + this.destinationStationId);
        
        //Sending the first message
        active = true;
        linkbird.send(mess.getBytes());
        
        MyTools.sleepi(3_000L);
        
        DLRLogger.info(this, "Finishing measurement " + this.measurementId + (failed ? " unsuccessfully" : " successessfully"));
        
        active = false;
    }    

    /**
     *      
     * @param data
     */
    public void incomingMessage(byte[] data)
    {
        LatencyMessage lm = new LatencyMessage();
        lm.parse(data);
                        
        if(lm.getTripId() == Config.getInstance().getMeasurementTripsCount() - 1)
        {
            endTime = System.currentTimeMillis();
            active = false;
            failed = false;
            
            DLRLogger.info(this, "Finishing measurement " + this.measurementId);
            
            long totalTime = this.endTime - this.startTime;
            DLRLogger.info(this, "Total time " + totalTime + " ms");
                        
            long totalPerMess = totalTime / Config.getInstance().getMeasurementTripsCount();            
            DLRLogger.info(this, "Total time per round trip  " + totalPerMess + " ms");
        }
        else
        {
            lm.increaseTripId();            
            linkbird.send(lm.getBytes());
        }
    }

    public boolean isActive() {
        return active;
    }            
}
