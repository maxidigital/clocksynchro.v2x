package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.clocksynchro.v2x.clocksource.ClockSource;
import de.dlr.ts.clocksynchro.v2x.timebroadcaster.LinkbirdBroadcaster;
import de.dlr.ts.clocksynchro.v2x.timeAPI.TimeAPISender;
import de.dlr.ts.clocksynchro.v2x.timebroadcaster.TimeBroadcaster;
import de.dlr.ts.commons.logger.DLRLogger;
import de.dlr.ts.commons.network.udp.UDPClient;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

/**
 *
 * @author bott_ma
 */
public class Main
{
    public static void main(String[] args) throws SocketException, IOException {
        new Main().start();
    }
    
    public void start() throws SocketException, IOException {        
        
        DLRLogger.config(this, "Starting Clock Synchro");
        
        Config.getInstance().load();
        TimeAPISender.getInstance().start();
        
        ClockSource.getInstance().start();
        
        if(Config.getInstance().getStationType() == Config.StationType.GENERATOR)
            TimeBroadcaster.getInstance().start();

    }
}
