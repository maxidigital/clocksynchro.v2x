/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.clocksynchro.v2x.latency.Latency;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bott_ma
 */
public class MyTools {
    
    public static void sleepi(long time)
    {
        try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Latency.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
