package com.thecodingjack.bakingtime;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.thecodingjack.bakingtime.ui.DetailsActivity.PREF_RECIPE_INGREDIENT;
import static com.thecodingjack.bakingtime.ui.DetailsActivity.PREF_RECIPE_NAME;

/**
 * Created by lamkeong on 7/9/2017.
 */

public class RecipeWidgetService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "com.example.android.bakingtime.action.update_recipe_widgets";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                handleActionUpdatePlantWidgets();
            }
        }
    }

    private void handleActionUpdatePlantWidgets() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String recipeName = sharedPreferences.getString(PREF_RECIPE_NAME, "nothing");
        int imgRes = R.drawable.recipe_default;
        String recipeIngredient = sharedPreferences.getString(PREF_RECIPE_INGREDIENT, "nothing");;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updatePlantWidgets(this, appWidgetManager,recipeName ,recipeIngredient,appWidgetIds);
    }
}
