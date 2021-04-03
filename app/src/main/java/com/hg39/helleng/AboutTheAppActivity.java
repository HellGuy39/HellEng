package com.hg39.helleng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutTheAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_app);

        String text = " _   _    ____   _     _       ____    _   _   _     _   ____   _____ " + "\n" +
                      "|@| |@|  |@@@@| |@|    |@|    |@@@@@| |@| |@| |@|   |@| |@@@@| |@@@@@|" + "\n" +
                      "|@|_|@|  |@|    |@|    |@|    |@| __  |@| |@|  |@|_|@|     |@| |@| |@|" + "\n" +
                      "|@ _ @|  |@@@@| |@|    |@|    |@||@@| |@| |@|    |@|    |@@@@| |@@@@@|" + "\n" +
                      "|@| |@|  |@|    |@|_   |@|_   |@|_|@| |@| |@|   |@|        |@|     |@|" + "\n" +
                      "|@| |@|  |@@@@| |@@@@| |@@@@| |@@@@@| |@@@@@|  |@|      |@@@@| |@@@@@|" + "\n";
    }
}