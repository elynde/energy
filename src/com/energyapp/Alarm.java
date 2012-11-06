package com.energyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent intent) {
        Vibrator v = (Vibrator)c.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);

        Intent i = new Intent(c, LogLevelActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }
}