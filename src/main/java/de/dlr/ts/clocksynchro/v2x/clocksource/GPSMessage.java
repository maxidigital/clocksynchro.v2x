package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.V2XMessageInterface;

/**
 *  Process GPS time messages
 *  @see V2XMessageInterface    
 */
public class GPSMessage implements V2XMessageInterface
{
   /**
    *  parses byte array message 
    *  @param bytes Message in bytes
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
