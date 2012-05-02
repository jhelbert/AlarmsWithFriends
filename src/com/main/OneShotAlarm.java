package com.main;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.


/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm that
 * should occur once.
 * <p>
 * When the alarm goes off, we show a <i>Toast</i>, a quick message.
 */
public class OneShotAlarm extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "R.string.one_shot_received", Toast.LENGTH_SHORT).show();
        try {
       Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
       Ringtone r = RingtoneManager.getRingtone(context, notification);
        }
        catch(Exception e) {
            //ringtone will not work on emulator
        }
    }
}
