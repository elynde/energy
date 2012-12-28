package com.energyapp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Helpers {
    public static void openActivityOnClick(final Activity curr, final int element, final java.lang.Class<?> to_open) {
        ((Button)curr.findViewById(element)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr.startActivity(new Intent(curr, to_open));
            }
        });

    }

    public static String formatDoubleHour(double hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, (int)hour);
        c.set(Calendar.MINUTE, (int)(60 * (hour - (int)hour)));
        SimpleDateFormat f = new SimpleDateFormat("h:mm a", Locale.US);
        String ret = f.format(c.getTime());
        return ret;
    }
}
