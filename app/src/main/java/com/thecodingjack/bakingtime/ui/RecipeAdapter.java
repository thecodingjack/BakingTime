package com.thecodingjack.bakingtime.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.Recipe;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipeList;
    private RecipeClickListener listener;

    public RecipeAdapter(ArrayList<Recipe> recipes, RecipeClickListener listener) {
        recipeList = recipes;
        this.listener = listener;
    }

    public interface RecipeClickListener {
        void onListItemClick(Recipe selectedRecipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeNameTextView;
        ImageView recipeImage;

        @Override
        public void onClick(View v) {
            Recipe selectedRecipe = recipeList.get(getAdapterPosition());
            listener.onListItemClick(selectedRecipe);
        }

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.card_recipe_name);
            recipeImage = (ImageView)itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        public void bind(Context context, int position) {
            Recipe currentRecipe = recipeList.get(position);
            recipeNameTextView.setText(currentRecipe.getName());
            String recipeImageURL = currentRecipe.getImageURL();
            if(TextUtils.isEmpty(recipeImageURL)){
                Picasso.with(context).load(R.drawable.recipe_default).placeholder(R.drawable.loading_icon).error(R.drawable.error_icon).into(recipeImage);
            }else {
                Picasso.with(context).load(currentRecipe.getImageURL()).placeholder(R.drawable.loading_icon).error(R.drawable.error_icon).into(recipeImage);
            }
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
