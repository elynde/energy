package com.example;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;

public class ViewDataActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        String data = new DBHelper(this).getData();
        ((TextView)findViewById(R.id.data)).setText(data);
    }
}
