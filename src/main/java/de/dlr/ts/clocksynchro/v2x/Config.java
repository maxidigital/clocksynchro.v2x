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

    private int linkbirdIncomingPort = 1302;
    private int linkbirdOutgoingPort = 1301;
    private String linkbirdOutgoingAddress = "localhost";

    
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
            
        } catch (SAXException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedKeyOrNameException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLNodeNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getLinkbirdOutgoingAddress() {
        return linkbirdOutgoingAddress;
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
