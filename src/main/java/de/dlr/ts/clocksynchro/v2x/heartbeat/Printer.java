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
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            print();
        }
    }
    
    private RemoteStation rs = new RemoteStation();
    
    private RemoteStation fillUpMyData()
    {
        rs.deltaTimeInMillis = ClockSource.getInstance().getDeltaTime();
        rs.hopsToReach = 0;
        rs.stationId = Config.getInstance().getMyStationId();
        rs.systemStartTime = Global.getInstance().getSystemStartTime();
        rs.lastMessageId = HeartbeatMessage.getCurrentMessageId();
        
        return rs;
    }
    
    private synchronized void print()
    {
        DLRLogger.info(RemoteStation.separator());
            
        DLRLogger.info(RemoteStation.printLineNames());
        
        DLRLogger.info(fillUpMyData().printLine());
        
        for (Integer i : rss.keySet())           
            DLRLogger.info(rss.get(i).printLine());            

        DLRLogger.info(RemoteStation.separator());
    }
}