package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class InstructionFragment extends Fragment implements ExoPlayer.EventListener {
    private SimpleExoPlayerView mPlayerView;
    private TextView mInstructionView;
    private SimpleExoPlayer mExoPlayer;
    private RecipeStep recipeStep;
    private Uri recipeUri;
    private Button nextButton, previousButton;
    private ArrayList<RecipeStep> recipeStepArrayList;
    private int stepIndex;
    private Toast mToast;

    public InstructionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
        mInstructionView = (TextView) rootView.findViewById(R.id.instruction_view);
        nextButton = (Button) rootView.findViewById(R.id.nextButton);
        previousButton = (Button) rootView.findViewById(R.id.previousButton);

        Intent intent = getActivity().getIntent();
        recipeStep = intent.getParcelableExtra(StepsFragment.STEP_KEY);
        recipeStepArrayList = intent.getParcelableArrayListExtra(StepsFragment.STEPS_LIST);
        stepIndex = intent.getIntExtra(StepsFragment.STEPS_INDEX, 0);
        recipeUri = Uri.parse(recipeStep.getVideoURL());
        if (recipeUri != null) {
            initializePlayer(recipeUri);
        }
        mInstructionView.setText(recipeStep.getFullDescription());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepIndex < recipeStepArrayList.size() - 1) {
                    stepIndex++;
                    recipeStep = recipeStepArrayList.get(stepIndex);
                    recipeUri = Uri.parse(recipeStep.getVideoURL());
                    if (recipeUri != null) {
                        releasePlayer();
                        initializePlayer(recipeUri);
                    }
                    mInstructionView.setText(recipeStep.getFullDescription());
                } else {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getActivity(), "Reached end of instruction", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepIndex > 0) {
                    stepIndex--;
                    recipeStep = recipeStepArrayList.get(stepIndex);
                    recipeUri = Uri.parse(recipeStep.getVideoURL());
                    if (recipeUri != null) {
                        releasePlayer();
                        initializePlayer(recipeUri);
                    }
                    mInstructionView.setText(recipeStep.getFullDescription());
                } else {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getActivity(), "This is the first step", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);


            mExoPlayer.addListener(this);


            String userAgent = Util.getUserAgent(getActivity(), "BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            Log.v("TEST", "Playing");
        } else if (playbackState == ExoPlayer.STATE_READY) {
            Log.v("TEST", "Paused");
        }
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}


