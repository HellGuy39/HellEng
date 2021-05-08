package com.hg39.helleng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.Locale;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {

    com.google.android.material.appbar.MaterialToolbar toolbar;

    WebStatusControl webStatusControl = new WebStatusControl();

    AutoCompleteTextView languageDropdown;

    TextView tvLanguage;

    String[] languages = { "English (recommended)", "Русский" };
    String actualSelected;

    public static final String CONFIG_FILE = "config";
    public static final String CONFIG_LANGUAGE = "language";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences(CONFIG_FILE, 0);
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

        setContentView(R.layout.activity_settings);

        languageDropdown = findViewById(R.id.languageDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageDropdown.setAdapter(adapter);

        //languageDropdown.setHintTextColor(ContextCompat.getColor(this,R.color.black));

        if(language.equalsIgnoreCase("en"))
            languageDropdown.setHint(languages[0]);
        else if (language.equalsIgnoreCase("ru"))
            languageDropdown.setHint(languages[1]);
        else
            languageDropdown.setHint(languages[0]);

        languageDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                String countryCode;
                if (item.equalsIgnoreCase(languages[0]))
                {
                    countryCode = "en";
                    editor = sp.edit();
                    editor.putString(CONFIG_LANGUAGE,countryCode);
                    editor.apply();
                    languageDropdown.setHint(languages[0]);
                    reloadActivity(countryCode);
                }
                else if (item.equalsIgnoreCase(languages[1]))
                {
                    countryCode = "ru";
                    editor = sp.edit();
                    editor.putString(CONFIG_LANGUAGE,countryCode);
                    editor.apply();
                    languageDropdown.setHint(languages[1]);
                    reloadActivity(countryCode);
                }
                else
                {
                    countryCode = "en";
                    editor = sp.edit();
                    editor.putString(CONFIG_LANGUAGE,countryCode);
                    editor.apply();
                    languageDropdown.setHint(languages[0]);
                    reloadActivity(countryCode);
                }

            }
        });

        toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //finish();
            }
        });
    }

    private void reloadActivity(String lang) {

        Locale myLocale = new Locale(lang);

        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);



    }

    @Override
    protected void onStart() {
        super.onStart();
        //webStatusControl.setWebStatus("Online");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //webStatusControl.setWebStatus("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //webStatusControl.setWebStatus("Offline");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //webStatusControl.setWebStatus("Offline");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //webStatusControl.setWebStatus("Offline");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}