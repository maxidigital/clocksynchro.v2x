/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.commons.logger.DLRLogger;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;



/**
 *
 * @author Praktikant-Q2-2015
 */
public class Time 
{
    private static final Time INSTANCE = new Time();
    private long time;
    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    private String s = "";
 
    {
        time = System.currentTimeMillis();
        calendar.setTimeInMillis(time);
    }
    
    public String getTime(){
        return s + formatter.format(calendar.getTime());
    }
    
    public byte[] getMessage(){
        buffer.putLong(0, time);
        return buffer.array();
    }
    
    public static Time getInstance() {
        return INSTANCE;
    }
}

