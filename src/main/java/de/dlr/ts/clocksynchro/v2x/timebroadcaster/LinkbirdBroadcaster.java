
package de.dlr.ts.clocksynchro.v2x.timebroadcaster;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.MainLinkbird;

/**
 *  Broadcasts linkbird messages 
 * 
 */
public class LinkbirdBroadcaster //implements CCU.Listener
{
    
    public LinkbirdBroadcaster() 
    {
    }
    
   /**
    *  forwards time messages from linkbird  
    *  @param payload Message in bytes
    */
    public void send(byte[] payload) {
        MainLinkbird.getInstance().send(payload, Config.getInstance().getTimeMessageBTPPort());
    }   

}
