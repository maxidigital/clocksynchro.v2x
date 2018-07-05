/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Praktikant-Q2-2015
 */
public class TimeUpdate {
     private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     
     
     public void start(){
         try {
            String value = Time.getInstance().getTime();
            Date date = dateFormat.parse(value);
            value = dateFormat.format(date);
            final Process dateProcess = Runtime.getRuntime().exec("sudo date --set " +value);
            dateProcess.waitFor();
            dateProcess.exitValue();
            final Process timeProcess = Runtime.getRuntime().exec("TS_labor_48");
            timeProcess.waitFor();
            timeProcess.exitValue();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
