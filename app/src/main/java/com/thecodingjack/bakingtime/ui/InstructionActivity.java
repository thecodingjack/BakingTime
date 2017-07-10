package com.thecodingjack.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class InstructionActivity extends AppCompatActivity {
    private ArrayList<RecipeStep> recipeStepArrayList;
    private int stepIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        if (savedInstanceState == null) {
            recipeStepArrayList = getIntent().getParcelableArrayListExtra(StepsFragment.STEPS_LIST);
            stepIndex = getIntent().getIntExtra(StepsFragment.STEPS_INDEX, 0);


            InstructionFragment instructionFragment = new InstructionFragment();
            instructionFragment.setRecipeStepArrayList(recipeStepArrayList);
            instructionFragment.setStepIndex(stepIndex);
            instructionFragment.setTwoPane(false);
            instructionFragment.setRecipeStep(recipeStepArrayList.get(stepIndex));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.instruction_container, instructionFragment)
                    .commit();
        }
    }

}
