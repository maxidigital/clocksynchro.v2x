/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.commons.logger.DLRLogger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Praktikant-Q2-2015
 */
public class TimeUpdate {
     private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     
     
     public void start(){
         TimeUpdate obj = new TimeUpdate(); 
         
         String value = TimeTools.getCurrentTimeInString();
         Date date = null;
         try {
             date = dateFormat.parse(value);
         } catch (ParseException ex) {
             Logger.getLogger(TimeUpdate.class.getName()).log(Level.SEVERE, null, ex);
         }
        value = "\"" + dateFormat.format(date) + "\"";        
        //value = dateFormat.format(date);
        
        String tmp = "date --set " + value;        
        System.out.println("Command " + tmp);
        String[] command = tmp.split(" ");                
        execCommand(command);         
        execCommand("date".split(" "));
     }
     
     public String execCommand(String[] command){
         StringBuffer output = new StringBuffer();
         Process p;
         
         //2011-12-07 01:20:15.962"
         try {
            p = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
         return output.toString();
    }
}
