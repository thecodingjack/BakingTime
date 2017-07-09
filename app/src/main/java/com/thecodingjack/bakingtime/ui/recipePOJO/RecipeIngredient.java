package com.thecodingjack.bakingtime.ui.recipePOJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class RecipeIngredient implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredientName;

    public RecipeIngredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredientName);
    }

    protected RecipeIngredient(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredientName = in.readString();
    }

    public static final Parcelable.Creator<RecipeIngredient> CREATOR = new Parcelable.Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel source) {
            return new RecipeIngredient(source);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };
}
