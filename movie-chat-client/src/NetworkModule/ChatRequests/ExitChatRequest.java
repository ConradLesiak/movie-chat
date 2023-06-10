package com.rgs.moviechat.NetworkModule.ChatRequests;

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
