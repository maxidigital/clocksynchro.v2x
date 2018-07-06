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
public class TimeTools 
{
    private final static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
 
    private TimeTools() {
    }
    
    /**
     * 
     * @return 
     */
    public static String getCurrentTimeInString(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 990000);
        
        return "" + formatter.format(calendar.getTime());
    }
    
    /**
     * 
     * @return 
     */
    public static byte[] getMessage(){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, System.currentTimeMillis());
        return buffer.array();
    }
    
}

