/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.MessagesListener;
import de.dlr.ts.clocksynchro.v2x.MyTools;
import de.dlr.ts.commons.logger.DLRLogger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bott_ma
 */
public class Latency extends Thread implements MessagesListener
{
    private static final Latency instance = new Latency();
    private LatencyLinkbird linkbird;
    private Measurements meas;
    private Measurement current = null;

    
    @Override
    public void run() {
        linkbird = new LatencyLinkbird(this);        
        linkbird.init();
        
        meas = new Measurements(linkbird);        
        
        DLRLogger.info(this, "Starting latency measurement module");
        
        MyTools.sleepi(5_000L);
        
        while (true)
        {                       
            current = meas.getNewMeasurement();
            
            if(current != null)
                current.start();
                        
            MyTools.sleepi(30_000L);                        
        }
    }        
    
    private Latency() {        
    }

    public static Latency getInstance() {
        return instance;
    }    

    @Override
    public void receivePayload(byte[] payload)
    {   
        LatencyMessage lm = new LatencyMessage();            
        lm.parse(payload);
        
        //System.out.println("incoming lm " + lm);    
            
        if(current != null && current.isActive() && 
                lm.getOriginStationId() == Config.getInstance().getMyStationId())
        {
            //System.out.println("----------- 1 ");
            current.incomingMessage(payload);
        }            
        else
        {
            //System.out.println("----------- 2 ");
            
            if(lm.getDesTinationStationId() == Config.getInstance().getMyStationId())
            {
                //System.out.println("----------- 3 ");
                
                //DLRLogger.info(this, "Echoing message ");
                linkbird.send(payload);
            }               
        }
    }

    
}
