package polina.example.com.movies.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.R;

import static android.R.attr.resource;

/**
 * Created by polina on 9/13/17.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder{
        ImageView image;
        TextView title;
        RatingBar rating;
        TextView genre;
        TextView description;
    }


    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, R.layout.movie_item_portrait, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate( R.layout.movie_item_portrait, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.movie_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.movie_title);
            viewHolder.rating = (RatingBar) convertView.findViewById(R.id.movie_ratingBar);
            viewHolder.genre = (TextView) convertView.findViewById(R.id.movie_genre);
            viewHolder.description = (TextView) convertView.findViewById(R.id.movie_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(movie.getTitle());
        viewHolder.description.setText(movie.getDescription());

        return convertView;
    }


}
