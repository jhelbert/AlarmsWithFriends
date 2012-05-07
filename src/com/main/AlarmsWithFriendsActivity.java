package com.main;


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

public class AlarmsWithFriendsActivity extends Activity {
    /** Called when the activity is first created. */
    
    private Button myAlarms, friendsAlarms, urgentAlarms, settings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myAlarms = (Button) findViewById(R.id.sButton00);
        friendsAlarms = (Button) findViewById(R.id.sButton01);
        
        
        myAlarms.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                
                Intent intent = new Intent(AlarmsWithFriendsActivity.this, NewAlarm.class);
   
                startActivity(intent);
                
                return false;
            }

            
            
        });
        
        friendsAlarms.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                
                Intent intent = new Intent(AlarmsWithFriendsActivity.this, NewGroupAlarm.class);
   
                startActivity(intent);
                
                return false;
            }

            
            
        });
    }
}