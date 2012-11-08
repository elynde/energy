package com.energyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.R;

public class GraphMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.graphs_menu);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GraphMenuActivity.this, "Stop looking at the graphs Elliot!", Toast.LENGTH_SHORT).show();
            }
        };

        ((Button)findViewById(R.id.average)).setOnClickListener(listener);
        ((Button)findViewById(R.id.daily)).setOnClickListener(listener);
        //Helpers.openActivityOnClick(this, R.id.daily, EnergyGraphView.class);
        //Helpers.openActivityOnClick(this, R.id.average, AverageGraphView.class);
    }
}
