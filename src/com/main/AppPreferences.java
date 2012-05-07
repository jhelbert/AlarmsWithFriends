package com.main;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
     private static final String APP_SHARED_PREFS = "com.main.preferences"; //  Name of the file -.xml
     private SharedPreferences appSharedPrefs;
     private Editor prefsEditor;

     public AppPreferences(Context context)
     {
         this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
         this.prefsEditor = appSharedPrefs.edit();
     }

     
     public String getSnoozeTime() {
         return appSharedPrefs.getString("snoozetime", "5");
     }

     public void saveSnoozeTime(String text) {
         prefsEditor.putString("snoozetime", text);
         prefsEditor.commit();
     }
     public String getSnoozeCount() {
         return appSharedPrefs.getString("snoozecount", "2");
     }

     public void saveSnoozeCount(String text) {
         prefsEditor.putString("snoozecount", text);
         prefsEditor.commit();
     }
     
     public String getEmContacts() {
         return appSharedPrefs.getString("contacts", "");
     }

     public void saveEmContacts(String text) {
         prefsEditor.putString("contacts", text);
         prefsEditor.commit();
     }
}
