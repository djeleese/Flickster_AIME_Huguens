package com.codepath.flickster;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

//import com.codepath.flickster;

import com.codepath.flickster.adapters.MoviesAdapter;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;
import java.util.logging.Handler;
import java.util.List;

import cz.msebera.android.httpclient.Header;

//List<Movie>movies;
//Add RecyclerView support library to the Gradle build file-Done
//Define a model class to use as the data source-Done
//Add a RecyclerView to your activity to display the items-Done
//Create a custom row layout XML file to visualize the item-Done
//Create a RecyclerView.Adapter and ViewHolder to render the item-Done
//Bind the adapter to the data source to populate the RecyclerView


public class MovieActivity extends AppCompatActivity {
    private static final String MOVIE_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private List<Movie> movies;
    MoviesAdapter adapter;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        RecyclerView rvMovies=findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(this, movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMovies.setAdapter(adapter);
        AsyncHttpClient client= new AsyncHttpClient();

        client.get(MOVIE_URL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonArray =response.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(movieJsonArray));
                    //List<Movie> movies= Movie.fromJsonArray(movieJsonArray);
                    adapter.notifyDataSetChanged();

                    Log.d("smile", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure( statusCode, headers, responseString, throwable );
            }
        });
    }
}
