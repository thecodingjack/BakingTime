package com.thecodingjack.bakingtime.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.thecodingjack.bakingtime.BakingTimeApplication;
import com.thecodingjack.bakingtime.ConnectivityReceiver;
import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.databinding.ActivityMainBinding;
import com.thecodingjack.bakingtime.ui.recipePOJO.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    RecyclerView mRecyclerView;
    ActivityMainBinding mbinding;
    public static final String SELECTED_RECIPE = "recipe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        boolean isConnected = ConnectivityReceiver.isConnected();
        showConnectionStatus(isConnected);

        new FetchRecipesTask(this,mbinding,this).execute();
    }

    @Override
    public void onListItemClick(Recipe selectedRecipe) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra(SELECTED_RECIPE,selectedRecipe);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BakingTimeApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showConnectionStatus(isConnected);
    }

    private void showConnectionStatus(boolean isConnected){
        String message;
        if (!isConnected) {
            message = "Error connecting to internet";
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }
    }
}
