package com.thecodingjack.bakingtime.ui.recipePOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class Recipe implements Parcelable {
    private int recipeID;
    private String name;
    private ArrayList<RecipeIngredient> ingredients;
    private ArrayList<RecipeStep> steps;
    private String imageURL;

    public Recipe(int recipeID, String name, ArrayList<RecipeIngredient> ingredients, ArrayList<RecipeStep> steps, String imageURL) {
        this.recipeID = recipeID;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imageURL=imageURL;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.recipeID);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeString(this.imageURL);
    }

    protected Recipe(Parcel in) {
        this.recipeID = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<RecipeIngredient>();
        in.readList(this.ingredients, RecipeIngredient.class.getClassLoader());
        this.steps = new ArrayList<RecipeStep>();
        in.readList(this.steps, RecipeStep.class.getClassLoader());
        this.imageURL = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
