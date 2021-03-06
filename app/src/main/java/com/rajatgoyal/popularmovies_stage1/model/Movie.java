package com.rajatgoyal.popularmovies_stage1.model;

/**
 * Created by rajat on 21/8/17.
 */

public class Movie {
    private int id;
    private String poster_path;

    public Movie(int id, String poster_path) {

        this.id = id;

        String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
        this.poster_path = POSTER_BASE_URL + poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
