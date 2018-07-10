package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.UDPMessageInterface;

/**
 *  Process GPS time messages
 *  @see UDPMessageInterface
 */
public class GPSMessage implements UDPMessageInterface
{
   /**
    *  parses byte array message 
    *  @param byte[] Message in bytes
    */
    @Override
    public void parse(byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   /**
    *  gets Message in bytes  
    *  @return byte[] 
    */
    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
