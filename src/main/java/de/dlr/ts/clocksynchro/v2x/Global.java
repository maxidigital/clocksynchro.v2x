/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Praktikant-Q2-2015
 */
public class Global {
    private static final Global INSTANCE = new Global();
    
    //private long deltaTime;
    private final long systemStartTime = System.currentTimeMillis();
    private final SimpleDateFormat onlyTime = new SimpleDateFormat("HH:mm:ss.SSS");

    public SimpleDateFormat getOnlyTime() {
        return onlyTime;
    }

    public String getOnlyTime(long time) {
        return onlyTime.format(new Date(time));
    }
    
    public long getSystemStartTime() {
        return systemStartTime;
    }
    
    public static Global getInstance() {
        return INSTANCE;
    }
    
}
