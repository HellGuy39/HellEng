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
                textLabel.setText("Words: furniture");
                break;
            case 2:
                textLabel.setText("Words: school supplies");
                break;
            case 3:
                textLabel.setText("Words: food");
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
}