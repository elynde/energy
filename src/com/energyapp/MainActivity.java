package com.energyapp;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.main);

        Helpers.openActivityOnClick(this, R.id.view_data_button, ViewDataActivity.class);
        Helpers.openActivityOnClick(this, R.id.log_button, LogLevelActivity.class);
        Helpers.openActivityOnClick(this, R.id.view_graph, GraphMenuActivity.class);
    }
}
