/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.UDPMessageInterface;

/**
 *
 * @author Praktikant-Q2-2015
 */
class HeartbeatMessage implements UDPMessageInterface{

    @Override
    public void parse(byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
