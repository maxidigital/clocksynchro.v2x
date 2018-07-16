package de.dlr.ts.clocksynchro.v2x;

/**
 * Interface for different message sources
 * 
 */
public interface V2XMessageInterface {
    
    void parse(byte[] bytes);
    byte[] getBytes();
}
