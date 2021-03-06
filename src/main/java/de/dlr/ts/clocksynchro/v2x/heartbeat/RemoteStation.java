/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.commons.tools.NiceTimeTool;
import de.dlr.ts.commons.tools.StringTools;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Praktikant-Q2-2015
 */
public class RemoteStation
{
    @Getter @Setter private int stationId;
    
    @Getter @Setter private int lastMessageId;
    @Getter @Setter private long systemStartTime;
    
    @Getter @Setter private int hopsToReach;
    
    @Getter @Setter private long messageTimeInMillis;
    @Getter @Setter private int deltaTimeInMillis;
    
    @Getter @Setter private long messageArrivalTime;
    
    @Getter @Setter private long lastLatencyMeasurementTime;
    @Getter @Setter private int latencyMessagesCount;
    @Getter @Setter private long lastLatencyMeasurementValue;
        
    private final NiceTimeTool ntt = new NiceTimeTool();
    
    private static final int[]    sizes = {17, 7, 8, 4, 8, 17};
    private static final String[] align = {"R", "R", "R", "R", "R", "R"};
    
    
    /**
     * 
     */
    public RemoteStation() {
        ntt.setSecond("secs");
        ntt.setMinute("min");
        ntt.setHour("h");
    }
    
    /**
     * 
     * @return 
     */
    public static String printLineNames()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        sb.append(StringTools.align("last", sizes[0], "C"));
        sb.append(" | ");
        sb.append(StringTools.align("statId", sizes[1], "C"));
        sb.append(" | ");
        sb.append(StringTools.align("lastId", sizes[2], "C"));
        sb.append(" | ");
        sb.append(StringTools.align("hops", sizes[3], "C"));
        sb.append(" | ");
        sb.append(StringTools.align("delta", sizes[4], "C"));
        sb.append(" | ");
        sb.append(StringTools.align("startUp", sizes[5], "C"));
        sb.append(" |");
                      
        return sb.toString();
    }

    /**
     * 
     * @return 
     */
    public boolean getState() {
        if(System.currentTimeMillis() - messageArrivalTime > 120_000)
            return false;
        
        return true;
    }
    
    /**
     * 
     * @return 
     */
    public String printLine()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("| ");        
        
        long diff = System.currentTimeMillis() - messageArrivalTime;
        String time = ntt.getTime(diff);
        
        if(this.stationId == Config.getInstance().getMyStationId())
            sb.append(StringUtils.repeat(" ", sizes[0]));
        else
            sb.append(StringTools.align(time + " ago", sizes[0], align[0]));
                        
        sb.append(" | ");
        sb.append(StringTools.align(stationId, sizes[1], align[1]));
        
        sb.append(" | ");
        sb.append(StringTools.align(lastMessageId, sizes[2], align[2]));
        
        sb.append(" | ");
        sb.append(StringTools.align(hopsToReach, sizes[3], align[3]));
        
        sb.append(" | ");
        sb.append(StringTools.align(deltaTimeInMillis, sizes[4], align[4]));
        
        sb.append(" | ");
        diff = System.currentTimeMillis() - systemStartTime;
        time = ntt.getTime(diff);
        //String star = Global.getInstance().getDateTime().format(new Date(systemStartTime));
        //sb.append(StringTools.align(star, sizes[5], align[5]));
        sb.append(StringTools.align(time + " ago", sizes[5], align[5]));
        
        sb.append(" |");        
        
        return sb.toString();
    }
    
    public static String separator() {
        return StringUtils.repeat("_", 80);        
    }
}
