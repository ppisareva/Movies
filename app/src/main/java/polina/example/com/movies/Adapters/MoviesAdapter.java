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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import polina.example.com.movies.Activities.MovieActivity;
import polina.example.com.movies.Activities.YouTubeActivity;
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.Network.NetworkConnection;
import polina.example.com.movies.Network.OnMoviesTrailerListener;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;

import static android.R.id.candidatesArea;
import static android.R.id.list;
import static android.R.id.redo;
import static android.R.id.switch_widget;
import static java.lang.System.load;
import static polina.example.com.movies.Utils.Utils.getGenre;

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
        if(movieList.get(position).getRating()>=5){
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
                RecyclerView.ViewHolder viewHolder1 = new ViewHolder1(v1);
                return viewHolder1;
            case SHOW_POPULAR:
                View v2 = inflater.inflate(R.layout.movie_trailer_play, parent, false);
                RecyclerView.ViewHolder viewHolder2 = new ViewHolder2(v2);
                return viewHolder2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case SHOW_NORMAL:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                break;
            case SHOW_POPULAR:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;

        }
    }

    private void configureViewHolder2(ViewHolder2 viewHolder, int position) {
        Movie movie = movieList.get(position);
        viewHolder.setMovie(movie);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.rating.setRating(movie.getRating()/2);
        String url = context.getResources().getString(R.string.image_uri) + movie.getImage_land();
        System.err.println("URL: " + url);
        Picasso.with(context).load(url).transform(new RoundedCornersTransformation(10, 0)).placeholder(R.drawable.placeholder)
                .error(R.drawable.icon_error_).into(viewHolder.image);
        viewHolder.genre.setText(Utils.getGenre(movie));
    }



    private void configureViewHolder1(ViewHolder1 viewHolder, int position) {
        Movie movie = movieList.get(position);
        viewHolder.setMovie(movie);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.description.setText(movie.getDescription());
        viewHolder.rating.setRating(movie.getRating()/2);
        String url = context.getResources().getString(R.string.image_uri) +
                (Utils.isLandOrientation(context) ? movie.getImage_land() : movie.getImage_port());
        System.err.println("URL: " + url);
        Picasso.with(context).load(url)
                .transform(new RoundedCornersTransformation(10, 0))
                .placeholder(R.drawable.placeholder).error(R.drawable.icon_error_).into(viewHolder.image);
        viewHolder.genre.setText(Utils.getGenre(movie));
    }


    public class ViewHolder1 extends RecyclerView.ViewHolder  implements View.OnClickListener{
        ImageView image;
        TextView title;
        RatingBar rating;
        TextView genre;
        TextView description;
        Movie movie;
        RelativeLayout layout;

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public ViewHolder1(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.movie_item_layout);
            layout.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.movie_image);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            rating = (RatingBar) itemView.findViewById(R.id.movie_ratingBar);
            genre = (TextView) itemView.findViewById(R.id.movie_genre);
            description = (TextView) itemView.findViewById(R.id.movie_description);

        }

        @Override
        public void onClick(View view) {
                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra(Utils.MOVIE, movie);
                    context.startActivity(intent);



        }


    }

        public  class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener,  OnMoviesTrailerListener{
            ImageView image;
            TextView title;
            RatingBar rating;
            TextView genre;
            Movie movie;
            RelativeLayout layout;

            public Movie getMovie() {
                return movie;
            }

            public void setMovie(Movie movie) {
                this.movie = movie;
            }

            public ViewHolder2(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.movie_image_play);
                title = (TextView) itemView.findViewById(R.id.movie_title_play);
                rating = (RatingBar) itemView.findViewById(R.id.movie_ratingBar_play);
                genre = (TextView) itemView.findViewById(R.id.movie_genre_play);
                itemView.setOnClickListener(this);
                itemView.findViewById(R.id.imageButtonTrailerPlay).setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.imageButtonTrailerPlay:
                        NetworkConnection networkConnection = new NetworkConnection(context);
                        networkConnection.getMovieTrailer(this, movie.getId());
                        break;
                    case R.id.movie_play_item:
                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra(Utils.MOVIE, movie);
                        context.startActivity(intent);
                        break;


                }
            }

            @Override
            public void onTrailerLoad(String youtubeId) {
                Intent intent = new Intent(context, YouTubeActivity.class);
                intent.putExtra(Utils.MOVIE, youtubeId);
                context.startActivity(intent);
            }
        }


}
