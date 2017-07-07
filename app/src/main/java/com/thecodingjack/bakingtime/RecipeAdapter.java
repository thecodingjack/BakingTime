package com.thecodingjack.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
    private RecipeClickListener listener;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        recipeList = recipes;
    }

    public interface RecipeClickListener {
        void onListItemClick(Recipe selectedRecipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeNameTextView;

        @Override
        public void onClick(View v) {
            listener.onListItemClick(recipeList.get(getAdapterPosition()));
        }

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.card_recipe_name);
        }

        public void bind(Context context, int position) {
            Recipe currentRecipe = recipeList.get(position);
            recipeNameTextView.setText(currentRecipe.getName());


        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        holder.bind(context, position);

    }

    @Override
    public int getItemCount() {
        if (recipeList == null) return 0;
        return recipeList.size();
    }
}
