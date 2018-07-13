/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Global;
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
    
    
    public static void main(String[] args)
    {
        String columnFormat = "| %-15s | %4d |%n";        
        //String format = String.format(columnFormat, "cucho", 2);                
        
        String format = String.format(title, 
                "     Arrival",
                " StatId",
                " LastId ",
                "hops",
                "  delta ",
                "     StartUp     ");
                        
        System.out.println(format);
                
    }
    
    private final static String title = "| %-17s | %7s | %8s | %4s | %8s | %-17s |";
    private final static String conte = "| %-17s | %7s | %8s | %4s | %8s | %-17s |";
    
    public static String printLineNames()
    {
        String format = String.format(title, 
                "     Arrival",
                " StatId",
                " LastId ",
                "hops",
                "  delta ",
                "     StartUp     ");
              
        return format;
    }
    
    public String printLine()
    {
        StringBuilder sb = new StringBuilder();
        
        if(messageArrivalTime == 0)
            sb.append("| ").append(StringUtils.repeat(" ", 17));
        else
            sb.append("| ").append(Global.getInstance().getDateTime().format(new Date(messageArrivalTime)));
        
        String staId = String.format("%1$7s", stationId);        
        sb.append(" | ").append(staId);
        
        String lastId = String.format("%1$8s", lastMessageId);
        sb.append(" | ").append(lastId);
        
        String hops = String.format("%1$4s", hopsToReach);
        sb.append(" | ").append(hops);
                
        String delta = String.format("%1$8s", deltaTimeInMillis);
        sb.append(" | ").append(delta);
        
        sb.append(" | ").append(Global.getInstance().getDateTime().format(new Date(systemStartTime)));
        
        sb.append(" |");        
        
        return sb.toString();
    }
    
    public static String separator() {
        return StringUtils.repeat("_", 80);        
    }
}
