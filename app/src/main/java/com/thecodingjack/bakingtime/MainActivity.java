package com.thecodingjack.bakingtime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.thecodingjack.bakingtime.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ActivityMainBinding mbinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
//        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);


        new FetchRecipesTask(this,mbinding).execute();
    }
}
