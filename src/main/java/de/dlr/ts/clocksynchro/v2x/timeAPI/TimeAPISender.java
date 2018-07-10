package de.dlr.ts.clocksynchro.v2x.timeAPI;

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
public class TimeAPISender extends Thread 
{
    private static final TimeAPISender instance = new TimeAPISender();
    private UDPClient client;

    public TimeAPISender() {
        try {
            client = new UDPClient("localhost", 1234);
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
        while(true)
        {
            DLRLogger.fine(this, "Sending Time messsage to localhost:1234");
            sendTimeMessage();
            
            try {
                Thread.sleep(1000);
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
            client.send(new TimeAPIMessage().getBytes());
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
