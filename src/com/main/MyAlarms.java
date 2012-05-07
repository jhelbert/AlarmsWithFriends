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
            Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                Toast.LENGTH_SHORT).show();
            mySQLiteAdapter.deleteAlarm(((TextView) view).getText().toString());
            refresh();
          }
        });
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alarms);
        lv.setAdapter(adapter);
        
        
        
        
        
        
        
        String FILENAME = "myalarms";
        FileInputStream fis;
        
    
        
        
        
        newAlarm = (Button)findViewById(R.id.newMyAlarm);
        newAlarm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MyAlarms.this, NewAlarm.class);
                
                startActivity(intent);
                
            }
            
        });
        
        
    }
    public void refresh() {
        ListView lv = (ListView) findViewById(R.id.listview);
        try {
            mySQLiteAdapter = new SQLiteAdapter(this);
            mySQLiteAdapter.openToWrite();
            
            ArrayList<String>alarms = mySQLiteAdapter.queueAllMain();
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, alarms);
            lv.setAdapter(adapter);
            }
            catch(Exception e) {
                
            }
    }
}