package com.rgs.moviechatserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rgs.moviechatserver.ChatRequests.ExitChatRequest;
import com.rgs.moviechatserver.ChatRequests.JoinChatRequest;
import com.rgs.moviechatserver.ChatRequests.ReceiveMessage;
import com.rgs.moviechatserver.ChatRequests.SendMessage;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatManager {

    //2-dimensional array that holds connections; outer dimension represents chat rooms; inner dimension represents users connected
    private ArrayList<ArrayList<Connection>> connectionsList = new ArrayList<ArrayList<Connection>>();

    //Constructor; Fills first dimension of 2D ArrayList and adds listeners to server to start listening for chat requests.
    public ChatManager() {
        for(Movie movie : Main.databaseManager.getMoviesList()) {
            for(ChatRoom chatRoom : movie.getChatRoomsList()) {
                connectionsList.add(new ArrayList<Connection>());
            }
        }
        addChatListeners();
    }

    //Adds listeners to server to start listening for chat requests and act upon the specified request.
    public void addChatListeners() {
        Main.connectionManager.getServer().addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if(object instanceof JoinChatRequest) {     //Detect join room request; Add user from connection list.
                    System.out.println("Connection " + connection.getID() + " is joining chat room " + ((JoinChatRequest)object).getChatRoomID());
                    connectionsList.get(((JoinChatRequest) object).getChatRoomID()-1).add(connection);
                }
                if(object instanceof ExitChatRequest) {     //Detect exit room request; Remove user from connection list.
                    System.out.println("Connection " + connection.getID() + " is exiting chat room " + ((ExitChatRequest)object).getChatRoomID());
                    connectionsList.get(((ExitChatRequest)object).getChatRoomID()-1).remove(connection);
                }
                if(object instanceof SendMessage) {     //Detect message request; Iterate users in the same chat room; Send message response to all but the sending user. Remove user from connections list if user is disconnected.
                    Iterator<Connection> itr = connectionsList.get(((SendMessage)object).getChatRoomID()-1).iterator();
                    while(itr.hasNext()) {
                        Connection c = itr.next();
                        if(c.isConnected()) {
                            if(!connection.equals(c))
                                c.sendTCP(new ReceiveMessage(((SendMessage)object).getMessage()));
                        } else {
                            itr.remove();
                        }
                    }
                }
            }
        });
    }
}
