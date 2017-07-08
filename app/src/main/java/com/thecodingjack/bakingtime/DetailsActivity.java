package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    public static final String RECIPE_KEY = "recipe_key";
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(MainActivity.SELECTED_RECIPE);

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPE_KEY)) {
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
        }

        if (recipe != null) {
            ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
            ArrayList<RecipeStep> stepList = recipe.getSteps();


            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientList(ingredientList);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientsFragment)
                    .commit();

            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setRecipeStepArrayList(stepList);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_KEY, recipe);
        Log.v("TEST", recipe.getName());
    }
}
