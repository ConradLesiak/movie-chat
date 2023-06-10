package com.rgs.moviechatserver;

import java.util.ArrayList;

public class Movie {

    private int id;
    private String title;
    private String description;
    private String year;
    private int length;     //Time in seconds
    private String thumbnailURL;
    private ArrayList<ChatRoom> chatRoomsList;

    public Movie() {

    }

    public Movie(int id, String title, String description, String year, int length, String thumbnailURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.length = length;
        this.thumbnailURL = thumbnailURL;
        chatRoomsList = new ArrayList<ChatRoom>();
    }

    //Setter method
    public void addChatRoom(ChatRoom chatRoom) {
        chatRoomsList.add(chatRoom);
    }

    //Getter methods
    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getYear() {
        return year;
    }

    public int getLength() {
        return length;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public ArrayList<ChatRoom> getChatRoomsList() {
        return chatRoomsList;
    }
}
