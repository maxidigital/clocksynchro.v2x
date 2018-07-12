package de.dlr.ts.clocksynchro.v2x.timeAPI;

import de.dlr.ts.commons.tools.BytesTools;
import java.nio.ByteBuffer;
import de.dlr.ts.clocksynchro.v2x.UDPMessageInterface;

/**
 * Process meeages from UDP server (GPS Source)
 * @see UDPMessageInterface
 */
public class TimeAPIMessage implements UDPMessageInterface
{
    private long currentTime;
    
   /**
    *  gets Message in bytes  
    *  @return byte[] 
    */
    @Override
    public byte[] getBytes() {
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(currentTime);
        return bb.array();
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

   /**
    *  parses byte array message
    *  @param bytes
    */
    @Override
    public void parse(byte[] bytes)
    {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        currentTime = bb.getLong();
    }
    
   /**
    *  gets the current time 
    *  @return curretnTime
    */
    public long getCurrentTime() {
        return currentTime;
    }
}
