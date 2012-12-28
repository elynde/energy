package com.energyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.*;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "energy_app";
    private static String TABLE_NAME = "energy_levels";
    private static String LEVEL = "level";
    private static String TIME = "log_time";
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
        c.put(TIME, System.currentTimeMillis() / 1000);
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
        db.update(TABLE_NAME, c, String.format("%s = ?", TIME), new String[]{Long.toString(old_time)});
        db.close();
    }

    public void deleteEntry(long old_time) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, String.format("%s = ?", TIME), new String[]{Long.toString(old_time)});
        db.close();
    }

    public ArrayList<LogEntry> getLevelsDuringPeriod(long start_time, long end_time) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                TABLE_NAME,
                new String[]{TIME, LEVEL},
                String.format("%s >= ? AND %s < ?", TIME, TIME),
                new String[]{Long.toString(start_time), Long.toString(end_time)},
                null,
                null,
                String.format("%s ASC", TIME)
        );

        ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
        while (c.moveToNext()) {
            entries.add(new LogEntry(c.getLong(0), c.getInt(1)));
        }

        db.close();
        return entries;
    }

    public ArrayList<Pair<Double, Double>> getAverageDay() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
        "SELECT strftime('%H', dt) + ROUND(strftime('%M', dt) / 60.0) AS time_of_day, AVG(level) as avg_level\n" +
                "FROM (\n" +
                "  SELECT datetime(log_time, 'unixepoch', 'localtime') AS dt, level FROM energy_levels\n" +
                "  WHERE dt < datetime('2012-12-02') -- when I started looking at average graphs\n" +
                ") GROUP BY time_of_day\n" +
                "ORDER BY time_of_day ASC",
                null
        );

        ArrayList<Pair<Double, Double>> entries = new ArrayList<Pair<Double, Double>>();

        TreeMap<Double, Double> hours_to_levels = new TreeMap<Double, Double>();
        while (c.moveToNext()) {
            double hour = c.getDouble(0);
            double avg_level = c.getDouble(1);

            // Not usually up, data is noise
            if (hour > 1 && hour < 8) {
                continue;
            }

            hour -= 8;

            if (hour < 0) {
                hour += 24;
            }

            hours_to_levels.put(hour, avg_level);
        }

        for (double key: hours_to_levels.keySet()) {
          entries.add(new Pair<Double, Double>(key, hours_to_levels.get(key)));
        }
        db.close();

        return entries;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
