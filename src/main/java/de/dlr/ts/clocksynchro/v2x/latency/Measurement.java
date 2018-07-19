/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Global;
import de.dlr.ts.clocksynchro.v2x.MyTools;
import de.dlr.ts.commons.logger.DLRLogger;
import java.util.Date;
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
    
    @Getter @Setter private boolean savedToDisk = false;
    
    private int lastTripId;
    
    
    public static String toDiskColumnNames() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("startDate").append(";");
        sb.append("startTime").append(";");
        sb.append("measurementId").append(";");
        sb.append("originStationId").append(";");
        sb.append("destinationStationId").append(";");
        sb.append("success").append(";");
        sb.append("duration").append(";");
        sb.append("messageSize").append(";");
        sb.append("totalTrips").append(";");        
        sb.append("lastTripId").append(";");
        sb.append("roundTripTime");
        
        return sb.toString();
    }
    
    /**
     * 
     * @return 
     */
    public String toDiskString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Global.getInstance().getWholeDate().format(new Date(startTime))).append(";");
        sb.append(Global.getInstance().getOnlyTimeWithMillis().format(new Date(startTime))).append(";");
        sb.append(measurementId).append(";");
        sb.append(Config.getInstance().getMyStationId()).append(";");
        sb.append(destinationStationId).append(";");
        sb.append(!failed).append(";");
        
        if(failed)
            sb.append(0).append(";");
        else
            sb.append(endTime - startTime).append(";");
        
        sb.append(messageSize).append(";");
        sb.append(Config.getInstance().getMeasurementTotalTrips()).append(";");        
        sb.append(lastTripId).append(";");
        
        if(failed)
            sb.append(0);
        else
            sb.append((endTime - startTime) / Config.getInstance().getMeasurementTotalTrips());
        
        return sb.toString();
    }
    
    /**
     * 
     * @param linkbird 
     */
    public Measurement(LatencyLinkbird linkbird) {
        this.linkbird = linkbird;
        measurementId = _id++;
        
        if(measurementId > Short.MAX_VALUE)
            measurementId = 0;
    }    

    
    private String getName() {
        return measurementId + "|" + Config.getInstance().getMyStationId() + 
                "->" + this.destinationStationId;
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
        
        //String string = ColorString.string("Starting", Color.GREEN, Effect.BOLD);
        DLRLogger.info(this, "Starting latency measurement " + getName());
        
        //Sending the first message
        active = true;        
        byte[] bytes = mess.getBytes();
        messageSize = bytes.length;
        linkbird.send(bytes);
        
        MyTools.sleepi(3_000L);
                
        DLRLogger.info(this, "Finishing latency measurement " + this.getName() + 
                (failed ? " unsuccessfully" : " successfully"));
        
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
        
        lastTripId = lm.getTripId();
        
        if(lm.getTripId() == Config.getInstance().getMeasurementTotalTrips() - 1)
        {
            endTime = System.currentTimeMillis();
            active = false;
            failed = false;
            
            StringBuilder sb = new StringBuilder();
            sb.append("Latency measurement: ").append(this.measurementId).append(" | ");
            
            long totalTime = this.endTime - this.startTime;
            
            int trips = Config.getInstance().getMeasurementTotalTrips();            
            sb.append(trips).append(" trips made | ");
            sb.append(totalTime).append(" ms total time | ");
                        
            long totalPerMess = totalTime / Config.getInstance().getMeasurementTotalTrips();            
            sb.append(totalPerMess).append(" ms roundtrip time");
            
            DLRLogger.info(this, sb.toString());
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
