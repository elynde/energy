package com.energyapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Calendar;
import java.util.Date;

public class LogEntryActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final LogEntry e = (LogEntry) getIntent().getExtras().getParcelable("entry");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_entry);

        final Integer[] levels = {3, 2, 1};

        final Spinner level_picker = (Spinner) findViewById(R.id.level_picker);
        level_picker.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, levels));
        level_picker.setSelection(3 - e.getLevel());

        final TimePicker time_picker = (TimePicker)findViewById(R.id.time_picker);
        time_picker.setCurrentHour(e.getTime().get(Calendar.HOUR_OF_DAY));
        time_picker.setCurrentMinute(e.getTime().get(Calendar.MINUTE));

        ((Button)findViewById(R.id.save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int level = (int) levels[level_picker.getSelectedItemPosition()];

                int hour = time_picker.getCurrentHour();
                int minute = time_picker.getCurrentMinute();

                Calendar time = Calendar.getInstance();
                time.set(
                        e.getTime().get(Calendar.YEAR),
                        e.getTime().get(Calendar.MONTH),
                        e.getTime().get(Calendar.DATE),
                        hour,
                        minute
                );

                LogEntry new_entry = new LogEntry(time.getTimeInMillis() / 1000, level);
                new DBHelper(LogEntryActivity.this).updateEntry(e.getRawTime(), new_entry);

                LogEntryActivity.this.finish();
            }
        });

        ((Button)findViewById(R.id.delete_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBHelper(LogEntryActivity.this).deleteEntry(e.getRawTime());

                LogEntryActivity.this.finish();
            }
        });
    }
}
