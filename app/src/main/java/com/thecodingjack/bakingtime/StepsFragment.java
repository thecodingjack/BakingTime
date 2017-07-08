package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class StepsFragment extends Fragment {
    public static final String STEPS_LIST = "steps";
    public static final String STEP_KEY = "step_key";
    private ArrayList<RecipeStep> recipeStepArrayList;
    private LinearLayout linearLayout;


    public StepsFragment() {
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipeStepArrayList = savedInstanceState.getParcelableArrayList(STEPS_LIST);
        }
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.steps_linear_layout);
        for (int i = 0; i < recipeStepArrayList.size(); i++) {

            View newView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_steps,linearLayout,false);
            TextView textView = (TextView) newView.findViewById(R.id.steps_shortDescription);
            linearLayout.addView(newView);

            final String shortDescription = recipeStepArrayList.get(i).getShortDescription();
            final RecipeStep recipeStep = recipeStepArrayList.get(i);

            textView.setText(shortDescription);

            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent stepsIntent = new Intent(getContext(),StepsActivity.class);
                    stepsIntent.putExtra(STEP_KEY, recipeStep);
                    startActivity(stepsIntent);

                }
            });
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEPS_LIST, recipeStepArrayList);

    }
}
