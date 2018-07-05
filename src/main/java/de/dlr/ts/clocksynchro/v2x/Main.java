/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.commons.logger.DLRLogger;
import java.util.Arrays;

/**
 *
 * @author bott_ma
 */
public class Main
{
    public static void main(String[] args) {
        new Main().start();
    }
    
    public void start() {        
        
        Config.getInstance().load();
        Linkbird.getInstance().start();
        byte[] message = new Time().getMessage();
        Linkbird.getInstance().send(message);
        new TimeUpdate().start();
    }
}
