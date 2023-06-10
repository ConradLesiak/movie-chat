package com.rgs.moviechat.NetworkModule.MainRequests;

import com.rgs.moviechat.ChatModule.Movie;
import java.util.ArrayList;

public class DataListResponse {

    private ArrayList<Movie> moviesList;

    public DataListResponse() {

    }

    public DataListResponse(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }
}
