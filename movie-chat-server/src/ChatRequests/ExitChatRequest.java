package com.rgs.moviechatserver.ChatRequests;

public class ExitChatRequest {

    private int chatRoomID;

    public ExitChatRequest() {

    }

    public ExitChatRequest(int chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public int getChatRoomID() {
        return chatRoomID;
    }
}
