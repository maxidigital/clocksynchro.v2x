/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.tests;

import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.logger.LogLevel;

/**
 *
 * @author bott_ma
 */
public class Test2 {
    
    public static void main(String[] args) {
        new Test2().start();
    }
    
    void start()
    {
        DLRLogger.setLevel(LogLevel.INFO);
        DLRLogger.info(this, "Hello");
        DLRLogger.config("ttttttttttttttttttttttttttttt");
        DLRLogger.severe("heloooooooooooooooooooo");

    }
}
