/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x;

import de.dlr.ts.c2x.devices.linkbird.LinkbirdFactory;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCU;
import de.dlr.ts.commons.c2x.interfaces.ccu.CCUMessage;
import de.dlr.ts.commons.c2x.interfaces.ccu.DisseminationType;
import de.dlr.ts.commons.logger.DLRLogger;

/**
 *
 * @author Praktikant-Q2-2015
 */
public class MainLinkbird {
    private static final MainLinkbird instance = new MainLinkbird();
    private CCU ccu;
    
    public void start()
    {
        DLRLogger.config(this, "Starting main linkbird");
                
        ccu = LinkbirdFactory.createCCU();
        ccu.setCCUAddress(Config.getInstance().getLinkbirdOutgoingAddress());
        ccu.setDataInputPort(Config.getInstance().getLinkbirdIncomingPort());
        ccu.setDataOutputPort(Config.getInstance().getLinkbirdOutgoingPort());
        ccu.startReceiver();
    }
    
    public void addListener(CCU.Listener listener) {
        ccu.addListener(listener);
    }
    
    public static MainLinkbird getInstance() {
        return instance;
    }
    
    public void send(byte[] payload, int btpPort)
    {
        CCUMessage mess = ccu.createCCUMessage();
        mess.setDisseminationType(DisseminationType.TOPO_SCOPED_BROADCAST_SINGLEHOP);
        mess.setDestinationPort(btpPort);
        mess.setPayload(payload);
        
        ccu.sendMessage(mess);
    }
}
