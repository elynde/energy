package com.energyapp;

import android.app.Activity;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * EnergyGraphView creates some dummy data to demonstrate the GraphView component.
 * <p/>
 * IMPORTANT: For examples take a look at GraphView-Demos (https://github.com/jjoe64/GraphView-Demos)
 * <p/>
 * Copyright (C) 2011 Jonas Gehring
 * Licensed under the GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/licenses/lgpl.html
 */
public class EnergyGraphView extends Activity {
    private Calendar currentDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar today = Calendar.getInstance();

        // Let's do 5am to 5am
        today.set(Calendar.HOUR_OF_DAY, 5);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);

        currentDay = today;


        final LineGraphView graphView = new LineGraphView(this, getGraphTitle()) {
            @Override
            protected String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    SimpleDateFormat f = new SimpleDateFormat("h a", Locale.US);
                    return f.format(value * 1000);
                }

                return super.formatLabel(value, isValueX);
            }
        };

        final GraphViewSeries series = new GraphViewSeries(getDataForDay(today));

        graphView.setVerticalLabels(new String[]{"high", "medium", "low"});
        graphView.setManualYAxisBounds(3, 1);
        graphView.addSeries(series);

        graphView.setClickable(true);
        setContentView(graphView);

        GestureDetector.SimpleOnGestureListener mySimpleGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Calendar new_day = (Calendar) currentDay.clone();
                if (velocityX < 0) {
                    new_day.set(Calendar.DATE, currentDay.get(currentDay.DATE) + 1);
                    if (new_day.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        currentDay = new_day;
                        series.resetData(getDataForDay(currentDay));
                    } else {
                        Toast.makeText(EnergyGraphView.this, "Sorry, I can't predict the future (yet).", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    new_day.set(Calendar.DATE, currentDay.get(currentDay.DATE) - 1);
                    GraphViewData[] data = getDataForDay(new_day);

                    if (data.length != 0) {
                        currentDay = new_day;
                        series.resetData(data);
                    } else {
                        Toast.makeText(EnergyGraphView.this, "Sorry, I didn't know you back then.", Toast.LENGTH_SHORT).show();
                    }
                }

                graphView.setTitle(getGraphTitle());
                graphView.invalidate();
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };

        final GestureDetector myGestureDetector = new GestureDetector(this, mySimpleGestureListener);

        View.OnTouchListener mOnListTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return myGestureDetector.onTouchEvent(event);
            }
        };

        graphView.setOnTouchListener(mOnListTouchListener);
    }

    private GraphViewData[] getDataForDay(Calendar day) {
        Calendar next_day = (Calendar) day.clone();
        next_day.set(Calendar.DATE, next_day.get(Calendar.DATE) + 1);

        List<LogEntry> entries = new DBHelper(this).getLevelsDuringPeriod(day.getTimeInMillis() / 1000, next_day.getTimeInMillis() / 1000);

        GraphViewData[] data = new GraphViewData[entries.size()];

        for (int i = 0; i < entries.size(); i++) {
            data[i] = new GraphViewData(entries.get(i).getRawTime(), entries.get(i).getLevel());
        }

        return data;
    }

    private String getGraphTitle() {
        return new SimpleDateFormat("E MM/dd/", Locale.US).format(currentDay.getTimeInMillis());
    }
}
