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
    
    @Override
    public void run()
    {
        DLRLogger.config(this, "Starting the heartbeat module");
        
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
        
            DLRLogger.info(this, "Sending heartbeat message: " + message);
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
            RemoteStation nrs = new RemoteStation();
            nrs.stationId = message.getStationId();
            nrs.lastMessageId = message.getMessageId();
            nrs.systemStartTime = message.getSystemStartTime();
            nrs.messageTimeInMillis = message.getCurrentTime();
            nrs.deltaTimeInMillis = message.getDeltaTime();
            nrs.hopsToReach = message.getHopsToReach();
            
            remoteStations.put(message.getStationId(), nrs);
            
            forwardMessage(message);
        }
        else //We already have this station
        {
            if(message.getMessageId() == rs.lastMessageId) //we already have received this message
            {
                if(message.getHopsToReach() < rs.hopsToReach)
                {
                    rs.hopsToReach = message.getHopsToReach();
                    forwardMessage(message);
                }
            }
            else //we haven't already received this message
            {
                rs.lastMessageId = message.getMessageId();
                rs.systemStartTime = message.getSystemStartTime();
                rs.messageTimeInMillis = message.getCurrentTime();
                rs.deltaTimeInMillis = message.getDeltaTime();
                rs.hopsToReach = message.getHopsToReach();
                
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
            linkbird.send(mess.getBytes());
    }
    
    /**
     * 
     * @return 
     */
    public static Heartbeat getInstance() {
        return INSTANCE;
    }
    
}
