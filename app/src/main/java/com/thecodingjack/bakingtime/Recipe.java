package com.thecodingjack.bakingtime;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class Recipe {
    private int recipeID;
    private String name;
    private ArrayList<RecipeIngredient> ingredients;
    private ArrayList<RecipeStep> steps;

    public Recipe(int recipeID, String name, ArrayList<RecipeIngredient> ingredients, ArrayList<RecipeStep> steps) {
        this.recipeID = recipeID;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }
}
