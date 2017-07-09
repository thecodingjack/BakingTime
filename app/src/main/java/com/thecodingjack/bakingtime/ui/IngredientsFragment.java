package com.thecodingjack.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeIngredient;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class IngredientsFragment extends Fragment {
    public static final String RECIPE_LIST = "recipe_list";
    private ArrayList<RecipeIngredient> ingredientList;

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState!= null){
            ingredientList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
        }

        View view = inflater.inflate(R.layout.fragment_ingredients,container,false);
        final TextView mtextView = (TextView) view.findViewById(R.id.ingredients_frag_TV);

        if(ingredientList !=null){

            StringBuilder sb = new StringBuilder();
            for(int i =0 ; i<ingredientList.size();i++){
                RecipeIngredient currentItem = ingredientList.get(i);
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

    public void setIngredientList(ArrayList<RecipeIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPE_LIST, ingredientList);
    }
}
