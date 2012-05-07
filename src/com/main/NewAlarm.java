package com.main;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NewAlarm extends Activity {
    Toast mToast;
    
    TimePicker alarm_time = null;
    
    Calendar calendar = Calendar.getInstance();
    int hours = calendar.get(Calendar.HOUR);
    int mins = calendar.get(Calendar.MINUTE);
    protected AppPreferences appPrefs;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        appPrefs = new AppPreferences(getApplicationContext());

        setContentView(R.layout.newalarm);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.one_shot);
        button.setOnClickListener(mOneShotListener);
        
        
        alarm_time = (TimePicker)findViewById(R.id.timePicker1);
        alarm_time.setOnTimeChangedListener(new OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker arg0, int hour, int min) {
                hours = hour;
                mins = min;
                
            }
            
        });
    }

    private OnClickListener mOneShotListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            
            Intent intent = new Intent(NewAlarm.this, OneShotAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(NewAlarm.this,
                    0, intent, 0);

          
            Date date = new Date();
            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            calendar.set(year, month, day, hours, mins, 0);
            
            

            // Schedule the alarm!
            
            
            SQLiteAdapter mySQLiteAdapter = new SQLiteAdapter(getBaseContext());
            mySQLiteAdapter.openToWrite();
            appPrefs.saveSnoozeCount(Integer.toString(Integer.parseInt(appPrefs.getSnoozeCount())));
            
            mySQLiteAdapter.insertAlarm("", "", hours + ":" + mins, appPrefs.getSnoozeCount());
            
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(NewAlarm.this, "one_shot_scheduled",
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };

    
    
}

