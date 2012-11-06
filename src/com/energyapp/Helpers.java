package com.energyapp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.R;

public class Helpers {
    public static void openActivityOnClick(final Activity curr, final int element, final java.lang.Class<?> to_open) {
        ((Button)curr.findViewById(element)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr.startActivity(new Intent(curr, to_open));
            }
        });

    }
}
