package com.hg39.helleng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

public class AuditionActivity extends AppCompatActivity {

    WebStatusControl webStatusControl = new WebStatusControl();

    Fragment fragAuditionTest,fragResult;

    String testName;

    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audition);

        //fragAuditionTest = new

        args = getIntent().getExtras();
        testName = args.getString("testName");

        setFragAuditionTest();
    }

    protected void setFragAuditionTest() {
        fragAuditionTest = new AuditionTestFragment();

        Bundle bundle = new Bundle();

        bundle.putString("testName", testName);

        fragAuditionTest.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_au, fragAuditionTest)
                .commit();
    }

    protected void setFragResult(String task1UserRes, String task2UserRes, String task3UserRes, //String task4UserRes, String task5UserRes,
                                 String task1TrueRes, String task2TrueRes, String task3TrueRes, //String task4TrueRes, String task5TrueRes,
                                 int completed, int countOfTasks, String testName) {

        fragResult = new TestResultFragment();

        Bundle testArguments = new Bundle();

        testArguments.putString("task1UserAnswer", task1UserRes);
        testArguments.putString("task2UserAnswer", task2UserRes);
        testArguments.putString("task3UserAnswer", task3UserRes);
        //testArguments.putString("task4UserAnswer", task4UserRes);
        //testArguments.putString("task5UserAnswer", task5UserRes);

        testArguments.putString("task1TrueAnswer", task1TrueRes);
        testArguments.putString("task2TrueAnswer", task2TrueRes);
        testArguments.putString("task3TrueAnswer", task3TrueRes);
        //testArguments.putString("task4TrueAnswer", task4TrueRes);
        //testArguments.putString("task5TrueAnswer", task5TrueRes);

        testArguments.putInt("completedInt", completed);
        testArguments.putInt("countOfTasks", countOfTasks);
        testArguments.putString("testName", testName);

        fragResult.setArguments(testArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_au, fragResult)
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
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AuditionActivity.super.onBackPressed();
                    }
                }).create().show();
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
}