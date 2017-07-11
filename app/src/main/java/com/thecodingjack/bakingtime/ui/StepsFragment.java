package com.thecodingjack.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class StepsFragment extends Fragment implements StepAdapter.StepClickListener{
    public static final String STEPS_LIST = "steps";
    public static final String STEPS_INDEX = "stepsID";
    public static final String SCROLL_POSITION = "scroll_key";
    private ArrayList<RecipeStep> recipeStepArrayList;
    private RecyclerView stepsRecyclerView;
    private StepAdapter stepAdapter;
    private StepAdapter.StepClickListener tableStepClickListener;
    private int scrollY;

    public void setTableStepClickListener(StepAdapter.StepClickListener listener) {
        tableStepClickListener = listener;
    }

    public StepsFragment() {
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scrollY=0;
        if (savedInstanceState != null) {
            recipeStepArrayList = savedInstanceState.getParcelableArrayList(STEPS_LIST);
            if (savedInstanceState.containsKey(SCROLL_POSITION)) {
                scrollY = savedInstanceState.getInt(SCROLL_POSITION);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_rv);
        stepAdapter = new StepAdapter(recipeStepArrayList, this,tableStepClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(stepAdapter);

        stepsRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                stepsRecyclerView.scrollTo(0, scrollY);
            }
        });
        return rootView;
    }

    @Override
    public void onListItemClick(int stepPosition) {
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (!tabletSize) {
            Intent stepsIntent = new Intent(getContext(), InstructionActivity.class);
            stepsIntent.putParcelableArrayListExtra(STEPS_LIST, recipeStepArrayList);
            stepsIntent.putExtra(STEPS_INDEX, stepPosition);
            startActivity(stepsIntent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SCROLL_POSITION, stepsRecyclerView.getScrollY());
        outState.putParcelableArrayList(STEPS_LIST, recipeStepArrayList);
    }
}
