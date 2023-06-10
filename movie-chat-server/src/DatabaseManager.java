package com.rgs.moviechatserver;

import com.badlogic.gdx.Gdx;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

    private Connection dbConnection = null;
    private ArrayList<Movie> moviesList = new ArrayList<Movie>();

    //Constructor for initializing the database manager; Connects to existing database if it is found; Exit program if no database is found.
    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:MovieChatDB.db");
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            Gdx.app.exit();
        }
        System.out.println("Successfully connected to database");
    }

    //Fills ArrayList with Movie objects using data from movies table
    public void createMovieList() {
        Statement getMovies = null;
        try {
            getMovies = dbConnection.createStatement();
            ResultSet resultSet = getMovies.executeQuery("SELECT * FROM MOVIES;");
            while(resultSet.next()) {
                Movie movie = new Movie(resultSet.getInt("MovieID"), resultSet.getString("MovieTitle"),
                        resultSet.getString("MovieDescription"), resultSet.getString("YearReleased"),
                        resultSet.getInt("MovieLength"), resultSet.getString("MovieImageUrl"));
                moviesList.add(movie);
            }
            resultSet.close();
            getMovies.close();
        } catch (Exception e) {
            System.out.println("Create movie list failed exception");
        }
    }

    //Fills ArrayList with ChatRoom objects
    public void createChatRoomsList() {
        int id = 0;
        for(Movie movie : moviesList) {
            //int e = (int)Math.ceil((double)movie.getLength() / 900.0);  //Divide movie length by 15 minutes and round up
            int e = 24;
            for(int i = 0; i < e; i++) {
                id++;
                movie.addChatRoom(new ChatRoom(id, i*3600, e*900));
            }
        }
    }

    //Update ChatRooms table in database
    public void updateChatRoomsTable() {
        //clear ChatRooms table
        try {
            Statement delStmt = dbConnection.createStatement();
            int rowsAffected = delStmt.executeUpdate("DELETE FROM ChatRooms");
            Statement resetSequence = dbConnection.createStatement();
            resetSequence.executeUpdate("DELETE FROM sqlite_sequence WHERE name = 'ChatRooms'");
            System.out.println(rowsAffected + " rows deleted from ChatRooms table");
        } catch(Exception e) {
            System.out.println("Delete ChatRooms records failed exception");
        }
        //update ChatRooms table with new data from ArrayList
        String insert = "INSERT INTO ChatRooms(MovieID,StartTime,StartInterval) VALUES(?,?,?)";
        int rowsInserted = 0;
        try {
            PreparedStatement insertStmt = dbConnection.prepareStatement(insert);
            for(Movie movie : moviesList) {
                for(ChatRoom chatRoom : movie.getChatRoomsList()) {
                    insertStmt.setInt(1, movie.getID());
                    insertStmt.setInt(2, chatRoom.getStartTime());
                    insertStmt.setInt(3, chatRoom.getStartInterval());
                    rowsInserted += insertStmt.executeUpdate();
                }
            }
            System.out.println(rowsInserted + " rows inserted into ChatRooms table");
        } catch (Exception e) {
            System.out.println("Insert chat room records failed exception");
        }
    }

    //Method disconnects from database; called on server close
    public void disconnect() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("Disconnect from database failed exception");
        }
    }

    //Getter method
    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }
}
