package com.rajatgoyal.popularmovies_stage1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rajatgoyal.popularmovies_stage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_movies;
    private MoviesAdapter mMoviesAdapter;

    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String TOP_RATED_MOVIES_URL = "https://api.themoviedb.org/3/movie/top_rated";

    private static final String API_KEY = "PLACE_YOUR_API_KEY_HERE";
    private static final String lang = "en-US";
    private static final int page = 1;

    final static String API_PARAM = "api_key";
    final static String LANG_PARAM = "language";
    final static String PAGE_PARAM = "page";

    public static final String TAG = "MainActivity";

    // 0 for popular movies and 1 for top rated movies
    private static int POPULAR_OR_TOP_RATED;

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    public static final String MY_PREFS = "popular";

    public static Movie[] moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO(1) Check if any shared preference stored reagarding the sort order
        getChoice();

        rv_movies = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_movies.setLayoutManager(layoutManager);

        mMoviesAdapter = new MoviesAdapter();
        rv_movies.setAdapter(mMoviesAdapter);

        loadMoviesData();
    }

    public void getChoice() {
        Context context = MainActivity.this;
        sharedPref = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);

        int choice = sharedPref.getInt("choice", -1);
        POPULAR_OR_TOP_RATED = (choice == -1) ? 0 : choice;
    }

    private void loadMoviesData() {
        URL moviesUrl = buildUrl();
        new MoviesFetchTask().execute(moviesUrl);
    }

    public URL buildUrl() {
        String BASE_URL = (POPULAR_OR_TOP_RATED == 0) ? POPULAR_MOVIES_URL : TOP_RATED_MOVIES_URL;

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(LANG_PARAM, lang)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(page))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public class MoviesFetchTask extends AsyncTask<URL, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(URL... params) {
            if (params.length == 0) {
                return null;
            }

            URL moviesRequestUrl = params[0];
            Log.d(TAG, "doInBackground: moviesRequestUrl: " + moviesRequestUrl.toString());

            try {
                RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this);
                mRequestQueue.start();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        moviesRequestUrl.toString(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray results = response.getJSONArray("results");

                                    String title, description, poster_path;
                                    double vote;

                                    Movie[] movies = new Movie[results.length()];

                                    for (int i = 0; i < results.length(); i++) {
                                        title = results.getJSONObject(i).getString("title");
                                        description = results.getJSONObject(i).getString("overview");
                                        poster_path = results.getJSONObject(i).getString("poster_path");
                                        vote = results.getJSONObject(i).getDouble("vote_average");

                                        movies[i] = new Movie(title, description, poster_path, vote);
//                                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                                    }

                                    mMoviesAdapter.setMovieData(movies);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                            }
                        }
                );

                mRequestQueue.add(jsonObjectRequest);
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: error: " + e.getMessage());
            }

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        int earlier = POPULAR_OR_TOP_RATED;

        if (id == R.id.action_popular) {
            POPULAR_OR_TOP_RATED = 0;
        }

        if (id == R.id.action_top_rated) {
            POPULAR_OR_TOP_RATED = 1;
        }

        updateSharedPref();

        if (earlier != POPULAR_OR_TOP_RATED) {
            loadMoviesData();
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO (2) Update according to POPULAR_OR_TOP_RATED
    public void updateSharedPref() {
        editor = sharedPref.edit();
        editor.putInt("choice", POPULAR_OR_TOP_RATED);
        editor.commit();
    }

    // TODO (3) Store data on orientation change

    @Override
    protected void onPause() {
        moviesList = mMoviesAdapter.getMovieData();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoviesAdapter.setMovieData(moviesList);
        mMoviesAdapter.notifyDataSetChanged();
    }

    // TODO (4) OnItemClickListener
    // TODO (5) New layout file showing detail of the movie
}
