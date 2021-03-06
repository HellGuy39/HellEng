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

public class TensesActivity extends AppCompatActivity {

    private Button btnContinue;
    private boolean wasOnTest;
    private TextView textLabel;
    private int testType;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenses);

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

        Fragment fragTenses = new TensesFragment();
        fragTenses.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragTenses)
                .commit();

    }
    private void startTensesTest() {

        Bundle arguments = getIntent().getExtras();
        testType = arguments.getInt("testType");

        Fragment fragTensesTest = new TensesTestFragment();
        fragTensesTest.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tn, fragTensesTest)
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
                        startTensesTest();
                        btnContinue.setClickable(false); btnContinue.setVisibility(View.INVISIBLE);
                        wasOnTest = true;
                    }
                }).create().show();

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