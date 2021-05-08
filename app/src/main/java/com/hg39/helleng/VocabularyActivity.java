package com.hg39.helleng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class VocabularyActivity extends AppCompatActivity {

    boolean wasOnTest;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Fragment fragVocabularyRule,fragVocabularyTest,fragResult;

    WebStatusControl webStatusControl = new WebStatusControl();

    String testName;

    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_vocabulary);

        fragVocabularyRule = new VocabularyRuleFragment();
        fragVocabularyTest = new VocabularyTestFragment();

        Bundle argumentsFromIntent = getIntent().getExtras();
        testName = argumentsFromIntent.getString("testName");

        arguments = new Bundle();
        arguments.putString("testName", testName);

        wasOnTest = false;

        setFragVocabularyRule();
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

    protected void setFragResult(String task1UserRes, String task2UserRes, String task3UserRes, String task4UserRes, String task5UserRes,
                                 String task6UserRes, String task7UserRes, String task8UserRes, String task9UserRes, String task10UserRes,
                                 String task1TrueRes, String task2TrueRes, String task3TrueRes, String task4TrueRes, String task5TrueRes,
                                 String task6TrueRes, String task7TrueRes, String task8TrueRes, String task9TrueRes, String task10TrueRes,
                                 int completed, int countOfTasks, String testName) {

        fragResult = new TestResultFragment();

        Bundle testArguments = new Bundle();

        testArguments.putString("task1UserAnswer", task1UserRes);
        testArguments.putString("task2UserAnswer", task2UserRes);
        testArguments.putString("task3UserAnswer", task3UserRes);
        testArguments.putString("task4UserAnswer", task4UserRes);
        testArguments.putString("task5UserAnswer", task5UserRes);
        testArguments.putString("task6UserAnswer", task6UserRes);
        testArguments.putString("task7UserAnswer", task7UserRes);
        testArguments.putString("task8UserAnswer", task8UserRes);
        testArguments.putString("task9UserAnswer", task9UserRes);
        testArguments.putString("task10UserAnswer", task10UserRes);

        testArguments.putString("task1TrueAnswer", task1TrueRes);
        testArguments.putString("task2TrueAnswer", task2TrueRes);
        testArguments.putString("task3TrueAnswer", task3TrueRes);
        testArguments.putString("task4TrueAnswer", task4TrueRes);
        testArguments.putString("task5TrueAnswer", task5TrueRes);
        testArguments.putString("task6TrueAnswer", task6TrueRes);
        testArguments.putString("task7TrueAnswer", task7TrueRes);
        testArguments.putString("task8TrueAnswer", task8TrueRes);
        testArguments.putString("task9TrueAnswer", task9TrueRes);
        testArguments.putString("task10TrueAnswer", task10TrueRes);

        testArguments.putInt("completedInt", completed);
        testArguments.putInt("countOfTasks", countOfTasks);
        testArguments.putString( "testName", testName);

        fragResult.setArguments(testArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_v, fragResult)
                .commit();

    }

    protected void setFragVocabularyRule() {
        fragVocabularyRule.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_v, fragVocabularyRule)
                .commit();
    }

    protected void setFragVocabularyTest() {
        fragVocabularyTest.setArguments(arguments);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_v, fragVocabularyTest)
                .commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("You are not finished")
                .setMessage("If you leave the test now, the data will not be saved")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //SomeActivity - имя класса Activity для которой переопределяем onBackPressed();
                        VocabularyActivity.super.onBackPressed();
                    }
                }).create().show();
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


    protected void finishActivity() {
        finish();
    }
}