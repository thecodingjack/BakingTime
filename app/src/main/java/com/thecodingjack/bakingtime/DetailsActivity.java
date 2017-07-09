package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    public static final String RECIPE_KEY = "recipe_key";
    public static final String SCROLL_POSITION = "scroll_key";
    private Recipe recipe;
    private ScrollView mScrollView;
    private int scrollY;
    private boolean isTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mScrollView = (ScrollView) findViewById(R.id.detailScrollView);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(MainActivity.SELECTED_RECIPE);
        scrollY = 0;
        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPE_KEY)) {
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
            if (savedInstanceState.containsKey(SCROLL_POSITION)) {
                scrollY = savedInstanceState.getInt(SCROLL_POSITION);
            }
        }

        if (recipe != null) {
            ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
            ArrayList<RecipeStep> stepList = recipe.getSteps();
            FragmentManager fragmentManager = getSupportFragmentManager();

            if(findViewById(R.id.instruction_container)!=null){
                isTwoPane = true;

                InstructionFragment instructionFragment = new InstructionFragment();
                instructionFragment.setRecipeStepArrayList(recipe.getSteps());
                instructionFragment.setStepIndex(0);
                instructionFragment.setTwoPane(isTwoPane);
                instructionFragment.setRecipeStep(recipe.getSteps().get(0));
                fragmentManager.beginTransaction()
                        .add(R.id.instruction_container,instructionFragment)
                        .commit();
            }else{
                isTwoPane = false;
            }

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientList(ingredientList);
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientsFragment)
                    .commit();

            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setTwoPane(isTwoPane);
            stepsFragment.setRecipeStepArrayList(stepList);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();

        }



        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.smoothScrollTo(0, scrollY);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_KEY, recipe);
        outState.putInt(SCROLL_POSITION, mScrollView.getScrollY());
    }

    void updateInstruction(int stepIndex){
        if(isTwoPane){
            InstructionFragment newFragment = new InstructionFragment();
            newFragment.setRecipeStepArrayList(recipe.getSteps());
            newFragment.setStepIndex(stepIndex);
            newFragment.setTwoPane(isTwoPane);
            newFragment.setRecipeStep(recipe.getSteps().get(stepIndex));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.instruction_container,newFragment)
                    .commit();

        }
    }
}
