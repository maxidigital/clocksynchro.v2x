/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
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
    private final SimpleDateFormat onlyTime = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat onlyTimeWithMillis = new SimpleDateFormat("HH:mm:ss.SSS");
    
    private final SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.YY HH:mm:ss");

    public SimpleDateFormat getOnlyTimeWithMillis() {
        return onlyTimeWithMillis;
    }

    public SimpleDateFormat getDateTime() {
        return dateTime;
    }
       
    public SimpleDateFormat getOnlyTime() {
        return onlyTime;
    }

    public String getOnlyTime(long time) {
        return onlyTime.format(new Date(time));
    }
    
    public long getSystemStartTime() {
        return systemStartTime + ClockSource.getInstance().getDeltaTime();
    }
    
    public static Global getInstance() {
        return INSTANCE;
    }
    
    public static String dateInWords(long time)
    {
        StringBuilder sb = new StringBuilder();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long weeksInMilli = daysInMilli * 7;
        long monthsInMilli = weeksInMilli * 4;
        long yearsInMilli = monthsInMilli * 12;
        
        if(time >= yearsInMilli){
            long yearsAgo = time / yearsInMilli;
            time = time % yearsInMilli;
            sb.append(yearsAgo).append("y").append(",");
        }
        
        if(time >= monthsInMilli){
            long monthsAgo = time / monthsInMilli;
            time = time % monthsInMilli;
            sb.append(monthsAgo).append("m").append(",");
        }        
        
        if(time >= weeksInMilli){
            long weeksAgo = time / weeksInMilli;
            time = time % weeksInMilli;
            sb.append(weeksAgo).append("w").append(",");
        }
        
        if(time >= daysInMilli){
            long daysAgo = time / daysInMilli;
            time = time % daysInMilli;
            sb.append(daysAgo).append("d").append(",");
        }
        
        if(time >= hoursInMilli){
            long hoursAgo = time / hoursInMilli;
            time = time % hoursInMilli;
            sb.append(hoursAgo).append("h").append(",");
        }
        
        if(time >= minutesInMilli){
            long minutesAgo = time / minutesInMilli;
            time = time % minutesInMilli;
            sb.append(minutesAgo).append("m").append(",");
        }
        
        if(time >= secondsInMilli){
            long secondsAgo = time / secondsInMilli;
            sb.append(secondsAgo).append("s ago");
        }
        return sb.toString();
    }
}
