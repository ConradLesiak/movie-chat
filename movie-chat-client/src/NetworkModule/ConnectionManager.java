package com.rgs.moviechat.NetworkModule;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rgs.moviechat.Assets;
import com.rgs.moviechat.ChatModule.ChatRoom;
import com.rgs.moviechat.ChatModule.Message;
import com.rgs.moviechat.ChatModule.Movie;
import com.rgs.moviechat.Main;
import com.rgs.moviechat.NetworkModule.ChatRequests.ExitChatRequest;
import com.rgs.moviechat.NetworkModule.ChatRequests.JoinChatRequest;
import com.rgs.moviechat.NetworkModule.ChatRequests.ReceiveMessage;
import com.rgs.moviechat.NetworkModule.ChatRequests.SendMessage;
import com.rgs.moviechat.NetworkModule.MainRequests.DataListRequest;
import com.rgs.moviechat.NetworkModule.MainRequests.DataListResponse;
import com.rgs.moviechat.ScreenManager;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionManager {

    private Client client;
    private Kryo kryo;
    public static boolean connected = false;

    //Constructor for initializing the connection manager; Create a client connection and register classes to be serialized for server requests.
    public ConnectionManager() {
        //Start client connection.
        client = new Client(1000000, 1000000);
        client.start();
        addListeners();

        //Classes must be registered in the same order on both the client and server.
        kryo = client.getKryo();
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

    //Start a connection with the server
    public boolean connectToServer() {
        try {
            client.connect(5000, "192.168.0.103", 54555, 54777);
            Main.screenManager.splashScreen.showScreen();
            connected = true;
            return connected;
        } catch (IOException e) {
            System.out.println("Connect to server failed exception");
            ScreenManager.reconnectScreen.showScreen();
            return false;
        }
    }

    //Request moviesList and chatroomsList from the server.
    public void requestDataLists() {
        DataListRequest dataListRequest = new DataListRequest();
        client.sendTCP(dataListRequest);
    }

    //Add listeners for client to receive response from server.
    public void addListeners() {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if(object instanceof DataListResponse) {
                    Assets.dataList = (DataListResponse)object;
                    //Download thumbnail images and add them to thumbnailsList in browse screen
                    Main.screenManager.browseScreen.downloadImages();
                }
            }
        });
    }

    //Getter method
    public Client getClient() {
        return client;
    }
}
