package com.hg39.helleng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Locale;
import java.util.Objects;

import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class TestMakerActivity extends AppCompatActivity {

    public static final String TASK_1 = "Task 1";
    public static final String TASK_2 = "Task 2";
    public static final String TASK_3 = "Task 3";
    public static final String TASK_4 = "Task 4";
    public static final String TASK_5 = "Task 5";
    public static final String TASK_6 = "Task 6";
    public static final String TASK_7 = "Task 7";
    
    public static final String TYPE_TEXT_FIELD = "TextField";
    public static final String TYPE_RADIO_GROUP = "RadioGroup";
    public static final String TASK_TYPE = "Task Type";
    public static final String TASK_DESCRIPTION = "Description";
    public static final String TASK_QUESTION = "Question";
    public static final String TASK_ANSWER = "Answer";

    SharedPreferences sp;

    Fragment createTestFragment;
    Fragment findOtherTestFragment;
    Fragment completeOtherTestFragment;
    Fragment fragResult;
    Fragment testPreviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_test_maker);

        createTestFragment = new CreateTestFragment();
        findOtherTestFragment = new FindUserTestFragment();
        completeOtherTestFragment = new CompleteOtherTestFragment();
        testPreviewFragment = new TestPreviewFragment();

        Bundle bundle = getIntent().getExtras();
        String action = bundle.getString("Action");

        if (action.equals("CreateTest"))
        {
            setFragCreateTest();
        }
        else if (action.equals("ViewOtherTest"))
        {
            setFragFindTest();
        }
        else if (action.equals("TestPreview"))
        {
            String testID = bundle.getString("testID");
            setFragTestPreview(testID);
        }
    }

    protected void setFragTestPreview(String testID) {
        Bundle bundle = new Bundle();
        bundle.putString("testID", testID);
        testPreviewFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,testPreviewFragment)
                .commit();
    }

    protected void setFragCreateTest() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,createTestFragment)
                .commit();
    }

    protected void setFragFindTest() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,findOtherTestFragment)
                .commit();
    }

    protected void setFragCompleteOtherTest(String testID) {
        Bundle bundle = new Bundle();
        bundle.putString("testID", testID);
        completeOtherTestFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,completeOtherTestFragment)
                .commit();
    }

    protected void setLanguage() {
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
    }

    protected void setFragResult(String task1UserRes, String task2UserRes, String task3UserRes, String task4UserRes, String task5UserRes, String task6UserRes, String task7UserRes,
                                 String task1TrueRes, String task2TrueRes, String task3TrueRes, String task4TrueRes, String task5TrueRes, String task6TrueRes, String task7TrueRes,
                                 int completed, String countOfTasks, String testName) {

        int countOfTasksInt = 3;

        fragResult = new TestResultFragment();

        Bundle testArguments = new Bundle();

        testArguments.putString("task1UserAnswer", task1UserRes);
        testArguments.putString("task2UserAnswer", task2UserRes);
        testArguments.putString("task3UserAnswer", task3UserRes);

        testArguments.putString("task1TrueAnswer", task1TrueRes);
        testArguments.putString("task2TrueAnswer", task2TrueRes);
        testArguments.putString("task3TrueAnswer", task3TrueRes);

        if (countOfTasks.equals("3"))
        {
            countOfTasksInt = 3;
        }
        else if (countOfTasks.equals("5"))
        {
            countOfTasksInt = 5;

            testArguments.putString("task4UserAnswer", task4UserRes);
            testArguments.putString("task5UserAnswer", task5UserRes);

            testArguments.putString("task4TrueAnswer", task4TrueRes);
            testArguments.putString("task5TrueAnswer", task5TrueRes);
        }
        else if (countOfTasks.equals("7"))
        {
            testArguments.putString("task4UserAnswer", task4UserRes);
            testArguments.putString("task5UserAnswer", task5UserRes);

            testArguments.putString("task4TrueAnswer", task4TrueRes);
            testArguments.putString("task5TrueAnswer", task5TrueRes);

            testArguments.putString("task6UserAnswer", task6UserRes);
            testArguments.putString("task7UserAnswer", task7UserRes);

            testArguments.putString("task6TrueAnswer", task6TrueRes);
            testArguments.putString("task7TrueAnswer", task7TrueRes);

            countOfTasksInt = 7;
        }

        testArguments.putInt("completedInt", completed);
        testArguments.putInt("countOfTasks", countOfTasksInt);
        testArguments.putString("testName", testName);

        fragResult.setArguments(testArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragResult)
                .commit();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm exit")
                .setMessage("Do you really want to leave?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();

        /*int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }*/
    }
}
