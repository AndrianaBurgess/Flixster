package com.example.aburgess11.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by aburgess11 on 6/21/17.
 */
@Parcel
public class Movie {

    public String title;
    public String overview;
    public String posterPath;
    public String backdropPath ;
    public Double voteAverage;
    public Integer id;

    public Movie(JSONObject movie) throws JSONException{
        title = movie.getString("title");
        overview = movie.getString("overview");
        posterPath = movie.getString("poster_path");
        backdropPath = movie.getString("backdrop_path");
        voteAverage = movie.getDouble("vote_average");
        id = movie.getInt("id");

    }

    public Movie (){}

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
}
