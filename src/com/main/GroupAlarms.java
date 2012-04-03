package com.main;


import java.io.*;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class GroupAlarms extends Activity {
    /** Called when the activity is first created. */
    
    private Button newAlarm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        
        
        
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
                Intent intent = new Intent(GroupAlarms.this, NewGroupAlarm.class);
                
                startActivity(intent);
                
            }
            
        });
        
        
    }
}