package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class StepsFragment extends Fragment implements StepAdapter.StepClickListener {
    public static final String STEPS_LIST = "steps";
    public static final String STEPS_INDEX = "stepsID";
    private ArrayList<RecipeStep> recipeStepArrayList;
    private RecyclerView stepsRecyclerView;
    private StepAdapter stepAdapter;
    private boolean isTwoPane;
//    private TabletStepClickListener mCallback;


    public StepsFragment() {
    }

    public void setTwoPane(boolean isTwoPane) {
        this.isTwoPane = isTwoPane;
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

//    public interface TabletStepClickListener{
//        void onStepSelected(int position);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mCallback = (TabletStepClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement TabletStepClickListener");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipeStepArrayList = savedInstanceState.getParcelableArrayList(STEPS_LIST);
        }
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_rv);
        stepAdapter = new StepAdapter(recipeStepArrayList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(stepAdapter);
        return rootView;
    }

    @Override
    public void onListItemClick(int stepPosition) {
        if (isTwoPane) {
            Toast.makeText(getActivity(), recipeStepArrayList.get(stepPosition).getShortDescription(), Toast.LENGTH_SHORT).show();
        } else {
            Intent stepsIntent = new Intent(getContext(), InstructionActivity.class);
            stepsIntent.putParcelableArrayListExtra(STEPS_LIST, recipeStepArrayList);
            stepsIntent.putExtra(STEPS_INDEX, stepPosition);
            startActivity(stepsIntent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEPS_LIST, recipeStepArrayList);
    }
}
