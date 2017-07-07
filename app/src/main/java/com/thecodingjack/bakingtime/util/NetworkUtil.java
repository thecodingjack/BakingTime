package com.thecodingjack.bakingtime.util;

import com.thecodingjack.bakingtime.Recipe;
import com.thecodingjack.bakingtime.RecipeIngredient;
import com.thecodingjack.bakingtime.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class NetworkUtil {

    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Recipe> extractJSONResponse(String JSONResponse) throws JSONException {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        JSONArray recipeJArray = new JSONArray(JSONResponse);
        for (int i = 0; i < recipeJArray.length(); i++) {
            JSONObject recipeJObj = recipeJArray.getJSONObject(i);
            int recipeID = recipeJObj.getInt("id");
            String recipeName = recipeJObj.getString("name");
            ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
            ArrayList<RecipeStep> recipeSteps = new ArrayList<>();

            JSONArray ingredientsJArray = recipeJObj.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsJArray.length(); j++) {
                JSONObject ingredientJObj = ingredientsJArray.getJSONObject(j);
                double quantity = ingredientJObj.getDouble("quantity");
                String measure = ingredientJObj.getString("measure");
                String ingredientName = ingredientJObj.getString("ingredient");
                RecipeIngredient ingredient = new RecipeIngredient(quantity, measure, ingredientName);
                recipeIngredients.add(ingredient);
            }

            JSONArray stepsJArray = recipeJObj.getJSONArray("steps");
            for(int k = 0; k< stepsJArray.length();k++){
                JSONObject stepsJObj = stepsJArray.getJSONObject(k);
                String shortDescription =  stepsJObj.getString("shortDescription");
                String fullDescription = stepsJObj.getString("description");
                String videoURL = stepsJObj.getString("videoURL");
                String thumbnailURL = stepsJObj.getString("thumbnailURL");
                RecipeStep step = new RecipeStep(shortDescription,fullDescription,videoURL,thumbnailURL);
                recipeSteps.add(step);
            }
            Recipe recipe = new Recipe(recipeID,recipeName,recipeIngredients,recipeSteps);
            recipeList.add(recipe);
        }
        return recipeList;
    }


}
