package com.rgs.moviechat.NetworkModule.ChatRequests;

import com.rgs.moviechat.ChatModule.Message;

public class ReceiveMessage {

    public Message message;

    public ReceiveMessage() {

    }

    public ReceiveMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
