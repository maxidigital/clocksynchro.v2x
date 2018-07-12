package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.commons.network.udp.UDPServer;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Receives time message from GPS source 
 * @see UDPServer.Listener
 * @see ClockSourceInterface
 */
class GPSSource implements UDPServer.Listener, ClockSourceInterface
{
    private int deltaTime = 0;
    private UDPServer udpServer;
    private boolean enabled = false;
    
    
    /**
     * Retrieves data from recieved time message from GPS source 
     * @param DatagramPacket 
     * 
     */
    @Override
    public void newDatagramPacket(DatagramPacket dp) {
        byte[] payload = dp.getData();
        //TimeAPIMessage mess = new TimeAPIMessage();
        //mess.parse(payload);
        
        //TODO set delta time
        deltaTime = 13000;
        
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }
    
   /**
    * Starts receiving time Messages from UDP server
    */
    @Override
    public void start() {
        
        try {
            udpServer = new UDPServer(Config.getInstance().getGpsInputPort(), this);
        } catch (SocketException ex) {
            Logger.getLogger(GPSSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        udpServer.start();
    }
    
   /**
    * gets the time differnece 
    * @return DeltaTime
    */
    @Override
    public int getDeltaTime() {
        return deltaTime;
    }
    
}
