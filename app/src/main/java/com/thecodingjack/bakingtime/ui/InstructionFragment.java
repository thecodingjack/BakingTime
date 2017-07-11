package com.thecodingjack.bakingtime.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class InstructionFragment extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = "TESTInstructionFragment";

    @BindView(R.id.player_view)SimpleExoPlayerView mPlayerView;
    @BindView(R.id.instruction_view)TextView mInstructionView;
    @BindView(R.id.previousButton) Button previousButton;
    @BindView(R.id.nextButton) Button nextButton;
    private SimpleExoPlayer mExoPlayer;
    private RecipeStep recipeStep;
    private Uri recipeUri;

    private ArrayList<RecipeStep> recipeStepArrayList;
    private int stepIndex;
    private Toast mToast;

    public InstructionFragment() {
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public void setRecipeStep(RecipeStep recipeStep) {
        this.recipeStep = recipeStep;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.thecodingjack.bakingtime.R.layout.fragment_instruction, container, false);
        ButterKnife.bind(this, rootView);


        if (savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt(StepsFragment.STEPS_INDEX, 0);
            recipeStep = recipeStepArrayList.get(stepIndex);
        }

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
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
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
        if (mExoPlayer == null) return;
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
        } else if (playbackState == ExoPlayer.STATE_READY) {
        }
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(StepsFragment.STEPS_INDEX, stepIndex);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}