/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Global;
import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
import de.dlr.ts.commons.logger.DLRLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bott_ma
 */
public class Printer extends Thread 
{
    private HashMap<Integer, RemoteStation> rss;
    
    @Override
    public void run()
    {        
        rss = Heartbeat.getInstance().getRemoteStations();
        
        while(true)
        {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            print();
        }
    }
    
    private RemoteStation rs = new RemoteStation();
    
    private RemoteStation fillUpMyData()
    {
        rs.setDeltaTimeInMillis(ClockSource.getInstance().getDeltaTime());
        rs.setHopsToReach(0);
        rs.setStationId(Config.getInstance().getMyStationId());
        rs.setSystemStartTime(Global.getInstance().getSystemStartTime());
        rs.setLastMessageId(HeartbeatMessage.getCurrentMessageId());
        rs.setMessageArrivalTime(System.currentTimeMillis());
        
        return rs;
    }
    
    private synchronized void print()
    {
        DLRLogger.info(RemoteStation.separator());
            
        DLRLogger.info(RemoteStation.printLineNames());
        
        DLRLogger.info(fillUpMyData().printLine());
                        
        ArrayList list = new ArrayList(rss.keySet());
        Collections.sort(list);
        
        for (Object o : list) {                                
            DLRLogger.info(rss.get((int)o).printLine());
        }
        
        //for (Integer i : rss.keySet())
          //  DLRLogger.info(rss.get(i).printLine());

        DLRLogger.info(RemoteStation.separator());
    }
}
