
package de.dlr.ts.clocksynchro.v2x.timebroadcaster;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.Module;
import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
import de.dlr.ts.commons.logger.DLRLogger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Broadcasts time messages 
 * 
 */
public class TimeBroadcaster extends Thread implements Module
{
    private static final TimeBroadcaster INSTANCE = new TimeBroadcaster();
    private final LinkbirdBroadcaster linkbird; 
    

    private TimeBroadcaster() {
        linkbird = new LinkbirdBroadcaster();
    }
    
   /**
    *  sends time message every one second   
    *  
    */
    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(Config.getInstance().getTimeBroadcastingSendingInterval());
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeBroadcaster.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            V2XTimeMessage message = new V2XTimeMessage();
            message.setMessageId(V2XTimeMessage.getMessageIdCounter());
            message.setCurrentTime(ClockSource.getInstance().getCurrentTime());
            message.setHops((byte) Config.getInstance().getStartingHopValue());
            
            DLRLogger.fine(this, "Sending V2X time message: " + message);
            
            linkbird.send(message.getBytes());
        }
    }
    
   /**
    *  gets TimeBroadcaster class instance 
    *  @return INSTANCE
    */
    public static TimeBroadcaster getInstance() {
        return INSTANCE;
    }
    
}
