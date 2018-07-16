/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Global;
import de.dlr.ts.clocksynchro.v2x.Module;
import java.util.HashMap;
import de.dlr.ts.clocksynchro.v2x.MessagesListener;
import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.utils.print.Color;
import de.dlr.ts.commons.utils.print.ColorString;
import de.dlr.ts.commons.utils.print.Effect;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Praktikant-Q2-2015
 */
public class Heartbeat extends Thread implements Module, MessagesListener
{
    private static final Heartbeat INSTANCE = new Heartbeat();
    private final HashMap<Integer, RemoteStation> remoteStations = new HashMap<>();
    private final LinkbirdHearbeat linkbird = new LinkbirdHearbeat(this);
    private final Printer printer = new Printer();
    
    HashMap<Integer, RemoteStation> getRemoteStations() {
        return remoteStations;
    }        
    
    /**
     * 
     * @return 
     */
    public RemoteStation[] getHopZeroStations()
    {
        List<RemoteStation> rm = new ArrayList<>();
        
        for (Integer i : remoteStations.keySet())        
            if(remoteStations.get(i).getHopsToReach() == 0)
                rm.add(remoteStations.get(i));        
        
        return rm.toArray(new RemoteStation[rm.size()]);
    }
    
    @Override
    public void run()
    {
        linkbird.init();
        
        printer.start();
        
        DLRLogger.config(this, "Starting heartbeat module");
        
        while(true)
        {
            HeartbeatMessage message = new HeartbeatMessage();
            message.setMessageId(HeartbeatMessage.getMessageIdCounter());
            message.setStationId(Config.getInstance().getMyStationId());
            message.setStartingHopValue((byte)Config.getInstance().getStartingHopValue());
            message.setHops((byte)Config.getInstance().getStartingHopValue());
            message.setCurrentTime(ClockSource.getInstance().getCurrentTime());
            message.setSystemStartTime(Global.getInstance().getSystemStartTime());
            message.setDeltaTime(ClockSource.getInstance().getDeltaTime());
        
            DLRLogger.fine(this, "Sending " + message);
            linkbird.send(message.getBytes());
            
            try {
                Thread.sleep(Config.getInstance().getHeartbeatSendingInterval());
            } catch (InterruptedException ex) {
                Logger.getLogger(Heartbeat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     *
     * @param payload
     */
    @Override
    public void receivePayload(byte[] payload)
    {                
        HeartbeatMessage message = new HeartbeatMessage();
        message.parse(payload);
                        
        if(message.getStationId() == Config.getInstance().getMyStationId())
            return;                                
        
        RemoteStation rs = remoteStations.get(message.getStationId());
            
        if(rs == null)
        {
            String inco = ColorString.string("Incoming ", Color.GREEN, Effect.BOLD);
            DLRLogger.fine(this, inco + message);
            
            RemoteStation nrs = new RemoteStation();
            nrs.setStationId(message.getStationId());
            nrs.setLastMessageId(message.getMessageId());
            nrs.setSystemStartTime(message.getSystemStartTime());
            nrs.setMessageTimeInMillis(message.getCurrentTime());
            nrs.setDeltaTimeInMillis(message.getDeltaTime());
            nrs.setHopsToReach(message.getHopsToReach());
            nrs.setMessageArrivalTime(System.currentTimeMillis());
            
            remoteStations.put(message.getStationId(), nrs);
            
            forwardMessage(message);
        }
        else //We already have this station
        {
            if(message.getMessageId() == rs.getLastMessageId()) //we already have received this message
            {
                if(message.getHopsToReach() < rs.getHopsToReach())
                {
                    String inco = ColorString.string("Incoming ", Color.GREEN, Effect.BOLD);
                    DLRLogger.fine(this, inco + message);
                    
                    rs.setHopsToReach(message.getHopsToReach());
                    rs.setMessageArrivalTime(System.currentTimeMillis());
                    forwardMessage(message);
                }
            }
            else //we haven't already received this message
            {
                String inco = ColorString.string("Incoming ", Color.GREEN, Effect.BOLD);
                DLRLogger.fine(this, inco + message);
                
                rs.setLastMessageId(message.getMessageId());
                rs.setSystemStartTime(message.getSystemStartTime());
                rs.setMessageTimeInMillis(message.getCurrentTime());
                rs.setDeltaTimeInMillis(message.getDeltaTime());
                rs.setHopsToReach(message.getHopsToReach());
                rs.setMessageArrivalTime(System.currentTimeMillis());
                
                forwardMessage(message);
            }
        }
    }

    /**
     * 
     * @param mess 
     */
    private void forwardMessage(HeartbeatMessage mess)
    {
        mess.decreaseHop();
        
        if(mess.getHops() > 0)
        {
            DLRLogger.fine(this, "Forwarding heartbeat message " + mess.getName());
            linkbird.send(mess.getBytes());
        }
            
    }
    
    /**
     * 
     * @return 
     */
    public static Heartbeat getInstance() {
        return INSTANCE;
    }
    
}
