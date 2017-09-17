package polina.example.com.movies.Network;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.LangUtils;
import polina.example.com.movies.BuildConfig;
import polina.example.com.movies.Models.Movie;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static polina.example.com.movies.Utils.Utils.parseJson;

/**
 * Created by polina on 9/14/17.
 */

public class NetworkConnection implements NetworkConnInterface {
    Context context;
    public static final String BASIC_URI = "https://api.themoviedb.org/3/";
    public static final String NOW_PLAYING_MOVIES = "movie/now_playing";
    public static final String MOVIE_VIDEOS = "/movie/{movie_id}/videos";
    public static final String API_KAY = "api_key";
    public static final String API_KAY_VALUE= BuildConfig.API_KEY;
    RequestParams params;


    AsyncHttpClient client = new AsyncHttpClient();

    public NetworkConnection(Context context) {
        this.context = context;
        params = new RequestParams();
        params.put(API_KAY, API_KAY_VALUE);

    }

    @Override
    public void getListOfMovies(final OnListOfMoviesLoadListenter listenter) {

        Log.d(API_KAY, API_KAY_VALUE);

       client.get((BASIC_URI+NOW_PLAYING_MOVIES), params, new JsonHttpResponseHandler() {

           @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
               Log.d(" server response", response.toString());
                try {
                    listenter.onMoviesLoad(Utils.parseJson(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

           @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
               Toast.makeText(context, context.getResources().getString(R.string.faild_to_connect), Toast.LENGTH_LONG);
            }


        });
    }

    @Override
    public void getMovieTrailer(final OnMoviesTrailerListener l, int movieId) {

        String url = (BASIC_URI+"movie/"+movieId+"/videos");
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(" server response", response.toString());
                try {
                    l.onTrailerLoad(Utils.parseVideoJson(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
               Log.d("failure  ", statusCode+"");
            }


        });
    }

}
