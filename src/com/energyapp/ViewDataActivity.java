package com.energyapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends ListActivity {
    private ArrayList<LogEntry> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(this, LogEntryActivity.class);
        i.putExtra("entry", items.get(position));
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        items = new DBHelper(this).getData();
        ArrayAdapter<LogEntry> adapter = new ArrayAdapter<LogEntry>(this, R.layout.list_item, items);
        setListAdapter(adapter);
    }
}
