
package de.dlr.ts.clocksynchro.v2x.timebroadcaster;

import java.nio.ByteBuffer;
import de.dlr.ts.clocksynchro.v2x.V2XMessageInterface;

/**
 * Process meeages from linkbird
 * @see V2XMessageInterface
 */
public class V2XTimeMessage implements V2XMessageInterface
{   
    private static int messageIdCounter = 0;
    private int messageId;
    private byte hops;
    private long currentTime;
    
   /**
    * gets message ID  
    * @return messageID
    */
    public int getMessageId() {
        return messageId;
    }

    public static int getMessageIdCounter() {
        return messageIdCounter++;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setHops(byte hops) {
        this.hops = hops;
    }
    
   /**
    * Gets number of hops  
    * @return hops
    */
    public int getHops() {
        return hops;
    }
    
   /**
    * gets current time  
    * @return current time 
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
    *  @param data Message in bytes
    */
    @Override
    public void parse(byte[] data)
    {
        ByteBuffer bb = ByteBuffer.wrap(data);
        hops = bb.get();
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
        ByteBuffer bb = ByteBuffer.allocate(13);
        bb.put(hops);               //1
        bb.putInt(messageId);       //4
        bb.putLong(currentTime);    //8
        
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
