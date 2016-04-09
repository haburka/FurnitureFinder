package com.robb.furniturefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Window;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    protected FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundResource(R.drawable.gradient);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);



       mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Map", null),
                MapFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Favorites", null),
                FavoriteTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Manage Listings", null),
                ManageTab.class, null);

        Intent intent = getIntent();

        if(intent!=null){
            if(intent.getIntExtra("back", 2) == 0 )
                mTabHost.setCurrentTab(2);
        }
    }
}
