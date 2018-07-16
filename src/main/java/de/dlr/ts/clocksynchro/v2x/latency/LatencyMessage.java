/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.latency;

import de.dlr.ts.clocksynchro.v2x.V2XMessageInterface;
import java.nio.ByteBuffer;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author bott_ma
 */
class LatencyMessage implements V2XMessageInterface
{    
    @Getter @Setter private int measurementId;
    @Getter @Setter private int originStationId;
    @Getter @Setter private int desTinationStationId;
    @Getter @Setter private int tripId = 0;    
    
    @Getter @Setter private byte[] extraPayload;

    @Override
    public String toString() {
        return "Id: " + measurementId + ", originStaId: " + originStationId +
                ", desTId: " + desTinationStationId + ", tripId: " + tripId;
    }            
    
    @Override
    public void parse(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        
        this.measurementId = bb.getShort();
        this.originStationId = bb.getInt();
        this.desTinationStationId = bb.getInt();
        this.tripId = bb.getShort();        
    }

    public void increaseTripId() {
        tripId++;
    }
            
    
    @Override
    public byte[] getBytes() {
        ByteBuffer bb = ByteBuffer.allocate(12);
        bb.putShort((short) measurementId);
        bb.putInt(originStationId);
        bb.putInt(desTinationStationId);
        bb.putShort((short) tripId);
        
        return bb.array();
    }
    
    public void createExtraPayload(int size)
    {
        
    }
}
