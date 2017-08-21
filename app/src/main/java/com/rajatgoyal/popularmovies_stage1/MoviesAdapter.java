package com.rajatgoyal.popularmovies_stage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rajatgoyal.popularmovies_stage1.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by rajat on 20/8/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private Movie[] moviesList;
    private Context context;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Picasso.with(context).load(moviesList[position].getPoster_path()).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        if (moviesList == null) return 0;
        return moviesList.length;
    }

    public void setMovieData(Movie[] movies) {
        moviesList = movies;
    }

    public Movie[] getMovieData() {
        return moviesList;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.iv_poster);
        }
    }
}
