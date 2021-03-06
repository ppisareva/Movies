package polina.example.com.movies.Activities;

import android.content.Context;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import polina.example.com.movies.Adapters.MoviesAdapter;
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.Network.NetworkConnection;
import polina.example.com.movies.Network.OnListOfMoviesLoadListenter;
import polina.example.com.movies.R;

public class MainListActivity extends AppCompatActivity  implements OnListOfMoviesLoadListenter{
    final List<Movie> moviesList = new ArrayList<>();
    @BindView(R.id.list_movies) RecyclerView recyclerViewListOfMovies;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        NetworkConnection networkConnection = new NetworkConnection(MainListActivity.this);
        networkConnection.getListOfMovies(this);
        recyclerViewListOfMovies.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewListOfMovies.setLayoutManager(layoutManager);
        adapter = new MoviesAdapter(this, moviesList);
        recyclerViewListOfMovies.setAdapter(adapter);

    }

    @Override
    public void onMoviesLoad(List<Movie> movies) {
        Log.d("Array from server", movies.toString());
        System.err.println("!!!!!!!!!!!!!!!!!! " + (Looper.getMainLooper().getThread() == Thread.currentThread()));
        moviesList.clear();
        moviesList.addAll(movies);
        adapter.notifyDataSetChanged();
    }
}
