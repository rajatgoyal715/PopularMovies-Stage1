package com.rajatgoyal.popularmovies_stage1.model;

/**
 * Created by rajat on 21/8/17.
 */

public class Movie {
    String title;
    String description;
    String poster_path;
    double vote;

    public Movie(String title, String description, String poster_path, double vote) {

        String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

        this.title = title;
        this.description = description;
        this.poster_path = POSTER_BASE_URL + poster_path;
        this.vote = vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }
}
