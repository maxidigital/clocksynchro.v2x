
package de.dlr.ts.clocksynchro.v2x.timebroadcaster;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Broadcasts time messages 
 * 
 */
public class TimeBroadcaster extends Thread
{
    private static final TimeBroadcaster INSTANCE = new TimeBroadcaster();
    private LinkbirdBroadcaster linkbird; 
    

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
                Thread.sleep(1000L);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeBroadcaster.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            V2XTimeMessage message = new V2XTimeMessage();
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
