package polina.example.com.movies.Network;

import java.util.List;

import polina.example.com.movies.Models.Movie;

/**
 * Created by polina on 9/14/17.
 */

public interface NetworkConnInterface {
    public abstract void getListOfMovies (OnListOfMoviesLoadListenter callback);
    public abstract Movie getMovie (int id);

}
