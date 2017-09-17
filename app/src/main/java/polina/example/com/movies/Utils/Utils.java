package polina.example.com.movies.Utils;

import android.content.Context;
import android.content.res.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import polina.example.com.movies.Models.Movie;

/**
 * Created by polina on 9/14/17.
 */

public class Utils {

    public static final String RESULTS ="results";
    public static final String TITLE ="title";
    public static final String DESCRIPTION ="overview";
    public static final String POSTER ="poster_path";
    public static final String BACKDROP ="backdrop_path";
    public static final String ID ="id";
    public static final String GENRE_ARRAY="genre_ids";
    public static final String RATING="vote_average";
    public static final String MOVIE="movie";
    public static final String MOVIE_TRAILER="key";



    private static final Map<Integer, String> GENRE = new HashMap(){{
        put(28, "Action");
        put(12, "Adventure");
        put(16, "Animation");
        put(35, "Comedy");
        put(80, "Crime");
        put(99, "Documentary");
        put(18, "Drama");
        put(10751, "Family");
        put(14, "Fantasy");
        put(36, "History");
        put(27, "Horror");
        put(10402, "Music");
        put(9648, "Mystery");
        put(10749, "Romance");
        put(878, "Science Fiction");
        put(10770, "TV Movie");
        put(53, "Thriller");
        put(10752, "War");
        put(37, "Western");
    }};

    public static String getGenre(int id){
        return GENRE.get(id);
    }

    public static boolean isLandOrientation(Context context){
       int orientation =  context.getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_LANDSCAPE) return true;
        if(orientation==Configuration.ORIENTATION_PORTRAIT) return false;
        return false;
    }

    public static String getGenre(Movie movie) {
        String genre = "";
        for(int i = 0; i<movie.getGenre().size(); i++){
            if(i==movie.getGenre().size()-1){
                genre = genre + getGenre(movie.getGenre().get(i));
            } else {
                genre = genre + getGenre(movie.getGenre().get(i)) + ", ";
            }
        }
        return genre;
    }


    public static List<Movie> parseJson(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray(RESULTS);
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            Movie movie = new Movie();
            movie.setTitle(object.optString(TITLE));
            movie.setDescription(object.optString(DESCRIPTION));
            movie.setImage_port(object.optString(POSTER));
            movie.setImage_land(object.optString(BACKDROP));
            movie.setId(object.optInt(ID));
            JSONArray genreArray = object.getJSONArray(GENRE_ARRAY);
            ArrayList<Integer> g = new ArrayList<>();
            for(int j = 0; j<genreArray.length(); j++){
                g.add(genreArray.getInt(j));
            }
            movie.setGenre(g);
            movie.setRating((float) object.optDouble(RATING));
            movies.add(movie);
        }
        return movies;

    }

    public static String parseVideoJson(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray(RESULTS);
        JSONObject object = jsonArray.optJSONObject(0);
        return object.optString(MOVIE_TRAILER);
    }
}
