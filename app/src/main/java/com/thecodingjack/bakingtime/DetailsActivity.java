package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(MainActivity.SELECTED_RECIPE);
        ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
        ArrayList<RecipeStep> stepList = recipe.getSteps();


        if(savedInstanceState == null){
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientList(ingredientList);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container,ingredientsFragment)
                    .commit();

            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setRecipeStepArrayList(stepList);
            stepsFragment.setContext(this);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container,stepsFragment)
                    .commit();
        }
    }
}
