package polina.example.com.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.Network.NetworkConnection;
import polina.example.com.movies.Network.OnMoviesTrailerListener;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;

public class MovieActivity extends AppCompatActivity implements OnMoviesTrailerListener {
    NetworkConnection networkConnection;
    Movie movie;
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        networkConnection = new NetworkConnection(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        movie = getIntent().getParcelableExtra(Utils.MOVIE);
        collapsingToolbar.setTitle(movie.getTitle());
        ((RatingBar)findViewById(R.id.movie_rat)).setRating(movie.getRating()/2);
        ((TextView)findViewById(R.id.movie_gen)).setText(Utils.getGenre(movie));
        ((TextView)findViewById(R.id.movie_desc)).setText(movie.getDescription());
        String url = getString(R.string.image_uri) + movie.getImage_land();
        Picasso.with(this).load(url).placeholder(R.drawable.placeholder).error(R.drawable.icon_error_).into((ImageView) findViewById(R.id.header));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onLoadVideo(View v){
       networkConnection.getMovieTrailer(this, movie.getId());
    }

    @Override
    public void onTrailerLoad(String youtubeId) {
        Intent intent = new Intent(MovieActivity.this, YouTubeActivity.class);
        intent.putExtra(Utils.MOVIE, youtubeId);
        startActivity(intent);
    }
}
