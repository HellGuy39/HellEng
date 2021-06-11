package com.hg39.helleng;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class GrammarActivity extends AppCompatActivity {

    boolean wasOnTest;

    Fragment fragGrammarRule,fragGrammarTest,fragResult;

    String testName;

    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_grammar);

        wasOnTest = false;

        arguments = getIntent().getExtras();
        testName = arguments.getString("testName");

        setFragGrammarRule();

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
                                 String task1TrueRes, String task2TrueRes, String task3TrueRes, String task4TrueRes, String task5TrueRes,
                                 int completed, int countOfTasks, String testName) {

        fragResult = new TestResultFragment();

        Bundle testArguments = new Bundle();

        testArguments.putString("task1UserAnswer", task1UserRes);
        testArguments.putString("task2UserAnswer", task2UserRes);
        testArguments.putString("task3UserAnswer", task3UserRes);
        testArguments.putString("task4UserAnswer", task4UserRes);
        testArguments.putString("task5UserAnswer", task5UserRes);

        testArguments.putString("task1TrueAnswer", task1TrueRes);
        testArguments.putString("task2TrueAnswer", task2TrueRes);
        testArguments.putString("task3TrueAnswer", task3TrueRes);
        testArguments.putString("task4TrueAnswer", task4TrueRes);
        testArguments.putString("task5TrueAnswer", task5TrueRes);

        testArguments.putInt("completedInt", completed);
        testArguments.putInt("countOfTasks", countOfTasks);
        testArguments.putString("testName", testName);

        fragResult.setArguments(testArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragResult)
                .commit();

    }

    protected void setFragGrammarRule() {

        fragGrammarRule = new GrammarRuleFragment();
        fragGrammarRule.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragGrammarRule)
                .commit();

    }

    protected void setFragGrammarTest() {

        fragGrammarTest = new GrammarTestFragment();
        fragGrammarTest.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragGrammarTest)
                .commit();

    }

    protected void finishActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("You are not finished")
                .setMessage("If you leave the test now, the data will not be saved")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> GrammarActivity.super.onBackPressed()).create().show();
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