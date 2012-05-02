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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyAlarms extends ListActivity {
    /** Called when the activity is first created. */
    ArrayAdapter<String> adapter = null;
    private Button newAlarm;
    SQLiteAdapter mySQLiteAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        ArrayList<String> values = new ArrayList<String>();
        //values.add(" ");
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        
        ArrayList<String>alarms = mySQLiteAdapter.queueAllMain();
        try {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alarms);
        setListAdapter(adapter);
        }
        catch(Exception e) {
            
        }
        
        
        
        
        String FILENAME = "myalarms";
        FileInputStream fis;
        try {
            fis = openFileInput(FILENAME);
            byte[] b = new byte[1000];
            int current = 0;
            fis.read(b);
            String s = new String(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myalarms);
        newAlarm = (Button)findViewById(R.id.newMyAlarm);
        newAlarm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MyAlarms.this, NewAlarm.class);
                
                startActivity(intent);
                
            }
            
        });
        
        
    }
}