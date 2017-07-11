package com.thecodingjack.bakingtime.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.Recipe;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeIngredient;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class DetailsActivity extends AppCompatActivity implements StepAdapter.StepClickListener {
    private static final String TAG = "TESTDetailsActivity";
    public static final String PREF_RECIPE_NAME = "recipename";
    public static final String PREF_RECIPE_INGREDIENT = "recipeIngredient";
    public static final String RECIPE_KEY = "recipe_key";
    public static final String STEP_KEY = "step_key";
    private Recipe recipe;
    private boolean isTablet;
    private int scrollY;
    @BindView(R.id.detailScrollView)
    ScrollView mScrollView;
    private int stepIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(MainActivity.SELECTED_RECIPE);
        scrollY = 0;
        stepIndex = 0;
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_KEY)) {
                recipe = savedInstanceState.getParcelable(RECIPE_KEY);
            }
            if (savedInstanceState.containsKey(STEP_KEY)) {
                stepIndex = savedInstanceState.getInt(STEP_KEY);
            }
        }
        
        if (recipe != null) {
            ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
            ArrayList<RecipeStep> stepList = recipe.getSteps();
            FragmentManager fragmentManager = getSupportFragmentManager();
            isTablet = getResources().getBoolean(R.bool.isTablet);
            if (isTablet) {
                InstructionFragment instructionFragment = new InstructionFragment();
                instructionFragment.setRecipeStepArrayList(recipe.getSteps());
                instructionFragment.setStepIndex(stepIndex);
                instructionFragment.setRecipeStep(recipe.getSteps().get(stepIndex));
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
            stepsFragment.setTableStepClickListener(this);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(PREF_RECIPE_NAME, recipe.getName()).commit();
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_KEY, recipe);
        outState.putInt(STEP_KEY, stepIndex);

    }

    @Override
    public void onListItemClick(int stepPosition) {
        if (isTablet) {
            stepIndex = stepPosition;
            InstructionFragment instructionFragment = new InstructionFragment();
            instructionFragment.setRecipeStepArrayList(recipe.getSteps());
            instructionFragment.setStepIndex(stepPosition);
            instructionFragment.setRecipeStep(recipe.getSteps().get(stepPosition));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.instruction_container, instructionFragment)
                    .commit();
        }
    }
}
