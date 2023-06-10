package com.rgs.moviechatserver;

public class ChatRoom {

    private int id;
    private int startTime;  //Movie start time in seconds
    private int startInterval;  //Movie start interval in seconds

    public ChatRoom() {

    }

    public ChatRoom(int id, int startTime, int startInterval) {
        this.id = id;
        this.startTime = startTime;
        this.startInterval = startInterval;
    }

    public int getID() {
        return id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getStartInterval() {
        return startInterval;
    }
}
