package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.c2x.devices.linkbird.LinkbirdFactory;
import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.timebroadcaster.V2XTimeMessage;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCU;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCUMessage;
import de.dlr.ts.commons.c2x.interfaces.ccu.DisseminationType;
import de.dlr.ts.commons.logger.DLRLogger;

/**
 * Receives time message from Linkbird source 
 * @see CCU.Listener
 * @see ClockSourceInterface
 */
class V2XSource implements CCU.Listener, ClockSourceInterface
{
    private CCU ccu;
    private int lastMessageReceivedId = -1;
    private int deltaTime = 0;
    
   /**
    * Starts receiving time Messages from Linkbird source
    */
    @Override
    public void start()
    {
        ccu = LinkbirdFactory.createCCU();
        ccu.addListener(this);
        ccu.setCCUAddress(Config.getInstance().getLinkbirdOutgoingAddress());
        ccu.setDataInputPort(Config.getInstance().getLinkbirdIncomingPort());
        ccu.setDataOutputPort(Config.getInstance().getLinkbirdOutgoingPort());
        ccu.startReceiver();
    }
    
    /**
     * Retrieves data from recieved time message from linkbird source 
     * @param CCUMessage  
     * 
     */
    @Override
    public void newIncomingMessage(CCUMessage im) {
        if(im.getDestinationPort() != Config.getInstance().getTimeMessageBTPPort())
            return;
        
        byte[] payload = im.getPayload();
        
        V2XTimeMessage mess = new V2XTimeMessage();
        mess.parse(payload);
        
        if(mess.getMessageId() == lastMessageReceivedId)
            return;
        
        DLRLogger.info(this, "Incoming V2X time message. " + mess.toString());
        
        lastMessageReceivedId = mess.getMessageId();
        
        if(mess.getHops() > 0)
        {
            mess.decreaseHop();
            send(mess.getBytes());
        }
        
        processMessage(mess);
    }
    
   /**
    * forwards time message to other linkbird terminals  
    * @param Payload message in bytes array
    */
    private void send(byte[] payload)
    {
        CCUMessage mess = ccu.createCCUMessage();
        mess.setDisseminationType(DisseminationType.TOPO_SCOPED_BROADCAST_SINGLEHOP);
        mess.setDestinationPort(Config.getInstance().getTimeMessageBTPPort());
        mess.setPayload(payload);
        
        ccu.sendMessage(mess);
    }
    
   /**
    * gets the time differnece 
    * @return DeltaTime
    */
    @Override
    public int getDeltaTime() {
        return deltaTime;
    }

   /**
    * Calculates time differnece between system time and message time 
    * @param V2XTimeMessage time message from Linkbird source
    */
    private void processMessage(V2XTimeMessage mess) {
        deltaTime = (int) (System.currentTimeMillis() - mess.getCurrentTime());
        
        DLRLogger.info(this, "Saving delta time: " + deltaTime);
    }
    
}
