package de.dlr.ts.clocksynchro.v2x.clocksource;

/**
 *  Interface for different clock sources
 * 
 */
interface ClockSourceInterface {
    
   /**
    *  gets the time difference
    * 
    */
    int getDeltaTime();
    
   /**
    *  Starts listening for time messages
    * 
    */
    void start();
}
