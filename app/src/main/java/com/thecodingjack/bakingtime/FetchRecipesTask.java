package com.thecodingjack.bakingtime;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;

import com.thecodingjack.bakingtime.databinding.ActivityMainBinding;
import com.thecodingjack.bakingtime.util.NetworkUtil;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class FetchRecipesTask extends AsyncTask<Void,Void,ArrayList<Recipe>> {
    private ActivityMainBinding mbinding;
    private RecipeAdapter mAdapter;
    private Context context;


    public FetchRecipesTask(Context ctx, ActivityMainBinding binding) {
        context=ctx;
        mbinding = binding;
    }

    @Override
    protected ArrayList<Recipe> doInBackground(Void... params) {
        URL url;
        try{
            url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
            String JSONResponse = NetworkUtil.getResponseFromHttp(url);
            ArrayList<Recipe> recipeList = NetworkUtil.extractJSONResponse(JSONResponse);
            return recipeList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        mAdapter = new RecipeAdapter(recipes);
        mbinding.recyclerview.setLayoutManager(layoutManager);
        mbinding.recyclerview.setAdapter(mAdapter);
    }
}
