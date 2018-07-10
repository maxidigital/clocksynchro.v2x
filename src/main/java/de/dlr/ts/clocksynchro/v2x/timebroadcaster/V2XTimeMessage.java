
package de.dlr.ts.clocksynchro.v2x.timebroadcaster;

import java.nio.ByteBuffer;
import de.dlr.ts.clocksynchro.v2x.UDPMessageInterface;

/**
 * Process meeages from linkbird
 * @see UDPMessageInterface
 */
public class V2XTimeMessage implements UDPMessageInterface
{    
    private int hops;
    private int messageId;
    private long currentTime;
    
    public static final int GDP_PORT = 2100;

   /**
    * gets message ID  
    * @reutrn messageID
    */
    public int getMessageId() {
        return messageId;
    }
    
   /**
    * gets nukber of hops  
    * @reutrn hops
    */
    public int getHops() {
        return hops;
    }
    
   /**
    * gets current time  
    * @reutrn current time 
    */
    public long getCurrentTime() {
        return currentTime;
    }
    
    /**
     * reduces number of hops when message is forwarded from one terminal to another
     */
    public void decreaseHop()
    {
        if(hops > 0)
            hops--;
    }
    
   /**
    *  parses byte array message 
    *  @param byte[] Message in bytes
    */
    @Override
    public void parse(byte[] data)
    {
        ByteBuffer bb = ByteBuffer.wrap(data);
        hops = bb.getInt();
        messageId = bb.getInt();
        currentTime = bb.getLong();
    }
    
   /**
    *  gets Message in bytes  
    *  @return byte[] 
    */
    @Override
    public byte[] getBytes()
    {
        ByteBuffer bb = ByteBuffer.allocate(128);
        
        bb.putInt(hops);
        bb.putInt(messageId);
        bb.putLong(currentTime);
        
        return bb.array();
    }
    
   /**
    *  adds message parameters to a string  
    *  @return message parameters
    */
    @Override
    public String toString() {
        
        return "V2XTimeMessage  hops=" + hops + ", messageId=" + 
                messageId + ", currentTime=" + currentTime;
        
    }
    
    
}
