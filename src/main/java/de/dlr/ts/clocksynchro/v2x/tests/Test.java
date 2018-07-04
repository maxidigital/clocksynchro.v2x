/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.tests;

import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.logger.LogLevel;
import de.dlr.ts.commons.network.udp.UDPClient;
import de.dlr.ts.commons.network.udp.UDPServer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bott_ma
 */
public class Test implements UDPServer.Listener
{    
    public static void main(String[] args) {
        //DLRLogger.info("Hello World");
        
        
    }
    
    
    public void start()
    {
        try {
            UDPClient client = new UDPClient("localhost", 1111);            
            client.send("hello world".getBytes());
            
            UDPServer server = new UDPServer(2222, this);
            server.start();
            
        } catch (SocketException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void newDatagramPacket(DatagramPacket dp)
    {
        byte[] data = new byte[dp.getLength()];
        System.arraycopy(dp.getData(), dp.getOffset(), data, 0, data.length);
        
        System.out.println("Incoming message: " + new String(data));
    }
}
