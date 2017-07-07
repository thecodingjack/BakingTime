package com.thecodingjack.bakingtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class IngredientsFragment extends Fragment {
    public static final String RECIPE_LIST = "recipe_list";
    public static final String LIST_INDEX = "list_index";
    private ArrayList<Recipe> recipesList;
    private int listIndex;

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState!= null){
            recipesList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            listIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        View view = inflater.inflate(R.layout.fragment_ingredients,container,false);
        final TextView mtextView = (TextView) view.findViewById(R.id.ingredients_frag_TV);

        if(recipesList !=null){
            ArrayList<RecipeIngredient> ingredientArrayList = recipesList.get(listIndex).getIngredients();
            StringBuilder sb = new StringBuilder();
            for(int i =0 ; i<ingredientArrayList.size();i++){
                RecipeIngredient currentItem = ingredientArrayList.get(i);
                double quantity = currentItem.getQuantity();
                String measure = currentItem.getMeasure();
                String name = currentItem.getIngredientName();
                String ItemDetails = quantity + " " + measure + " " + name;
                sb.append(ItemDetails +"\n");
            }
            mtextView.setText(sb.toString());
        }

        return view;
    }

    public void setRecipesList(ArrayList<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPE_LIST, recipesList);
        outState.putInt(LIST_INDEX,listIndex);
    }
}
