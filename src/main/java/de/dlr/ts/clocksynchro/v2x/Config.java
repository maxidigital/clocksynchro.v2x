/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.logger.LogLevel;
import de.dlr.ts.utils.xmladmin2.XMLAdmin2;
import de.dlr.ts.utils.xmladmin2.exceptions.MalformedKeyOrNameException;
import de.dlr.ts.utils.xmladmin2.exceptions.XMLNodeNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author bott_ma
 */
public class Config
{
    private static final Config INSTANCE = new Config();
    
    private int stationId;
    private int linkbirdIncomingPort = 1302;
    private int linkbirdOutgoingPort = 1301;
    private String linkbirdOutgoingAddress = "localhost";
    
    private String timeAPIOutputAddress = "localhost";
    private int timeAPIOutputPort = 1234;
    
    public enum StationType{GENERATOR, TERMINAL}
    private StationType stationType = StationType.GENERATOR;
    
    private int gpsInputPort = 1234;
    
    private final int timeMessageBTPPort = 3333;
    private final int heartbeatBTPPort = 3334;
    
    private final int startingHopValue = 10;
    private final long heartbeatSendingInterval = 30000;
    private final long timeBroadcastingSendingInterval = 10000;
    private final long timeAPISendingInterval = 10000;
    
    /**
     * 
     */
    public void load()
    {
        try {
            XMLAdmin2 x = new XMLAdmin2().load("config.xml");
            
            String debugLevel = x.getNode("debug").getValue();
            DLRLogger.setLevel(LogLevel.parse(debugLevel.toUpperCase()));
            
            int std = x.getNode("debug").getAttributes().get("saveToDisk").getValue(0);
            DLRLogger.setWriteToDisk(std);
            
            linkbirdIncomingPort = x.getNode("linkbird.incomingPort").getValue(0);
            
            stationId = x.getNode("general.stationId").getValue(0);
            String st = x.getNode("general.stationType").getValue();
            stationType = StationType.valueOf(st.toUpperCase());
            
            linkbirdIncomingPort = x.getNode("linkbird.incomingPort").getValue(0);
            linkbirdOutgoingPort = x.getNode("linkbird.outgoingPort").getValue(0);
            linkbirdOutgoingAddress = x.getNode("linkbird.outgoingAddress").getValue();
            
        } catch (SAXException | IOException | MalformedKeyOrNameException | XMLNodeNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getTimeBroadcastingSendingInterval() {
        return timeBroadcastingSendingInterval;
    }

    public long getTimeAPISendingInterval() {
        return timeAPISendingInterval;
    }
    
    public String getTimeAPIOutputAddress() {
        return timeAPIOutputAddress;
    }

    public int getTimeAPIOutputPort() {
        return timeAPIOutputPort;
    }
    
    
    public int getStartingHopValue() {
        return startingHopValue;
    }
    
    public int getMyStationId() {
        return stationId;
    }

    public long getHeartbeatSendingInterval() {
        return heartbeatSendingInterval;
    }

    
    public int getGpsInputPort() {
        return gpsInputPort;
    }

    public String getLinkbirdOutgoingAddress() {
        return linkbirdOutgoingAddress;
    }

    public StationType getStationType() {
        return stationType;
    }

    public int getHeartbeatBTPPort() {
        return heartbeatBTPPort;
    }

    public int getTimeMessageBTPPort() {
        return timeMessageBTPPort;
    }
    
    public int getLinkbirdOutgoingPort() {
        return linkbirdOutgoingPort;
    }
    
    public int getLinkbirdIncomingPort() {
        return linkbirdIncomingPort;
    }
    
    
    private Config() {
    }

    public static Config getInstance() {
        return INSTANCE;
    }
    
    
}
