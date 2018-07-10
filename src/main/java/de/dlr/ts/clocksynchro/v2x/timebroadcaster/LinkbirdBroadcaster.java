
package de.dlr.ts.clocksynchro.v2x.timebroadcaster;

import de.dlr.ts.c2x.devices.linkbird.LinkbirdFactory;
import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCU;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCUMessage;
import de.dlr.ts.commons.c2x.interfaces.ccu.DisseminationType;

/**
 *  Broadcasts linkbird messages 
 * 
 */
public class LinkbirdBroadcaster //implements CCU.Listener
{
    private CCU ccu;
    
    public LinkbirdBroadcaster() 
    {
    }

   /**
    *  create Linkbird object and configures parameters  
    *  
    */
    public void start()
    {
        ccu = LinkbirdFactory.createCCU();
        //ccu.addListener(this);
        ccu.setCCUAddress(Config.getInstance().getLinkbirdOutgoingAddress());
        //ccu.setDataInputPort(Config.getInstance().getLinkbirdIncomingPort());
        ccu.setDataOutputPort(Config.getInstance().getLinkbirdOutgoingPort());
        //ccu.startReceiver();
    }
    
   /**
    *  processes incoming linkbird messages 
    *  @param im 
    */
    //@Override
    public void newIncomingMessage(CCUMessage im) {
        
        if(im.getDestinationPort() != Config.getInstance().getTimeMessageBTPPort())
            return;
        
        byte[] payload = im.getPayload();
        
        String string = new String(payload);
        System.out.println(string);
    }
    
   /**
    *  forwards time messages from linkbird  
    *  @param payload Message in bytes
    */
    public void send(byte[] payload)
    {
        CCUMessage mess = ccu.createCCUMessage();
        mess.setDisseminationType(DisseminationType.TOPO_SCOPED_BROADCAST_SINGLEHOP);
        mess.setDestinationPort(Config.getInstance().getTimeMessageBTPPort());
        mess.setPayload(payload);
        
        ccu.sendMessage(mess);
    }   

}
