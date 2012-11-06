package com.energyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.R;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.view_data);

        Helpers.openActivityOnClick(this, R.id.view_data_button, ViewDataActivity.class);
        Helpers.openActivityOnClick(this, R.id.log_button, LogLevelActivity.class);
    }
}
