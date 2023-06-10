package com.rgs.moviechatserver.ChatRequests;

import com.rgs.moviechatserver.Message;

public class ReceiveMessage {

    private Message message;

    public ReceiveMessage() {

    }

    public ReceiveMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
