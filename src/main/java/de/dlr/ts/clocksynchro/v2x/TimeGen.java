/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.logger.LogLevel;


/**
 *
 * @author Praktikant-Q2-2015
 */
public class TimeGen 
{
    private long time = System.currentTimeMillis();
    
    public static void main (String[]args){
        
        new TimeGen().print();
    }
    
    public void print(){
        DLRLogger.info(this, String.valueOf(time));
    }
    
   
}

