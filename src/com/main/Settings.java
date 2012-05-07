package com.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {
    protected AppPreferences appPrefs;
    /** Called when the activity is first created. */
    ArrayAdapter<String> adapter = null;
    private Button newAlarm;
    SQLiteAdapter mySQLiteAdapter;
    
    private static final int CONTACT_PICKER_RESULT = 1001;
    ArrayList<String> values = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        appPrefs = new AppPreferences(getApplicationContext());

        final TextView snoozeTimeDisp = (TextView) findViewById(R.id.textView1);
        final SeekBar snoozeTime = (SeekBar) findViewById(R.id.seekBar1);
        snoozeTimeDisp.setText("Snooze Time: " + appPrefs.getSnoozeTime());
        snoozeTime.setProgress(Integer.parseInt(appPrefs.getSnoozeTime()));
        
        
        refresh();
        //appPrefs.saveEmContacts("");
        snoozeTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                            int progress, boolean fromUser) {
                        // TODO Auto-generated method stub
                        snoozeTimeDisp.setText("Snooze Time: " + progress);
                       
                        appPrefs.saveSnoozeTime(Integer.toString(progress));
                        String k = appPrefs.getSnoozeTime();
                        int j = Integer.parseInt(k);
                        int x = 3;
                        
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
        
        final TextView snoozeCountDisp = (TextView) findViewById(R.id.textView2);
        final SeekBar snoozeCount = (SeekBar) findViewById(R.id.seekBar2);
        
        int count = Integer.parseInt(appPrefs.getSnoozeCount()) - 1;
        snoozeCountDisp.setText("Snooze Count: " + count);
        snoozeCount.setProgress(count);
        Button clear = (Button)findViewById(R.id.clear_em);
        clear.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                appPrefs.saveEmContacts("");
                refresh();
                
            }
            
        });
        Button button = (Button)findViewById(R.id.button1);
        button.setTextSize(9);
        button.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
        //button.setPadding(50, 0, 0, 0);

        snoozeCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                            int progress, boolean fromUser) {
                        // TODO Auto-generated method stub
                        progress = progress + 1;
                        snoozeCountDisp.setText("Snooze Count: " + progress);
                       
                        appPrefs.saveSnoozeCount(Integer.toString(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
        
        
        
        
        
        
        
        
        
        
        //values.add(" ");
        
        
        
    
    }
    public void doLaunchContactPicker(View view) {  
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
                Contacts.CONTENT_URI);  
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT); 
    }
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
                    
                 
                }  
                person = extras.get("android.intent.extra.shortcut.NAME").toString();
                Uri result = data.getData(); 
                String ans = "";
                String name = "";
                String number = "";
                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0 && person.equals(name)) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                            null, 
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                            new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                number = pCur.getString(
                                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                
                                ans = name + "|" + number;
                                
                            } 
                            pCur.close();
                    }
                   }
            }
                String s = appPrefs.getEmContacts();
                if (!(s.contains(name) && s.contains(number))) {
                if (s.equals("")) {
                    appPrefs.saveEmContacts(ans);
                }
                else {
                    appPrefs.saveEmContacts(s + "!!" + ans);
                }
                refresh();
                Toast.makeText(getBaseContext(), appPrefs.getEmContacts(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "That contact is already here", Toast.LENGTH_SHORT).show();
                }
  
            }  
      
        } else {  
            // gracefully handle failure  
            
        }  
    }
    
    public void refresh() {
        ListView contacts = (ListView) findViewById(R.id.em_contacts);
        String info = appPrefs.getEmContacts();
        if (info.equals("")) {
            values = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, values);
            contacts.setAdapter(adapter);
        }
        else {
            
            String [] temp = info.split("!!");
            //snoozeCountDisp.setText(Integer.toString(temp.length));
            for (String t : temp) {
                values.add(t.split("\\|")[0]);
                //Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
            }
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        contacts.setAdapter(adapter);
    }
    }
