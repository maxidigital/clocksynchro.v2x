package de.dlr.ts.clocksynchro.v2x.timeAPI;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Module;
import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.network.udp.UDPClient;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sends time messages from GPS 
 * 
 */
public class TimeAPISender extends Thread implements Module
{
    private static final TimeAPISender instance = new TimeAPISender();
    private UDPClient client;

    public TimeAPISender() {
        try {
            client = new UDPClient(Config.getInstance().getTimeAPIOutputAddress(), 
                    Config.getInstance().getTimeAPIOutputPort());
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(TimeAPISender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   /**
    *  sends time message every one second   
    *  
    */
    @Override
    public void run() {
        
        DLRLogger.config(this, "Starting Time API sender");
        
        while(true)
        {
            DLRLogger.fine(this, "Sending Time messsage to " + 
                    Config.getInstance().getTimeAPIOutputAddress() + ":" +
                    Config.getInstance().getTimeAPIOutputPort());
            sendTimeMessage();
            
            try {
                Thread.sleep(Config.getInstance().getTimeAPISendingInterval());
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeAPISender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   /**
    *  sends time message from GPS source in byte array  
    */
    private void sendTimeMessage()
    {
        try {
            TimeAPIMessage mess = new TimeAPIMessage();
            mess.setCurrentTime(ClockSource.getInstance().getCurrentTime());
            
            client.send(mess.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(TimeAPISender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   /**
    *  gets TimeAPISender class instance 
    *  @return INSTANCE
    */
    public static TimeAPISender getInstance() {
        return instance;
    }
    
}
