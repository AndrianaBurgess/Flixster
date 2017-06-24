package com.example.aburgess11.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aburgess11.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.example.aburgess11.flixster.MovieListActivity.API_BASE_URL;
import static com.example.aburgess11.flixster.MovieListActivity.API_KEY_PARAM;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    AsyncHttpClient client;
    @Nullable @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @Nullable @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    String movieId;
    @BindView(R.id.ivBackdrop) ImageView ivBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(), "detail", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);



        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        client = new AsyncHttpClient();

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        Glide.with(ivBackdrop.getContext()).load(movie.getBackdropPath()).into(ivBackdrop);


        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }

    @OnClick(R.id.ivBackdrop)
    public void onClick(){
        getTrailer();
    }
    private void getTrailer(){

        String url = API_BASE_URL + "/movie/" + movie.getId().toString() + "/videos";

        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM,getString(R.string.api_key)); //API key, always requires
        //execute a GET request expecting a JSON object response
        client.get(url,params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // log the results into movie list
                try {
                    JSONArray results = response.getJSONArray("results");
                    movieId = results.getJSONObject(0).getString("key");
                    Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    intent.putExtra("key", movieId);
                    MovieDetailsActivity.this.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
