package polina.example.com.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.Network.NetworkConnection;
import polina.example.com.movies.Network.OnMoviesTrailerListener;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;

public class MovieActivity extends AppCompatActivity implements OnMoviesTrailerListener {
    NetworkConnection networkConnection;
    Movie movie;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.movie_rat) RatingBar rating;
    @BindView(R.id.movie_gen) TextView gen;
    @BindView(R.id.movie_desc) TextView desc;
    @BindView(R.id.header) ImageView image;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        networkConnection = new NetworkConnection(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movie = getIntent().getParcelableExtra(Utils.MOVIE);
        collapsingToolbar.setTitle(movie.getTitle());
        rating.setRating(movie.getRating());
        gen.setText(Utils.getGenre(movie));
        desc.setText(movie.getDescription());
        String url = getString(R.string.image_uri) + movie.getImage_land();
        Picasso.with(this).load(url).transform(new RoundedCornersTransformation(10, 0))
                .placeholder(R.drawable.placeholder).error(R.drawable.icon_error_)
                .into(image);
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

    @OnClick(R.id.load_video_btn)
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
