package com.hg39.helleng;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hg39.helleng.Models.User;

public class TensesActivity extends AppCompatActivity {

    private Button btnContinue;
    private boolean wasOnTest;
    private TextView textLabel;
    private int testType;
    RelativeLayout relativeLayout;
    com.google.android.material.card.MaterialCardView card1;

    Fragment fragTenses,fragTensesTest,fragResult;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenses);

        relativeLayout = findViewById(R.id.layout_with_buttons);
        card1 = findViewById(R.id.card1);
        textLabel = findViewById(R.id.textLabel);
        btnContinue = findViewById(R.id.btnContinue);
        wasOnTest = false;

        Bundle arguments = getIntent().getExtras();
        testType = arguments.getInt("testType");

        switch (testType) {
            case 4:
                textLabel.setText("Tenses: Present Simple");
                break;
            case 5:
                textLabel.setText("Tenses: Past Simple");
                break;
            case 6:
                textLabel.setText("Tenses: Future Simple");
                break;
        }

        fragTenses = new TensesFragment();
        fragTenses.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragTenses)
                .commit();

    }

    protected void setFragResult(String task1UserRes, String task2UserRes, String task3UserRes, String task4UserRes, String task5UserRes,
                                 String task1TrueRes, String task2TrueRes, String task3TrueRes, String task4TrueRes, String task5TrueRes,
                                 int completed) {

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

        fragResult.setArguments(testArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragResult)
                .commit();

    }

    private void startTensesTest() {

        Bundle arguments = getIntent().getExtras();
        testType = arguments.getInt("testType");

        fragTensesTest = new TensesTestFragment();
        fragTensesTest.setArguments(arguments);

        relativeLayout.setVisibility(View.GONE);
        card1.setVisibility(View.GONE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragTensesTest)
                .commit();
    }

    public void onClickNext(View view) {

        if (!wasOnTest) {
            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение продолжения.")
                    .setMessage("Вы уверены что хотите продолжить?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startTensesTest();
                            btnContinue.setClickable(false);
                            btnContinue.setVisibility(View.INVISIBLE);
                            wasOnTest = true;
                        }
                    }).create().show();
        } else if (wasOnTest) {
            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение продолжения.")
                    .setMessage("Вы уверены что хотите продолжить?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startTensesTest();
                            //btnContinue.setClickable(false);
                            //btnContinue.setVisibility(View.INVISIBLE);
                            //wasOnTest = true;

                        }
                    }).create().show();
        }

    }

    protected void finishActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Курс ещё не окончен!")
                .setMessage("Вы действительно хотите покинуть курс не закончив его?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        TensesActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}