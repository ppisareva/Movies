package polina.example.com.movies.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import polina.example.com.movies.BuildConfig;
import polina.example.com.movies.R;
import polina.example.com.movies.Utils.Utils;

public class YouTubeActivity extends YouTubeBaseActivity {
    String youtubeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        youtubeID = getIntent().getStringExtra(Utils.MOVIE);
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);
        youTubePlayerView.initialize(BuildConfig.API_KEY_GOOGLE,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(youtubeID);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
