package com.energyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.*;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "energy_app";
    private static String TABLE_NAME = "energy_levels";
    private static String LEVEL = "level";
    private static String TIME = "log_time";
    private static String YEAR = "year";
    private static String MONTH = "month";
    private static String DAY = "day";
    private static String HOUR = "hour";
    private static int DB_VERSION = 1;

    public DBHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER)",
                TABLE_NAME,
                TIME,
                LEVEL
        );

        sqLiteDatabase.execSQL(sql);

    }

    public void logLevel(int level) {
        ContentValues c = new ContentValues();
        c.put(TIME, System.currentTimeMillis()/1000);
        c.put(LEVEL, level);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, c);
        db.close();

    }

    public ArrayList<LogEntry> getData() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                TABLE_NAME,
                new String[]{TIME, LEVEL},
                "",
                new String[]{},
                null,
                null,
                String.format("%s DESC", TIME)
        );

        ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
        while (c.moveToNext()) {
            entries.add(new LogEntry(c.getLong(0), c.getInt(1)));
        }

        db.close();
        return entries;
    }

    public void updateEntry(long old_time, LogEntry e) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(TIME, e.getRawTime());
        c.put(LEVEL, e.getLevel());
        db.update(TABLE_NAME, c, String.format("%s = ?", TIME), new String[] { Long.toString(old_time) });
        db.close();
    }

    public void deleteEntry(long old_time) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, String.format("%s = ?", TIME), new String[] { Long.toString(old_time)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
