package com.main;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class Tabs extends TabActivity {
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, MyAlarms.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("artists").setIndicator("My Alarms",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, AlarmsWithFriendsActivity.class);
        spec = tabHost.newTabSpec("albums").setIndicator("Set New Alarm",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Settings.class);
        spec = tabHost.newTabSpec("songs").setIndicator("Settings",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        MyAlarms myalarms = (MyAlarms)getLocalActivityManager().getActivity(TAG_B); 
        myalarms.refresh();
        SQLiteAdapter mySQLiteAdapter = new SQLiteAdapter(getBaseContext());
        mySQLiteAdapter.openToWrite();
        //mySQLiteAdapter.deleteAll();
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) { 
                if(TAG_B.equals(tabId)) { 
                    MyAlarms myalarms = (MyAlarms)getLocalActivityManager().getActivity(tabId); 
                    myalarms.refresh(); 
                } 
            } 
        
        });

        tabHost.setCurrentTab(0);
    }
    String TAG_B = "artists";
    

}
