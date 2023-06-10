package com.rgs.moviechat.NetworkModule;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rgs.moviechat.Main;
import com.rgs.moviechat.NetworkModule.ChatRequests.ReceiveMessage;

public class ChatManager {

    //Constructor for initializing the chat manager;
    public ChatManager() {
        addChatListeners();
    }

    //Adds client listener for incoming chat messages from the server; Listener displays messages received.
    public void addChatListeners() {
        Main.connectionManager.getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if(object instanceof ReceiveMessage) {
                    Main.screenManager.chatScreen.showMessage(((ReceiveMessage) object).getMessage());
                }
            }
        });
    }
}
