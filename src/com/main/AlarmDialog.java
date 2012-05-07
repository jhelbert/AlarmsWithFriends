package com.main;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmDialog extends Activity{
    protected AppPreferences appPrefs;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmdialog);
      //Toast.makeText(context, "R.string.one_shot_received", Toast.LENGTH_SHORT).show();
        try {
       Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
       Ringtone r = RingtoneManager.getRingtone(getBaseContext(), notification);
        }
        catch(Exception e) {
            //ringtone will not work on emulator
        }
        final SQLiteAdapter mySQLiteAdapter = new SQLiteAdapter(getBaseContext());
        mySQLiteAdapter.openToWrite();
        Date date = new Date();
        final Calendar calendar = Calendar.getInstance();
        final int hours = calendar.get(Calendar.HOUR_OF_DAY);
        final int mins = calendar.get(Calendar.MINUTE);
        appPrefs = new AppPreferences(getApplicationContext());
        //appPrefs.saveSmsBody("testing");
        
        Button snooze = (Button)findViewById(R.id.alarmsnooze);
        snooze.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                HashMap<String,String> info = mySQLiteAdapter.getInfo(hours+ ":" + mins);
                int snooze = Integer.parseInt(info.get("snooze")) - 1;
                mySQLiteAdapter.deleteAlarm(hours + ":" + mins);
                calendar.add(Calendar.MINUTE, 2);
                final int snooze_hours = calendar.get(Calendar.HOUR_OF_DAY);
                final int snooze_mins = calendar.get(Calendar.MINUTE);
                mySQLiteAdapter.insertAlarm("", "", snooze_hours + ":" + snooze_mins, Integer.toString(snooze));
                Intent intent = new Intent(AlarmDialog.this, OneShotAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(AlarmDialog.this,
                        0, intent, 0);
                
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                
                
                Toast.makeText(arg0.getContext(), snooze_hours + ":" + snooze_mins + "\t" + snooze, Toast.LENGTH_SHORT).show();
                finish();
            }
            
        });
        
        Button dismiss = (Button)findViewById(R.id.alarmdismiss);
        dismiss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mySQLiteAdapter.deleteAlarm(hours + ":" + mins);
                finish();
                
            }
            
        });
    }

}
