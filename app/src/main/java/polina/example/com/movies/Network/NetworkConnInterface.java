package polina.example.com.movies.Network;

/**
 * Created by polina on 9/14/17.
 */

public interface NetworkConnInterface {
    public abstract void getListOfMovies (OnListOfMoviesLoadListenter callback);
    public abstract void getMovieTrailer (OnMoviesTrailerListener callback, int movieId);

}
