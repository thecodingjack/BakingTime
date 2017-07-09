package com.thecodingjack.bakingtime.ui;

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

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class InstructionFragment extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = "TESTInstructionFragment";
    private SimpleExoPlayerView mPlayerView;
    private TextView mInstructionView;
    private SimpleExoPlayer mExoPlayer;
    private RecipeStep recipeStep;
    private Uri recipeUri;
    private Button nextButton, previousButton;
    private ArrayList<RecipeStep> recipeStepArrayList;
    private int stepIndex;
    private Toast mToast;
    private boolean isTwoPane;

    public InstructionFragment() {
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public void setTwoPane(boolean isTwoPane) {
        this.isTwoPane = isTwoPane;
    }

    public void setRecipeStep(RecipeStep recipeStep) {
        this.recipeStep = recipeStep;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.thecodingjack.bakingtime.R.layout.fragment_instruction, container, false);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
        mInstructionView = (TextView) rootView.findViewById(R.id.instruction_view);
        nextButton = (Button) rootView.findViewById(R.id.nextButton);
        previousButton = (Button) rootView.findViewById(R.id.previousButton);
        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(StepsFragment.STEPS_LIST) && intent.hasExtra(StepsFragment.STEPS_INDEX)) {
            recipeStepArrayList = intent.getParcelableArrayListExtra(StepsFragment.STEPS_LIST);
            stepIndex = intent.getIntExtra(StepsFragment.STEPS_INDEX, 0);
            recipeStep = recipeStepArrayList.get(stepIndex);
        }

        if (savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt(StepsFragment.STEPS_INDEX, 0);
            recipeStep = recipeStepArrayList.get(stepIndex);
            Log.v(TAG, "onCreateView Saved step: " + stepIndex);
        } else {
            Log.v(TAG, "onCreateView savedInstance is empty");
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
        if (mExoPlayer == null) {
            return;
        }
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

    /* TODO 1) Issue - instructionSavedInstance becomes null onCreate
    if savedInstance is empty, this fragment is loaded with the step passed from details activity
    I saved the step on saveInstance but onCreate it becomes null...
07-09 10:56:25.392 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onSaveInstance saved step:8
07-09 10:56:25.463 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onDestroy saved step:8
07-09 10:56:25.517 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onCreate saved step: null
07-09 10:56:25.525 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onCreateView savedInstance is empty
07-09 10:56:25.535 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onSaveInstance saved step:6
07-09 10:56:25.537 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onActivityCreated saved step:6
07-09 10:56:25.537 14828-14828/com.thecodingjack.bakingtime V/TESTInstructionFragment: onSaveInstance saved step:6
     */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(StepsFragment.STEPS_INDEX, stepIndex);
        Log.v(TAG, "onSaveInstance saved step:" + stepIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy saved step:" + stepIndex);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated saved step:" + stepIndex);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, "onSaveInstance saved step:" + stepIndex);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v(TAG, "onSaveInstance saved step:" + stepIndex);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Log.v(TAG, "onCreate saved step: null");
        } else {
            stepIndex = savedInstanceState.getInt(StepsFragment.STEPS_INDEX, 0);
            Log.v(TAG, "onCreate saved step:" + stepIndex);
        }
    }
}


