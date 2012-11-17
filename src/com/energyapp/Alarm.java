package com.energyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent intent) {
        AudioManager a = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        if (a.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(c, notification);
            r.play();
        } else if (a.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
            Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(4000);
        }

        Intent i = new Intent(c, LogLevelActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }
}
