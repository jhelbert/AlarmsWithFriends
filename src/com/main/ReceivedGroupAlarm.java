package com.main;

import android.app.Activity;
import android.os.Bundle;

public class ReceivedGroupAlarm extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String alarm = getIntent().getExtras().getString("alarm");
    setContentView(R.layout.main);
    
    }

}
