package polina.example.com.movies.Adapters;

import android.content.Context;
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
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;

/**
 * Created by polina on 9/13/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Movie> movieList;
    private static final int SHOW_POPULAR =1;
    private static final int SHOW_NORMAL = 2;

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
        RecyclerView.ViewHolder viewHolder = new ViewHolder1(parent);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case SHOW_NORMAL:
                View v1 = inflater.inflate(R.layout.movie_item, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case SHOW_POPULAR:
                View v2 = inflater.inflate(R.layout.movie_trailer_play, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
        }
        return viewHolder;
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
        viewHolder.title.setText(movie.getTitle());
        viewHolder.rating.setRating(movie.getRating()/2);
        String url = context.getResources().getString(R.string.image_uri) + movie.getImage_land();
        System.err.println("URL: " + url);
        Picasso.with(context).load(url).placeholder(R.drawable.placeholder)
                .error(R.drawable.icon_error_).into(viewHolder.image);
        viewHolder.genre.setText(getGenre(movie));
    }

    private String getGenre(Movie movie) {
        String genre = "";
        for(int i = 0; i<movie.getGenre().size(); i++){
            if(i==movie.getGenre().size()-1){
                genre = genre + Utils.getGenre(movie.getGenre().get(i));
            } else {
                genre = genre + Utils.getGenre(movie.getGenre().get(i)) + ", ";
            }
        }
        return genre;
    }

    private void configureViewHolder1(ViewHolder1 viewHolder, int position) {
        Movie movie = movieList.get(position);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.description.setText(movie.getDescription());
        viewHolder.rating.setRating(movie.getRating()/2);
        String url = context.getResources().getString(R.string.image_uri) +
                (Utils.isLandOrientation(context) ? movie.getImage_land() : movie.getImage_port());
        System.err.println("URL: " + url);
        Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.icon_error_).into(viewHolder.image);
        viewHolder.genre.setText(getGenre(movie));
    }


    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        RatingBar rating;
        TextView genre;
        TextView description;

        public ViewHolder1(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.movie_image);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            rating = (RatingBar) itemView.findViewById(R.id.movie_ratingBar);
            genre = (TextView) itemView.findViewById(R.id.movie_genre);
            description = (TextView) itemView.findViewById(R.id.movie_description);
        }
    }

        public static class ViewHolder2 extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;
            RatingBar rating;
            TextView genre;
            TextView description;

            public ViewHolder2(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.movie_image_play);
                title = (TextView) itemView.findViewById(R.id.movie_title_play);
                rating = (RatingBar) itemView.findViewById(R.id.movie_ratingBar_play);
                genre = (TextView) itemView.findViewById(R.id.movie_genre_play);
            }



        }




}
