package com.rgs.moviechatserver.ChatRequests;

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
