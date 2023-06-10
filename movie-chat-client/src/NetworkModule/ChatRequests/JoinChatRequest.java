package com.rgs.moviechat.NetworkModule.ChatRequests;

public class JoinChatRequest {

    private int chatRoomID;

    public JoinChatRequest() {

    }

    public JoinChatRequest(int chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public int getChatRoomID() {
        return chatRoomID;
    }
}
