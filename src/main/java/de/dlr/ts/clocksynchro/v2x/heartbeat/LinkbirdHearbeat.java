/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Config;
import de.dlr.ts.clocksynchro.v2x.MainLinkbird;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCU;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCUMessage;
import de.dlr.ts.clocksynchro.v2x.MessagesListener;

/**
 *
 * @author Praktikant-Q2-2015
 */
class LinkbirdHearbeat implements CCU.Listener
{
    private final MessagesListener listener;

    public LinkbirdHearbeat(MessagesListener listener) {
        this.listener = listener;
    }
    
    public void init() {
        MainLinkbird.getInstance().addListener(this);
    }
    
    @Override
    public void newIncomingMessage(CCUMessage ccum)
    {
        if(ccum.getDestinationPort() != Config.getInstance().getHeartbeatBTPPort())
            return;
        
        listener.receivePayload(ccum.getPayload());
    }
    
    public void send(byte[] payload) {
        MainLinkbird.getInstance().send(payload, Config.getInstance().getHeartbeatBTPPort());
    }
}
