package com.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.net.Uri;
import android.os.Bundle;

public class ContactPickerActivity extends ListActivity {
    /** Called when the activity is first created. */
    private static final int CONTACT_PICKER_RESULT = 1001;
    ArrayList<String> phoneNumbers = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    private SQLiteAdapter mySQLiteAdapter;
    
    EditText name = null;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.context_picker);
        final EditText name = (EditText)findViewById(R.id.invite_email);
        
        Button done = (Button)findViewById(R.id.group_done);
        done.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("numbers", phoneNumbers);
                bundle.putString("name", name.getText().toString());
                data.putExtras(bundle);
                mySQLiteAdapter = new SQLiteAdapter(v.getContext());
                mySQLiteAdapter.openToWrite();
                String nums = "";
                for (String n : phoneNumbers) {
                    nums += n + ",";
                }
                nums = nums.substring(0, nums.length() -1);
                mySQLiteAdapter.insertList(name.getText().toString(), nums);

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, data);
                } else {
                    getParent().setResult(Activity.RESULT_OK, data);
                }

                finish();
                
            }
            
        });
        ArrayList<String> values = new ArrayList<String>();
        //values.add(" ");
        
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    
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
    
                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0 && person.equals(name)) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                            null, 
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                            new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                String number = pCur.getString(
                                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                adapter.add(name);
                                adapter.notifyDataSetChanged();
                                phoneNumbers.add(number);
                            } 
                            pCur.close();
                    }
                   }
            }
                
          
  
            }  
      
        } else {  
            // gracefully handle failure  
            
        }  
    }  
}