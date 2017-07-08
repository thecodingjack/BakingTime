package com.thecodingjack.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class InstructionActivity extends AppCompatActivity {
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private RecipeStep recipeStep;
    private Uri recipeUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);


//        Intent intent = getIntent();
//        recipeStep = intent.getParcelableExtra(StepsFragment.STEP_KEY);
//        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
//        recipeUri = Uri.parse(recipeStep.getVideoURL());
//        if (recipeUri != null) {
//            initializePlayer(recipeUri);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        releasePlayer();
//    }
//
//    private void initializePlayer(Uri mediaUri) {
//        if (mExoPlayer == null) {
//
//            TrackSelector trackSelector = new DefaultTrackSelector();
//            LoadControl loadControl = new DefaultLoadControl();
//            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
//            mPlayerView.setPlayer(mExoPlayer);
//
//
//            mExoPlayer.addListener(this);
//
//
//            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
//            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
//                    this, userAgent), new DefaultExtractorsFactory(), null, null);
//            mExoPlayer.prepare(mediaSource);
//            mExoPlayer.setPlayWhenReady(true);
//        }
//    }
//
//    private void releasePlayer(){
//        mExoPlayer.stop();
//        mExoPlayer.release();
//        mExoPlayer = null;
//    }
//
//    @Override
//    public void onTimelineChanged(Timeline timeline, Object manifest) {
//
//    }
//
//    @Override
//    public void onPlayerError(ExoPlaybackException error) {
//
//    }
//
//    @Override
//    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//    }
//
//    @Override
//    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//        if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
//            Log.v("TEST", "Playing");
//        } else if (playbackState == ExoPlayer.STATE_READY) {
//            Log.v("TEST", "Paused");
//        }
//    }
//
//    @Override
//    public void onLoadingChanged(boolean isLoading) {
//
//    }
//
//    @Override
//    public void onPositionDiscontinuity() {
//
//    }
    }
}
