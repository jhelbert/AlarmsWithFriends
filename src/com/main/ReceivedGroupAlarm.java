package com.main;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ReceivedGroupAlarm extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String alarm = getIntent().getExtras().getString("alarmtime");
    setContentView(R.layout.main);
    Toast.makeText(getBaseContext(), alarm, Toast.LENGTH_SHORT).show();
    String[] times = alarm.split(",");
    

    Calendar calendar = Calendar.getInstance();
    int year = Integer.parseInt(times[0]);
    int month = Integer.parseInt(times[1]);
    int day = Integer.parseInt(times[2]);
    int hours = Integer.parseInt(times[3]);
    int mins = Integer.parseInt(times[4]);
    Intent intent = new Intent(ReceivedGroupAlarm.this, OneShotAlarm.class);
    PendingIntent sender = PendingIntent.getBroadcast(ReceivedGroupAlarm.this,
            0, intent, 0);
    calendar.set(year, month, day, hours, mins, 0);
    
    AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    
    }

}
