package com.thecodingjack.bakingtime;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipeList;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
    }

    public void setRecipeList(ArrayList<Recipe> recipes){
        recipeList = recipes;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView recipeNameTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView)itemView.findViewById(R.id.card_recipe_name);
        }

        public void bind(int position){
            recipeNameTextView.setText(recipeList.get(position).getName());
            Log.v("TEST",recipeList.get(position).getName());
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if(recipeList == null)return 0;
        return recipeList.size();
    }
}
