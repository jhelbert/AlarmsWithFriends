package com.main;

import java.util.List;
import java.util.Random;

import sqlite.MyAlarmDB;
import sqlite.MyAlarmDataSource;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class TestDatabaseActivity extends ListActivity {
    private MyAlarmDataSource datasource;
    private Button add;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datasource = new MyAlarmDataSource(this);
        datasource.open();
        setContentView(R.layout.testlayout);
        add = (Button) findViewById(R.id.add);
        
        List<MyAlarmDB> values = datasource.getAllComments();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
       
    
        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                @SuppressWarnings("unchecked")
                ArrayAdapter<MyAlarmDB> adapter = (ArrayAdapter<MyAlarmDB>) getListAdapter();
                MyAlarmDB comment = null;
                
                    int[] comments = new int[] { 543, 3343, 344 };
                    int nextInt = new Random().nextInt(3);
                    // Save the new comment to the database
                    comment = datasource.createAlarm(comments[nextInt]);
                    try {
                    adapter.add(comment);
                    }
                    catch (Exception e){
                        
                    }
                    
            }
        });
            
            
        

    }


    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //datasource.close();
        super.onPause();
    }

}
