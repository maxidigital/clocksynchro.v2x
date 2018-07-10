package de.dlr.ts.clocksynchro.v2x.clocksource;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.commons.network.udp.UDPServer;
import java.net.DatagramPacket;
import de.dlr.ts.clocksynchro.v2x.timeAPI.TimeAPIMessage;
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
    private long deltaTime = 0;
    private UDPServer udpServer;
    
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
    public long getDeltaTime() {
        return deltaTime;
    }
    
}
