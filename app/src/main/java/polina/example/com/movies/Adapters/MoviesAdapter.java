package polina.example.com.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import polina.example.com.movies.Activities.MovieActivity;
import polina.example.com.movies.Activities.YouTubeActivity;
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.Network.NetworkConnection;
import polina.example.com.movies.Network.OnMoviesTrailerListener;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;


/**
 * Created by polina on 9/13/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public  Context context;
    List<Movie> movieList;
    private static final int SHOW_POPULAR =1;
    private static final int SHOW_NORMAL = 2;
    private int currentPosition = 0;

    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super();
        this.context = context;
        movieList = objects;
    }



    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movieList.get(position).getRating()>=2.5){
            return SHOW_POPULAR;
        }
         return SHOW_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case SHOW_NORMAL:
                View v1 = inflater.inflate(R.layout.movie_item, parent, false);
                RecyclerView.ViewHolder viewHolder1 = new NormalViewHolder(v1);
                return viewHolder1;
            case SHOW_POPULAR:
                View v2 = inflater.inflate(R.layout.movie_trailer_play, parent, false);
                RecyclerView.ViewHolder viewHolder2 = new PopularViewHolder(v2);
                return viewHolder2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case SHOW_NORMAL:
                NormalViewHolder vh1 = (NormalViewHolder) holder;
                configureNormalViewHolder(vh1, position);
                break;
            case SHOW_POPULAR:
                PopularViewHolder vh2 = (PopularViewHolder) holder;
                configurePopularViewHolder(vh2, position);
                break;

        }
    }

    private void configurePopularViewHolder(PopularViewHolder viewHolder, int position) {
        Movie movie = movieList.get(position);
        viewHolder.setMovie(movie);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.rating.setRating(movie.getRating());
        String url = context.getResources().getString(R.string.image_uri) + movie.getImage_land();
        System.err.println("URL: " + url);
        Picasso.with(context).load(url).transform(new RoundedCornersTransformation(10, 0)).placeholder(R.drawable.placeholder)
                .error(R.drawable.icon_error_).into(viewHolder.image);
        viewHolder.genre.setText(Utils.getGenre(movie));
    }



    private void configureNormalViewHolder(NormalViewHolder viewHolder, int position) {
        Movie movie = movieList.get(position);
        viewHolder.setMovie(movie);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.description.setText(movie.getDescription());
        viewHolder.rating.setRating(movie.getRating());
        String url = context.getResources().getString(R.string.image_uri) +
                (Utils.isLandOrientation(context) ? movie.getImage_land() : movie.getImage_port());
        System.err.println("URL: " + url);
        Picasso.with(context).load(url)
                .transform(new RoundedCornersTransformation(10, 0))
                .placeholder(R.drawable.placeholder).error(R.drawable.icon_error_).into(viewHolder.image);
        viewHolder.genre.setText(Utils.getGenre(movie));
    }


    public class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_image) ImageView image;
        @BindView(R.id.movie_title) TextView title;
        @BindView(R.id.movie_ratingBar) RatingBar rating;
        @BindView(R.id.movie_genre) TextView genre;
        @BindView(R.id.movie_description) TextView description;
        Movie movie;

        void setMovie(Movie movie) {
            this.movie = movie;
        }

        NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.movie_item_layout)
        void onClick() {
            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra(Utils.MOVIE, movie);
            context.startActivity(intent);
        }
    }

        public  class PopularViewHolder extends RecyclerView.ViewHolder implements OnMoviesTrailerListener{
            @BindView(R.id.movie_image_play) ImageView image;
            @BindView(R.id.movie_title_play) TextView title;
            @BindView(R.id.movie_ratingBar_play) RatingBar rating;
            @BindView(R.id.movie_genre_play) TextView genre;
            Movie movie;

            public void setMovie(Movie movie) {
                this.movie = movie;
            }

            public PopularViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.imageButtonTrailerPlay)
            public void onGetMovieTrailer() {
                NetworkConnection networkConnection = new NetworkConnection(context);
                networkConnection.getMovieTrailer(this, movie.getId());
            }

            @OnClick(R.id.movie_play_item)
            void onOpenMovie() {
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra(Utils.MOVIE, movie);
                context.startActivity(intent);
            }

            @Override
            public void onTrailerLoad(String youtubeId) {
                Intent intent = new Intent(context, YouTubeActivity.class);
                intent.putExtra(Utils.MOVIE, youtubeId);
                context.startActivity(intent);
            }
        }


}
