package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLTesterActivity extends Activity {
 
 private SQLiteAdapter mySQLiteAdapter;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqldisplay);
        TextView listContent = (TextView)findViewById(R.id.contentlist);
        
        /*
         *  Create/Open a SQLite database
         *  and fill with dummy content
         *  and close it
         */
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        mySQLiteAdapter.deleteLists();
        
        long test = mySQLiteAdapter.insertList("List1", "123,456");
        long test2 = mySQLiteAdapter.insertList("List2", "777,888,999");
        mySQLiteAdapter.close();

        /*
         *  Open the same SQLite database
         *  and read all it's content.
         */
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        
        String contentRead = mySQLiteAdapter.queueAllMain() + "\n";
        String nextr = mySQLiteAdapter.queueAllLists();
        
        String search = mySQLiteAdapter.getNumbers("List1");
        contentRead += nextr + "\n\n" + search;
        mySQLiteAdapter.close();
        
        listContent.setText(contentRead);
        
    }
}