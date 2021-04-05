package com.hg39.helleng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    com.google.android.material.appbar.MaterialToolbar toolbar;

    WebStatusControl webStatusControl = new WebStatusControl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        webStatusControl.setWebStatus("Online");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webStatusControl.setWebStatus("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        webStatusControl.setWebStatus("Offline");
    }

    @Override
    protected void onStop() {
        super.onStop();
        webStatusControl.setWebStatus("Offline");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webStatusControl.setWebStatus("Offline");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}