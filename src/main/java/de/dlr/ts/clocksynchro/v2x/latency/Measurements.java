/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.heartbeat.Heartbeat;
import de.dlr.ts.clocksynchro.v2x.heartbeat.RemoteStation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bott_ma
 */
public class Measurements
{    
    private final List<Measurement> elements = new ArrayList<>();
    private final LatencyLinkbird linkbird;

    /**
     * 
     * @param linkbird 
     */
    public Measurements(LatencyLinkbird linkbird) {
        this.linkbird = linkbird;
    }
    
    private int lastStationIndex = 0;
    
    /**
     * 
     * @return 
     */
    public Measurement getNewMeasurement()
    {
        Measurement mea = new Measurement(linkbird);        
        elements.add(mea);
        
        RemoteStation[] stas = Heartbeat.getInstance().getHopZeroStations();                                
        
        if(stas.length == 0)
            return null;
        
        if(stas.length == 1)
            mea.setDestinationStationId(stas[0].getStationId());
        else
        {
            int a = lastStationIndex++ % stas.length;
            mea.setDestinationStationId(stas[a].getStationId());  
        }
        
        return mea;
    }
}
