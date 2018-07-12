package de.dlr.ts.clocksynchro.v2x;

/**
 * Interface for different message sources
 * 
 */
public interface UDPMessageInterface {
    
    void parse(byte[] bytes);
    byte[] getBytes();
}
