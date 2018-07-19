/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.heartbeat.Heartbeat;
import de.dlr.ts.clocksynchro.v2x.heartbeat.RemoteStation;
import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.tools.FileTools;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bott_ma
 */
public class Measurements
{    
    private final List<Measurement> elements = new ArrayList<>();
    private final LatencyLinkbird linkbird;
    private int lastStationIndex = 0;        
    
    private String saveToDiskFileName = "measurements/latency.csv";
    
    private Timer timer;
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            
            DLRLogger.info("Saving latency measurements to disk");
            
            try {                                
                boolean wasNew = FileTools.createFile(saveToDiskFileName);                
                FileWriter fw = new FileWriter(saveToDiskFileName, true);
                
                if(wasNew)
                {
                    fw.append(Measurement.toDiskColumnNames());
                    fw.append(System.lineSeparator());
                }                    
                
                for (Measurement m : elements)
                {
                    fw.append(m.toDiskString());
                    fw.append(System.lineSeparator());                    
                    m.setSavedToDisk(true);
                }                
                fw.close();
                
                Iterator<Measurement> it = elements.iterator();
                
                while (it.hasNext()) {
                    Measurement next = it.next();
                    if(next.isSavedToDisk())
                        it.remove();
                }                
            } catch (IOException ex) {
                Logger.getLogger(Measurements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    
    
    
    
    
    /**
     * 
     * @param linkbird 
     */
    public Measurements(LatencyLinkbird linkbird, boolean saveToDisk) {
        this.linkbird = linkbird;
        
        if(saveToDisk)
        {
            timer = new Timer("Measurements saver", true);
            timer.schedule(task, 60_000, 120_000);
        }
    }
    
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
