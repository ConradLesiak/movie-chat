package com.rgs.moviechatserver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.rgs.moviechatserver.ChatRequests.ExitChatRequest;
import com.rgs.moviechatserver.ChatRequests.JoinChatRequest;
import com.rgs.moviechatserver.ChatRequests.ReceiveMessage;
import com.rgs.moviechatserver.ChatRequests.SendMessage;
import com.rgs.moviechatserver.MainRequests.DataListRequest;
import com.rgs.moviechatserver.MainRequests.DataListResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ConnectionManager {

    private Server server;
    private Kryo kryo;

    //Opens the server connection to the binding ports on the host device IP address.
    public ConnectionManager() {
        server = new Server(1000000, 1000000);
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            System.out.println("Server binding failed exception.");
        }
        addListeners();
        //Register classes to server; must be in same order as the client.
        kryo = server.getKryo();
        kryo.register(DataListRequest.class);
        kryo.register(ArrayList.class);
        kryo.register(Movie.class);
        kryo.register(ChatRoom.class);
        kryo.register(DataListResponse.class);
        kryo.register(JoinChatRequest.class);
        kryo.register(ExitChatRequest.class);
        kryo.register(Message.class);
        kryo.register(SendMessage.class);
        kryo.register(ReceiveMessage.class);
    }

    //Add listeners for server to receive requests from client
    public void addListeners() {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if(object instanceof DataListRequest) {
                    DataListResponse dataListResponse = new DataListResponse(Main.databaseManager.getMoviesList());
                    connection.sendTCP(dataListResponse);
                }
            }
        });
    }

    public Server getServer() {
        return server;
    }
}
