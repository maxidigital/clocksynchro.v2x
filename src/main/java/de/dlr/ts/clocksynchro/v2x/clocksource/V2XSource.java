package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.MessagesListener;
import de.dlr.ts.clocksynchro.v2x.timebroadcaster.V2XTimeMessage;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCU;
import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.utils.print.Color;
import de.dlr.ts.commons.utils.print.ColorString;

/**
 * Receives time message from Linkbird source 
 * @see CCU.Listener
 * @see ClockSourceInterface
 */
class V2XSource implements MessagesListener, ClockSourceInterface
{    
    private int lastMessageReceivedId = -1;
    private int deltaTime = 0;
    private V2XClockSourceLinkbird linkbird;
    
   /**
    * Starts receiving time Messages from Linkbird source
    */
    @Override
    public void start()
    {
        linkbird = new V2XClockSourceLinkbird(this);
        linkbird.start();
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

    @Override
    public void receivePayload(byte[] payload)
    {
        V2XTimeMessage mess = new V2XTimeMessage();
        mess.parse(payload);
        
        if(mess.getMessageId() == lastMessageReceivedId)
            return;
        
        String string = ColorString.string("V2X Time message", Color.CYAN);
        DLRLogger.info(this, "Incoming " + string + ". "+ mess.toString());
        
        lastMessageReceivedId = mess.getMessageId();
        
        if(mess.getHops() > 0)
        {
            mess.decreaseHop();
            linkbird.send(mess.getBytes());
        }
        
        processMessage(mess);
    }
    
}
