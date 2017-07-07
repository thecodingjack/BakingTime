package com.thecodingjack.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(MainActivity.SELECTED_RECIPE);


        if(savedInstanceState == null){
            IngredientsFragment ingredientsFragment = new IngredientsFragment();

        }
    }
}
