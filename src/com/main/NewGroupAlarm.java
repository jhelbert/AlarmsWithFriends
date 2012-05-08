package com.main;
import android.telephony.SmsManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NewGroupAlarm extends Activity {
    Toast mToast;
    private SQLiteAdapter mySQLiteAdapter;
    private static final int CONTACT_PICKER_RESULT = 1001;
    TimePicker alarm_time = null;
    TextView dataview = null;
    Spinner spin = null;
    DatePicker date;
    Button dateButton;
    Button descButton;
    EditText description;
    protected AppPreferences appPrefs;
    Calendar calendar = Calendar.getInstance();
    int hours = calendar.get(Calendar.HOUR_OF_DAY);
    int mins = calendar.get(Calendar.MINUTE);
    int c_month = calendar.get(Calendar.MONTH);
    int c_date = calendar.get(Calendar.DAY_OF_MONTH);
    int c_year = calendar.get(Calendar.YEAR);
    ArrayList<GroupList> groups = new ArrayList<GroupList>();
    boolean descMode = true;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefs = new AppPreferences(getApplicationContext());
        
        setContentView(R.layout.newgroupalarm);
        date = (DatePicker)findViewById(R.id.datePicker2);
        dateButton = (Button)findViewById(R.id.dateButton);
        descButton = (Button)findViewById(R.id.descButton);
        description = (EditText)findViewById(R.id.group_alarm_description);
        date.setVisibility(4);
        descButton.getBackground().setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
        
        dateButton.getBackground().setColorFilter(0xFFC0C0C0, PorterDuff.Mode.MULTIPLY);
        dateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                date.setVisibility(0);
                dateButton.getBackground().setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
                description.setVisibility(4);
                descButton.getBackground().setColorFilter(0xFFC0C0C0, PorterDuff.Mode.MULTIPLY);
                
            }
            
        });
        
        descButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                date.setVisibility(4);
                description.setVisibility(0);
                descButton.getBackground().setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
                
                dateButton.getBackground().setColorFilter(0xFFC0C0C0, PorterDuff.Mode.MULTIPLY);
                
            }
            
        });
        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.one_shot_group);
        button.setOnClickListener(mOneShotListener);
        button.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
        Button new_group = (Button)findViewById(R.id.new_list);
        new_group.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        new_group.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(NewGroupAlarm.this, ContactPickerActivity.class);
                
                startActivityForResult(intent,99);
                
            }
            
        });
        
        date.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int arg1, int arg2,
                    int arg3) {
                
                   c_date = arg3;
                   c_month = arg2;
                   c_year = arg1;
                }
                
            });
    
        
        alarm_time = (TimePicker)findViewById(R.id.timePickerGroup);
        alarm_time.setOnTimeChangedListener(new OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker arg0, int hour, int min) {
                hours = hour;
                mins = min;
                
            }
            
        });
        dataview = (TextView)findViewById(R.id.groupdata);
        String s = "";

        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        ArrayList<String> groups = mySQLiteAdapter.getGroups();
        
        
        ArrayAdapter <CharSequence> adapter =
                new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
              adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (String g : groups) {
            adapter.add(g);
            
        }
        
        spin = (Spinner) findViewById(R.id.spinner1);
        spin.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //save(output);
        
    }
    
    
    
    

    private OnClickListener mOneShotListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            
            Intent intent = new Intent(NewGroupAlarm.this, OneShotAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(NewGroupAlarm.this,
                    0, intent, 0);

          
            Date date = new Date();
            
            if ((calendar.get(Calendar.HOUR_OF_DAY) > hours)) {
                calendar.add(Calendar.DATE, 1);
            }
            
            int year = c_year;
            int day = c_date;
            int month = c_month;
            calendar.set(year, month, day, hours, mins, 0);
            String msg = "alarm:" + calendar.toString();
            

            // Schedule the alarm!
            int true_month = month + 1;
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            mySQLiteAdapter = new SQLiteAdapter(getBaseContext());
            mySQLiteAdapter.openToWrite();
            mySQLiteAdapter.insertAlarm("", "", hours + ":" + mins, appPrefs.getSnoozeCount(),true_month + "-" + day + "-" + year);
            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
           
                    String list = spin.getSelectedItem().toString(); 
                    
                    msg = "AWF " + list + " alarm:" + year + "," + month + "," + day + "," + hours + "," + mins + ":" + description.getText().toString();
                    String[] contacts =mySQLiteAdapter.getNumbers(list).split(",");
                    PendingIntent sentPI = PendingIntent.getBroadcast(v.getContext(), 0,
                            new Intent("SMS_SENT"), 0);
                 
                        PendingIntent deliveredPI = PendingIntent.getBroadcast(v.getContext(), 0,
                            new Intent("SMS_DELIVERED"), 0);
                    for (String phone : contacts) {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phone, null, msg, sentPI, null); 
                        
                    }
                
            
            mToast = Toast.makeText(NewGroupAlarm.this, "Alarm Sent!  " + msg,
                    Toast.LENGTH_LONG);
            mToast.show();
            finish();
            
        }
    };

    String person = "";
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
            switch (requestCode) {  
            case CONTACT_PICKER_RESULT:  
                
                // handle contact results  
                Bundle extras = data.getExtras();  
                Set<String> keys = extras.keySet();  
                Iterator<String> iterate = keys.iterator();  
                String ks = "";
                while (iterate.hasNext()) {  
                    ks  += iterate.next() + "  ";  
                    //extras.toString();
                    person = extras.get("android.intent.extra.shortcut.NAME").toString();
                 
                }  
                
                Uri result = data.getData(); 
                //text.setText(extras.toString());
                break;
            case 99:
                String s = "";

                Button new_group = (Button)findViewById(R.id.one_shot_group);
                new_group.setText(groups.toString());
                Bundle extras2 = data.getExtras();  
                ArrayList<String> newGroupNumbers = extras2.getStringArrayList("numbers");
                String newGroupName = extras2.getString("name");
                
                
                GroupList g = new GroupList(newGroupName);
                ArrayAdapter <CharSequence> adapter =
                        new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
                      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                
                    adapter.add(g.getName());
                    
                
                adapter.notifyDataSetChanged();
                Spinner spin = (Spinner) findViewById(R.id.spinner1);
                spin.setAdapter(adapter);
                g.addAll(newGroupNumbers);
                groups.add(g);
  
                //new_group.setText(o);
      
                
          
  
            }  
      
        } else {  
            // gracefully handle failure  
            
        }  
    }
}



