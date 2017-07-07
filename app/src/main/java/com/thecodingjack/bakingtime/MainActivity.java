package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.thecodingjack.bakingtime.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {
    RecyclerView mRecyclerView;
    ActivityMainBinding mbinding;
    public static final String SELECTED_RECIPE = "recipe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);


        new FetchRecipesTask(this,mbinding).execute();
    }

    @Override
    public void onListItemClick(Recipe selectedRecipe) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra(SELECTED_RECIPE,selectedRecipe);
        startActivity(intent);
    }
}
