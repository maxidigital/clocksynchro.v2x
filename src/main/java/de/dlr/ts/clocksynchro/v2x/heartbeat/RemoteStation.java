/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Global;
import de.dlr.ts.commons.tools.NiceTimeTool;
import de.dlr.ts.commons.tools.StringTools;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Praktikant-Q2-2015
 */
class RemoteStation
{
    int stationId;
    int lastMessageId;
    long systemStartTime;
    
    int hopsToReach;
    
    long messageTimeInMillis;
    int deltaTimeInMillis;
    
    long messageArrivalTime;
    //int messagesCount;

    private NiceTimeTool ntt = new NiceTimeTool();
    
    public RemoteStation() {
        ntt.setSecond("seconds");
        ntt.setMinute("min");
        ntt.setHour("hours");
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
    
    private static int[]    sizes = {17, 7, 8, 4, 8, 17};
    private static String[] align = {"R", "R", "R", "R", "R", "R    "};
    
    public boolean getState()
    {
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
        String star = Global.getInstance().getDateTime().format(new Date(systemStartTime));
        sb.append(StringTools.align(star, sizes[5], align[5]));
        
        sb.append(" |");        
        
        return sb.toString();
    }
    
    public static String separator() {
        return StringUtils.repeat("_", 80);        
    }
}
