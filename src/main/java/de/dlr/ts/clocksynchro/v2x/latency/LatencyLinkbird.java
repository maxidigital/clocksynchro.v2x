/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.MainLinkbird;
import de.dlr.ts.clocksynchro.v2x.MessagesListener;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCU;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCUMessage;

/**
 *
 * @author bott_ma
 */
public class LatencyLinkbird  implements CCU.Listener
{
    private final MessagesListener listener;
    
    public LatencyLinkbird(MessagesListener listener) {
        this.listener = listener;
    }

    public void init() {
        MainLinkbird.getInstance().addListener(this);                
    }
    
    @Override
    public void newIncomingMessage(CCUMessage im) {
        if(im.getDestinationPort() == Config.getInstance().getMeasurementBTPPort())
            listener.receivePayload(im.getPayload());
    }
    
    public void send(byte[] data) {
        MainLinkbird.getInstance().send(data, Config.getInstance().getMeasurementBTPPort());
    }
    
}
