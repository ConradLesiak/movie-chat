package com.rgs.moviechatserver.ChatRequests;

import com.rgs.moviechatserver.Message;

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
