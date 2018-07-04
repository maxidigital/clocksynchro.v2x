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

/**
 *
 * @author Praktikant-Q2-2015
 */
public class Linkbird implements CCU.Listener
{
    private static final Linkbird INSTANCE = new Linkbird();
    
    private CCU ccu;
    
    private Linkbird() 
    {
    }

    public void start()
    {
        ccu = LinkbirdFactory.createCCU();
        ccu.addListener(this);
        ccu.setCCUAddress(Config.getInstance().getLinkbirdOutgoingAddress());
        ccu.setDataInputPort(Config.getInstance().getLinkbirdIncomingPort());
        ccu.setDataOutputPort(Config.getInstance().getLinkbirdOutgoingPort());
        ccu.startReceiver();
    }

    @Override
    public void newIncomingMessage(CCUMessage im) {
        
        if(im.getDestinationPort() != 2100)
            return;
        
        byte[] payload = im.getPayload();
        
        String string = new String(payload);
        System.out.println(string);
    }
    
    public void send(byte[] payload)
    {
        CCUMessage mess = ccu.createCCUMessage();
        mess.setDisseminationType(DisseminationType.TOPO_SCOPED_BROADCAST_SINGLEHOP);
        mess.setDestinationPort(3333);
        mess.setPayload(payload);
        
        ccu.sendMessage(mess);
    }   

    public static Linkbird getInstance() {
        return INSTANCE;
    }
    
    
}
