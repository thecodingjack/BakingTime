package com.thecodingjack.bakingtime.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;

import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.Recipe;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeIngredient;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class DetailsActivity extends AppCompatActivity{
    private static final String TAG = "TESTDetailsActivity";
    public static final String PREF_RECIPE_NAME = "recipename";
    public static final String PREF_RECIPE_INGREDIENT = "recipeIngredient";
    public static final String RECIPE_KEY = "recipe_key";
    public static final String SCROLL_POSITION = "scroll_key";
    private Recipe recipe;
    private ScrollView mScrollView;
    private int scrollY;

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
            boolean isTablet = getResources().getBoolean(R.bool.isTablet);
            if (isTablet){

                InstructionFragment instructionFragment = new InstructionFragment();
                instructionFragment.setRecipeStepArrayList(recipe.getSteps());
                instructionFragment.setStepIndex(0);
                instructionFragment.setRecipeStep(recipe.getSteps().get(0));
                fragmentManager.beginTransaction()
                        .add(R.id.instruction_container, instructionFragment)
                        .commit();
            }

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientList(ingredientList);
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientsFragment)
                    .commit();

            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setRecipeStepArrayList(stepList);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(PREF_RECIPE_NAME, recipe.getName()).commit();
        Log.v(TAG, sharedPreferences.getString(PREF_RECIPE_NAME, "nothing"));
        StringBuilder sb = new StringBuilder();
        ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
        for (int i = 0; i < ingredientList.size(); i++) {
            RecipeIngredient currentItem = ingredientList.get(i);
            double quantity = currentItem.getQuantity();
            String measure = currentItem.getMeasure();
            String name = currentItem.getIngredientName();
            String ItemDetails = quantity + " " + measure + " " + name;
            sb.append(ItemDetails + "\n");
        }
        String recipeIngredient = sb.toString();
        sharedPreferences.edit().putString(PREF_RECIPE_INGREDIENT, recipeIngredient).commit();
        Log.v(TAG, sharedPreferences.getString(PREF_RECIPE_INGREDIENT, "nothing"));

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
}
