package com.thecodingjack.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.thecodingjack.bakingtime.ui.MainActivity;

import static com.thecodingjack.bakingtime.ui.DetailsActivity.PREF_RECIPE_NAME;

/**
 * Created by lamkeong on 7/9/2017.
 */

public class RecipeWidgetProvider extends AppWidgetProvider implements SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = "TESTRecipeWidgetProv";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,String recipeName , String recipeIngredients,int appWidgetId) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.widget_name,recipeName);
        views.setTextViewText(R.id.widget_ingredients,recipeIngredients);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        Log.v(TAG, PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_RECIPE_NAME,"nothing"));
        RecipeWidgetService.startActionUpdatePlantWidgets(context);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v(TAG, sharedPreferences.getString(key,"nothing"));
    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager, String recipeName , String recipeIngredient, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName ,recipeIngredient,appWidgetId);
        }
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

}
