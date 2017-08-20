package com.rajatgoyal.popularmovies_stage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_movies;
    private MoviesAdapter adapter;

    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?api_key=&language=en-US&page=1";

    final static String API_PARAM = "api_key";
    final static String LANG_PARAM = "lang";
    final static String PAGE_PARAM = "page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_movies = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_movies.setLayoutManager(layoutManager);

        adapter = new MoviesAdapter();
        rv_movies.setAdapter(adapter);

        loadMoviesData();
    }

    private void loadMoviesData() {

    }

}
