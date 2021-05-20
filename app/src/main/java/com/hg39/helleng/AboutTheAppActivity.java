package com.hg39.helleng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import static com.hg39.helleng.MainActivity.version;
import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class AboutTheAppActivity extends AppCompatActivity {

    com.google.android.material.appbar.MaterialToolbar toolbar;

    String HellGuy39,textSong,someText;

    TextView tvVersion, tvUpdate;
    TextView something, somethingToo;

    com.google.android.material.card.MaterialCardView cardSamurai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_about_the_app);

        tvVersion = findViewById(R.id.version);
        tvUpdate = findViewById(R.id.state);
        something = findViewById(R.id.something);
        somethingToo = findViewById(R.id.somethingToo);
        cardSamurai = findViewById(R.id.card_samurai);

        cardSamurai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutTheAppActivity.this, "Never Fade Away", Toast.LENGTH_SHORT).show();
            }
        });

        toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        someText =  "refused";

        textSong =  "...\n" +
                    "i see your eyes, i know you see me\n" +
                    "you're like a ghost how you're everywhere\n" +
                    "I am your demon never leaving\n" +
                    "a metal soul of rage and fear\n" +
                    "\n" +
                    "that one thing that changed it all\n" +
                    "that one sin that caused the fall\n" +
                    "\n" +
                    "a thing of beauty - I know\n" +
                    "will never fade away\n" +
                    "what you did to me - I know\n" +
                    "said what you had to say\n" +
                    "but a thing of beauty - I know\n" +
                    "will never fade away\n" +
                    "and I'll do my duty - I know\n" +
                    "somehow I'll find a way\n" +
                    "but a thing of beauty\n" +
                    "will never fade away\n" +
                    "and I'll do my duty\n" +
                    "...";

        HellGuy39 =
                " _   _    ____   _      _      ____    _   _   _     _   ____   _____ " + "\n" +
                "|@| |@|  |@@@@| |@|    |@|    |@@@@@| |@| |@| |@|   |@| |@@@@| |@@@@@|" + "\n" +
                "|@|_|@|  |@|    |@|    |@|    |@| __  |@| |@|  |@|_|@|     |@| |@| |@|" + "\n" +
                "|@ _ @|  |@@@@| |@|    |@|    |@||@@| |@| |@|    |@|    |@@@@| |@@@@@|" + "\n" +
                "|@| |@|  |@|    |@|_   |@|_   |@|_|@| |@| |@|   |@|        |@|     |@|" + "\n" +
                "|@| |@|  |@@@@| |@@@@| |@@@@| |@@@@@| |@@@@@|  |@|      |@@@@| |@@@@@|" + "\n";





        updateUI();
    }

    private void updateUI() {
        tvVersion.setText("Version: " + version);
        tvUpdate.setText("State: pre-release");
        something.setText(textSong);
        somethingToo.setText(someText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void setLanguage() {
        SharedPreferences sp = getSharedPreferences(CONFIG_FILE, 0);
        String language = sp.getString(CONFIG_LANGUAGE, "en");

        Locale locale = new Locale(language);

        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }

}