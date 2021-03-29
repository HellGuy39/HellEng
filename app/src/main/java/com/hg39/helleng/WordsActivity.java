package com.hg39.helleng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hg39.helleng.Models.User;

public class WordsActivity extends AppCompatActivity {

    private Button btnContinue;
    private boolean wasOnTest;
    private TextView textLabel;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Fragment fragTenses,fragTensesTest,fragResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        textLabel = findViewById(R.id.textLabel);

        //1 - furniture
        //2 - school supplies
        //3 - food

        Bundle arguments = getIntent().getExtras();
        int testType = arguments.getInt("testType");

        switch (testType) {
            case 1:
                textLabel.setText("Furniture");
                break;
            case 2:
                textLabel.setText("School supplies");
                break;
            case 3:
                textLabel.setText("Food");
                break;
        }
        //Bundle aFrg = new Bundle();
        //aFrg.putInt("testType", testType);
        Fragment fragWords = new WordsFragment();
        fragWords.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_wf, fragWords)
                .commit();

        btnContinue = findViewById(R.id.btnContinue);
        wasOnTest = false;
    }

    protected void setFragResult(String task1UserRes, String task2UserRes, String task3UserRes, String task4UserRes, String task5UserRes,
                                 String task6UserRes, String task7UserRes, String task8UserRes, String task9UserRes, String task10UserRes,
                                 String task1TrueRes, String task2TrueRes, String task3TrueRes, String task4TrueRes, String task5TrueRes,
                                 String task6TrueRes, String task7TrueRes, String task8TrueRes, String task9TrueRes, String task10TrueRes,
                                 int completed, int countOfTasks, int testType) {

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
        testArguments.putInt("testType", testType);

        fragResult.setArguments(testArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_wf, fragResult)
                .commit();

    }

    public void onClickNext(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение продолжения.")
                .setMessage("Вы уверены что хотите продолжить?")
                .setNegativeButton(android.R.string.no,null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startTestWordsFurniture();
                        btnContinue.setClickable(false); btnContinue.setVisibility(View.INVISIBLE);
                        wasOnTest = true;
                    }
                }).create().show();

    }

    private void startTestWordsFurniture() {
        Bundle arguments = getIntent().getExtras();
        int testType = arguments.getInt("testType");

        Fragment fragWordsTest = new WordsTestFragment();
        fragWordsTest.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_wf, fragWordsTest)
                .commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Курс ещё не окончен!")
                .setMessage("Вы действительно хотите покинуть курс не закончив его?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //SomeActivity - имя класса Activity для которой переопределяем onBackPressed();
                        WordsActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    protected void finishActivity() {
        finish();
    }
}