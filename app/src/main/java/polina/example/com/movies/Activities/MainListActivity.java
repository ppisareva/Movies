package polina.example.com.movies.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import polina.example.com.movies.Adapters.MoviesAdapter;
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.Network.NetworkConnection;
import polina.example.com.movies.Network.OnListOfMoviesLoadListenter;
import polina.example.com.movies.R;

public class MainListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        NetworkConnection networkConnection = new NetworkConnection(MainListActivity.this);
        ListView listView = (ListView) findViewById(R.id.list_movies);
        final MoviesAdapter adapter = new MoviesAdapter(this, new ArrayList<Movie>());
        listView.setAdapter(adapter);
        networkConnection.getListOfMovies(new OnListOfMoviesLoadListenter() {
           @Override
           public void onMoviesLoad(List<Movie> movies) {
               adapter.addAll(movies);
               adapter.notifyDataSetChanged();
           }
       });
    }
}
