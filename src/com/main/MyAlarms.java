package com.main;

import android.app.ListActivity;
import java.io.*;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAlarms extends Activity {
    /** Called when the activity is first created. */
    ArrayAdapter<String> adapter = null;
    private Button newAlarm;
    SQLiteAdapter mySQLiteAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myalarms);
        
        ArrayList<String> values = new ArrayList<String>();
        //values.add(" ");
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        
        ArrayList<String>alarms = mySQLiteAdapter.queueAllMain();
        ListView lv = (ListView) findViewById(R.id.listview);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
            // When clicked, show a toast with the TextView text
            
            String time = ((TextView) view).getText().toString();
            String[] parts = time.split(" ");
            String mode = parts[1]; 
            int hrs = Integer.parseInt(parts[0].split(":")[0]);
            int mins = Integer.parseInt(parts[0].split(":")[1]);
            if (mode.equals("PM")) {
                hrs = (hrs + 12);
            }
            if (mode.equals("AM") && hrs == 12) {
                hrs = 0;
            }
            time = hrs + ":" + mins;
            Toast.makeText(getApplicationContext(), time,
                    Toast.LENGTH_SHORT).show();
            mySQLiteAdapter.deleteAlarm(time);
            refresh();
          }
        });
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alarms);
        lv.setAdapter(adapter);
        
        
        
        
        
        
        
        String FILENAME = "myalarms";
        FileInputStream fis;
        
    
        
        
        
        
        
    }
    public void refresh() {
        ListView lv = (ListView) findViewById(R.id.listview);
        try {
            mySQLiteAdapter = new SQLiteAdapter(this);
            mySQLiteAdapter.openToWrite();
            
            ArrayList<String>alarms = mySQLiteAdapter.queueAllMain();
            ArrayList<String>ed_alarms = new ArrayList<String>();
            for (String s : alarms) {
                String result = "";
                String[] tokens = s.split(":");
                int hrs = Integer.parseInt(tokens[0]);
                int mins = Integer.parseInt(tokens[1]);
                if (hrs == 0) {
                    result = "12:" + mins + " AM";
                }
                
                else if (hrs > 12) {
                    hrs -= 12;
                    result = hrs + ":" + mins + " PM";
                }
                if (!result.equals("")) {
                ed_alarms.add(result);
                }
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, ed_alarms);
            lv.setAdapter(adapter);
            }
            catch(Exception e) {
                
            }
    }
}