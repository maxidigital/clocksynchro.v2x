/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

import de.dlr.ts.clocksynchro.v2x.Global;
import de.dlr.ts.clocksynchro.v2x.UDPMessageInterface;
import java.nio.ByteBuffer;

/**
 *
 * @author Praktikant-Q2-2015
 */
class HeartbeatMessage implements UDPMessageInterface
{
    private static int messageIdCounter = 0;
    
    private int messageId;
    private int stationId;
    private byte startingHopValue;
    private byte hops;
    private long currentTime;
    private long systemStartTime;
    private int deltaTime;
    
    
    public String getName()
    {
        return stationId + ":" + messageId;
    }
    
    public int getStationId() {
        return stationId;
    }

    public void setHops(byte hops) {
        this.hops = hops;
    }

    public static int getMessageIdCounter() {
        return messageIdCounter++;
    }
    
    public static int getCurrentMessageId() {
        return messageIdCounter;
    }
    
    public int getDeltaTime() {
        return deltaTime;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setStartingHopValue(byte startingHopValue) {
        this.startingHopValue = startingHopValue;
    }

    public void setSystemStartTime(long systemStartTime) {
        this.systemStartTime = systemStartTime;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
    
    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }
    
    public long getCurrentTime()
    {
        return currentTime;
    }
    
    public int getHops()
    {
        return hops;
    }

    public int getMessageId() {
        return messageId;
    }
    
    public long getSystemStartTime()
    {
        return systemStartTime;
    }
    
    public void decreaseHop()
    {
        if(hops > 0)
            hops--;
    }

    public int getStartingHopValue() {
        return startingHopValue;
    }

    
    @Override
    public void parse(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        messageId = bb.getInt();
        stationId = bb.getInt();
        startingHopValue = bb.get();
        hops = bb.get();
        deltaTime = bb.getInt();
        currentTime = bb.getLong();
        systemStartTime = bb.getLong();
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer bb = ByteBuffer.allocate(36);
        bb.putInt(messageId);           //4
        bb.putInt(stationId);           //4
        bb.put(startingHopValue);    //4
        bb.put(hops);                   //4
        bb.putInt(deltaTime);           //4
        bb.putLong(currentTime);        //8
        bb.putLong(systemStartTime);    //8
        
        return bb.array();
    }
    
    public int getHopsToReach() {
        return startingHopValue - hops;
    }
    
   /**
    *  adds message parameters to a string  
    *  @return message parameters
    */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder("HeatbeatMessage ");
        sb.append("stationId=").append(stationId).append(", ");
        sb.append("messageId=").append(messageId).append(", ");        
        sb.append("startingHop=").append(startingHopValue).append(", ");
        sb.append("hops=").append(hops).append(", ");
        sb.append("currentTime=").append(Global.getInstance().getOnlyTime(currentTime)).append(", ");
        sb.append("deltaTime=").append(deltaTime).append(", ");
        sb.append("systemStartTime=").append(systemStartTime);
        
        return sb.toString();
    }
}
