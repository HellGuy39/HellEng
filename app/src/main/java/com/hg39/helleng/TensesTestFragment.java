package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hg39.helleng.Models.User;

public class TensesTestFragment extends Fragment {

    private TextView ruleTextView,textViewHint,questionTextView,resultView;
    private Button btn1,btn2,btn3,btnEnd;
    private int currentAnswer = 1;
    private int completed,testType;
    private String completedString;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    User user = new User();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        View rootView =
                inflater.inflate(R.layout.fragment_tenses_test,container,false);
        btn1 = rootView.findViewById(R.id.btn1);
        btn2 = rootView.findViewById(R.id.btn2);
        btn3 = rootView.findViewById(R.id.btn3);
        btnEnd = rootView.findViewById(R.id.btnEnd);
        resultView = rootView.findViewById(R.id.resultView);
        questionTextView = rootView.findViewById(R.id.questionTextView);
        ruleTextView = rootView.findViewById(R.id.ruleTextView);
        textViewHint = rootView.findViewById(R.id.textViewHint);
        btn1.setOnClickListener(this::onClickAnswerButtons);
        btn2.setOnClickListener(this::onClickAnswerButtons);
        btn3.setOnClickListener(this::onClickAnswerButtons);
        btnEnd.setOnClickListener(this::onClickEnd);

        btnEnd.setVisibility(View.GONE);

        testType = getArguments().getInt("testType");

        switch (testType) {
            case 4:
                presentSimpleTest();
                break;
            case 5:
                pastSimpleTest();
                break;
            case 6:
                futureSimpleTest();
                break;
        }
        return rootView;
    }

    private void presentSimpleTest() {
        switch (currentAnswer) {
            case 1:
                textViewHint.setText("Вставьте глагол to be в Present Simple.");
                questionTextView.setText("I ___ Andrey");
                break;
            case 2:
                questionTextView.setText("He ___ happy");
                break;
            case 3:
                questionTextView.setText("We ___ friends");
                break;
            case 4:
                questionTextView.setText("It ___ broken");
                break;
            case 5:
                questionTextView.setText("What ___ your name?");
                break;
            case 6:
                questionTextView.setText("How ___ you?");
                break;
            case 7:
                questionTextView.setText("___ you sure?");
                break;
            case 8:
                btn1.setText("Do");
                btn2.setText("Does");
                btn3.setVisibility(View.GONE);
                textViewHint.setText("Вставьте DO / DOES в вопросительное предложение.");
                questionTextView.setText("_____ you go to school on Sundays?");
                break;
            case 9:
                questionTextView.setText("_____ Tom drive his car well?");
                break;
            case 10:
                questionTextView.setText("_____ they go to play football?");
                break;
            case 11:
                completedString = Integer.toString(completed);
                resultView.setText("You completed\n" + completedString + "%");
                questionTextView.setText(" ");
                btn1.setVisibility(View.GONE);btn2.setVisibility(View.GONE);
                btnEnd.setVisibility(View.VISIBLE);

                break;
            default:

                break;
        }
    }

    private void onClickEnd(View view) {
        saver();
    }

    private void saver() {
        Intent intent = new Intent(getActivity(),SaverActivity.class);
        switch (testType) {
            case 4:
                user.setTest4Interest(completed);
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test4Interest").setValue(user.getTest1Interest());
                intent.putExtra("result4", completed);
                break;
            case 5:
                user.setTest5Interest(completed);
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test5Interest").setValue(user.getTest1Interest());
                intent.putExtra("result5", completed);
                break;
            case 6:
                user.setTest6Interest(completed);
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test6Interest").setValue(user.getTest1Interest());
                intent.putExtra("result6", completed);
                break;
            default:
                Toast.makeText(getActivity(), "Error",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        intent.putExtra("testType",testType);
        startActivity(intent);
    }

    private void pastSimpleTest() {

    }

    private void futureSimpleTest() {

    }

    private void onClickAnswerButtons(View view) {
        switch (view.getId()) {
            /*
             * 1 - AM
             * 2 - IS
             * 3 - ARE
             *
             * 1 - DO
             * 2 - DOES
             *
             */
            case R.id.btn1:
                if (currentAnswer == 1) { completed += 10;}
                if (currentAnswer == 8) { completed += 10;}
                if (currentAnswer == 10) { completed += 10;}
                break;
            case R.id.btn2:
                if (currentAnswer == 2) { completed += 10;}
                if (currentAnswer == 4) { completed += 10;}
                if (currentAnswer == 5) { completed += 10;}
                if (currentAnswer == 9) { completed += 10;}
                break;
            case R.id.btn3:
                if (currentAnswer == 3) { completed += 10;}
                if (currentAnswer == 6) { completed += 10;}
                if (currentAnswer == 7) { completed += 10;}
                break;
        }
        currentAnswer++;
        switch (testType) {
            case 4:
                presentSimpleTest();
                break;
            case 5:
                pastSimpleTest();
                break;
            case 6:
                futureSimpleTest();
                break;
        }

    }
}
