package com.main;

import java.util.Calendar;
import java.util.Date;
import android.os.SystemClock;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.content.Context;

 
public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str += msgs[i].getMessageBody().toString();
                if (str.startsWith("AWF")) {
                    String alarm = str.split(":")[1];
                    String[] times = alarm.split(",");
                    Intent in = new Intent(context, AlarmsWithFriendsActivity.class);
                    PendingIntent sender = PendingIntent.getBroadcast(context,
                            0, intent, 0);

                    Calendar calendar = Calendar.getInstance();
                    int year = Integer.parseInt(times[0]);
                    int month = Integer.parseInt(times[1]);
                    int day = Integer.parseInt(times[2]);
                    int hours = Integer.parseInt(times[3]);
                    int mins = Integer.parseInt(times[4]);
                    
                    calendar.set(year, month, day, hours, mins, 0);
                    String msg = "alarm:" + calendar.toString();
                    

                    // Schedule the alarm!
                    Intent interpret = new Intent(context, ReceivedGroupAlarm.class);
                    interpret.putExtra("alarmtime", alarm);
                    interpret.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                    context.startActivity(interpret);  
                    //AlarmManager am = (AlarmManager)getSystemService("alarm");
                    //am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                }
            }
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }                         
    }
}
