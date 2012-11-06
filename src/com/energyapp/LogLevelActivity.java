package com.energyapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.R;

public class LogLevelActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_level);

        View.OnClickListener l = new ClickListener();
        ((Button)findViewById(R.id.level_1)).setOnClickListener(l);
        ((Button)findViewById(R.id.level_2)).setOnClickListener(l);
        ((Button)findViewById(R.id.level_3)).setOnClickListener(l);
    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int level = Integer.parseInt(((Button)view).getTag().toString());
            new DBHelper(LogLevelActivity.this).logLevel(level);

            Toast.makeText(LogLevelActivity.this, "Logged successfully", Toast.LENGTH_LONG).show();
            LogLevelActivity.this.finish();
        }
    }
}
