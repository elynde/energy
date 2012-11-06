package com.energyapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogEntry implements Parcelable{
    private long time;
    private int level;

    public LogEntry(long time, int level) {
        this.time = time;
        this.level = level;
    }


    public long getRawTime() {
        return time;
    }

    public Calendar getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getRawTime()*1000);
        return cal;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        SimpleDateFormat time_formatter = new SimpleDateFormat("MM/dd h:mm a", Locale.US);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getRawTime()*1000);

        return String.format("%s : %d", time_formatter.format(cal.getTime()), getLevel());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(time);
        parcel.writeInt(level);
    }

    public static final Parcelable.Creator<LogEntry> CREATOR
            = new Parcelable.Creator<LogEntry>() {
        @Override
        public LogEntry createFromParcel(Parcel in) {
            return new LogEntry(in);
        }

        @Override
        public LogEntry[] newArray(int size) {
            return new LogEntry[size];
        }
    };

    private LogEntry(Parcel in) {
        time = in.readLong();
        level = in.readInt();
    }
}
