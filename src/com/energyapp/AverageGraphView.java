package com.energyapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

public class AverageGraphView extends Activity {
    private Calendar currentDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Pair<Double, Double>> entries = new DBHelper(this).getAverageDay();

        final LineGraphView graphView = new LineGraphView(this, "") {
            @Override
            protected String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    value += 8;

                    if (value >= 24) {
                        value -= 24;
                    }

                    return Helpers.formatDoubleHour(value);
                }

                return super.formatLabel(value, isValueX);
            }
        };

        GraphView.GraphViewData[] data = new GraphView.GraphViewData[entries.size()];

        for (int i = 0; i < entries.size(); i++) {
            data[i] = new GraphView.GraphViewData(entries.get(i).first, entries.get(i).second);
        }
        final GraphViewSeries series = new GraphViewSeries(data);

        graphView.setVerticalLabels(new String[]{"high", "medium", "low"});
        graphView.addSeries(series);

        setContentView(graphView);

    }
}
