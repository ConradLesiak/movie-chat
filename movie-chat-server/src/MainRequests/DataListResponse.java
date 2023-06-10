package com.rgs.moviechatserver.MainRequests;

import com.rgs.moviechatserver.Movie;
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
