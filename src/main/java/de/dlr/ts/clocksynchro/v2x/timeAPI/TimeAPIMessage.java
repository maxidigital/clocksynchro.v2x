package de.dlr.ts.clocksynchro.v2x.timeAPI;

import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
import de.dlr.ts.commons.tools.BytesTools;
import java.nio.ByteBuffer;
import de.dlr.ts.clocksynchro.v2x.UDPMessageInterface;

/**
 * Process meeages from UDP server (GPS Source9 
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
    public byte[] getBytes()
    {
        currentTime = ClockSource.getInstance().getCurrentTime();
        return BytesTools.getBytes(currentTime);
    }
    
   /**
    *  parses byte array message 
    *  @param byte[] Message in bytes
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
    public long getCurrentTime()
    {
        return currentTime;
    }
}
