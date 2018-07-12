/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dlr.ts.clocksynchro.v2x.heartbeat;

/**
 *
 * @author Praktikant-Q2-2015
 */
class RemoteStation
{
    int stationId;
    int lastMessageId;
    long systemStartTime;
    
    int hopsToReach;
    
    long messageTimeInMillis;
    int deltaTimeInMillis;
    
    //int messagesCount;
}
