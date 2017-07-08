package com.thecodingjack.bakingtime;

import android.content.Context;
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
    private ArrayList<RecipeStep> recipeStepArrayList;
    private LinearLayout linearLayout;
    private Context mContext;


    public StepsFragment() {
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
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

            View newView = LayoutInflater.from(mContext).inflate(R.layout.fragment_steps,linearLayout,false);
            TextView textView = (TextView) newView.findViewById(R.id.steps_shortDescription);
            linearLayout.addView(newView);

            final String shortDescription = recipeStepArrayList.get(i).getShortDescription();

            textView.setText(shortDescription);

            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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
