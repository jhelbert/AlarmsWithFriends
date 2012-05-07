package com.main;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReceivedGroupAlarm extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       
    super.onCreate(savedInstanceState);
    String alarm = getIntent().getExtras().getString("alarmtime");
    setContentView(R.layout.receivealarm);
    Toast.makeText(getBaseContext(), alarm, Toast.LENGTH_SHORT).show();
    String[] times = alarm.split(",");
    

    final Calendar calendar = Calendar.getInstance();
    final int year = Integer.parseInt(times[0]);
    final int month = Integer.parseInt(times[1]);
    final int day = Integer.parseInt(times[2]);
    final int hours = Integer.parseInt(times[3]);
    final int mins = Integer.parseInt(times[4]);
    TextView display = (TextView)findViewById(R.id.AcceptAlarmDisplay);

    String month_str =   new DateFormatSymbols().getMonths()[month-1];
    display.setText("New Alarm for " + hours + ":" + mins + " on " + month_str + " " + day + ", " + year + "?");
    Button accept = (Button)findViewById(R.id.accept);
    Button dismiss = (Button)findViewById(R.id.dismiss);
    accept.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(ReceivedGroupAlarm.this, OneShotAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(ReceivedGroupAlarm.this,
                    0, intent, 0);
            calendar.set(year, month, day, hours, mins, 0);
            
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            SQLiteAdapter mySQLiteAdapter = new SQLiteAdapter(getBaseContext());
            mySQLiteAdapter.openToWrite();
            mySQLiteAdapter.insertAlarm("", "", hours + ":" + mins);
            finish();
        }
        
    });
    
    dismiss.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
            
        }
        
    });
    
    Button main = (Button)findViewById(R.id.maintabs);
    main.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(ReceivedGroupAlarm.this, Tabs.class);
            
            startActivity(intent);
        }
        
    });
    
    
    }

}
