package com.hg39.helleng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChillZoneActivty extends AppCompatActivity {

    TextView tvActualVersion,tvUserVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chill_zone_activty);

        String actualVersion = getIntent().getExtras().getString("actualVersion");
        String userVersion = getIntent().getExtras().getString("version");

        tvActualVersion = findViewById(R.id.tvActualVersion);
        tvUserVersion = findViewById(R.id.tvYourVersion);

        tvActualVersion.setText("Actual version: " + actualVersion);
        tvUserVersion.setText("Installed version: " + userVersion);

    }
}