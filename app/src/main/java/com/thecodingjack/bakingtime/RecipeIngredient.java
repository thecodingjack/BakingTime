package com.thecodingjack.bakingtime;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class RecipeIngredient {
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
}
