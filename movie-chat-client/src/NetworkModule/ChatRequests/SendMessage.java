package com.rgs.moviechat.NetworkModule.ChatRequests;

import com.rgs.moviechat.ChatModule.Message;

public class SendMessage {

    private int chatRoomID;
    private Message message;

    public SendMessage() {

    }

    public SendMessage(int chatRoomID, Message message) {
        this.chatRoomID = chatRoomID;
        this.message = message;
    }

    public int getChatRoomID() {
        return chatRoomID;
    }

    public Message getMessage() {
        return message;
    }
}
