package com.hg39.helleng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;

public class TensesTestFragment extends Fragment {

    TextView ruleTextView,textViewHint,resultView;
    Button btnEnd;
    TextView textViewResult;
    TextView questionTextView1,questionTextView2,questionTextView3,
            questionTextViewAfter1,questionTextViewAfter2,questionTextViewAfter3;
    TextView questionTextView4,questionTextView5;
    EditText etAnswer4,etAnswer5;

    AutoCompleteTextView dropDownMenu1;
    AutoCompleteTextView dropDownMenu2;
    AutoCompleteTextView dropDownMenu3;

    String[] slot1;
    String[] slot2;
    String[] slot3;

    String slot1Res;
    String slot2Res;
    String slot3Res;
    String slot4Res;
    String slot5Res;

    String questions1,questions2,questions3,questions4,questions5,questions6,questions7,questions8,questions9,questions10;
    String question1After,question2After,question3After;
    String answers1,answers2,answers3,answers4,answers5,answers6,answers7,answers8,answers9,answers10;
    //String rule;

    private int currentAnswer = 1;
    private int completed,testType;
    private int testsStarted;
    private String completedString;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference tests;

    User user = new User();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        tests = database.getReference("Tests");
        users = database.getReference("Users");
        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.setTestsStarted(snapshot.child("testsStarted").getValue(int.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        testType = getArguments().getInt("testType");

        slot1 = new String[2];
        slot2 = new String[2];
        slot3 = new String[2];

        switch (testType) {
            case 4:

                tests.child("Tenses").child("Present Simple").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        /*questions1 = snapshot.child("Questions").child("0").getValue(String.class);
                        questions2 = snapshot.child("Questions").child("1").getValue(String.class);
                        questions3 = snapshot.child("Questions").child("2").getValue(String.class);
                        questions4 = snapshot.child("Questions").child("3").getValue(String.class);
                        questions5 = snapshot.child("Questions").child("4").getValue(String.class);
                        questions6 = snapshot.child("Questions").child("5").getValue(String.class);
                        questions7 = snapshot.child("Questions").child("6").getValue(String.class);
                        questions8 = snapshot.child("Questions").child("7").getValue(String.class);
                        questions9 = snapshot.child("Questions").child("8").getValue(String.class);
                        questions10 = snapshot.child("Questions").child("9").getValue(String.class);

                        answers1 = snapshot.child("Answers").child("0").getValue(int.class);
                        answers2 = snapshot.child("Answers").child("1").getValue(int.class);
                        answers3 = snapshot.child("Answers").child("2").getValue(int.class);
                        answers4 = snapshot.child("Answers").child("3").getValue(int.class);
                        answers5 = snapshot.child("Answers").child("4").getValue(int.class);
                        answers6 = snapshot.child("Answers").child("5").getValue(int.class);
                        answers7 = snapshot.child("Answers").child("6").getValue(int.class);
                        answers8 = snapshot.child("Answers").child("7").getValue(int.class);
                        answers9 = snapshot.child("Answers").child("8").getValue(int.class);
                        answers10 = snapshot.child("Answers").child("9").getValue(int.class);*/

                        //rule = snapshot.child("Rules").child("rule1").getValue(String.class);
                        updateUI();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

            case 5:
                tests.child("Tenses").child("Past Simple").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        /*questions1 = snapshot.child("Questions").child("0").getValue(String.class);
                        questions2 = snapshot.child("Questions").child("1").getValue(String.class);
                        questions3 = snapshot.child("Questions").child("2").getValue(String.class);
                        questions4 = snapshot.child("Questions").child("3").getValue(String.class);
                        questions5 = snapshot.child("Questions").child("4").getValue(String.class);
                        questions6 = snapshot.child("Questions").child("5").getValue(String.class);
                        questions7 = snapshot.child("Questions").child("6").getValue(String.class);
                        questions8 = snapshot.child("Questions").child("7").getValue(String.class);
                        questions9 = snapshot.child("Questions").child("8").getValue(String.class);
                        questions10 = snapshot.child("Questions").child("9").getValue(String.class);

                        answers1 = snapshot.child("Answers").child("0").getValue(int.class);
                        answers2 = snapshot.child("Answers").child("1").getValue(int.class);
                        answers3 = snapshot.child("Answers").child("2").getValue(int.class);
                        answers4 = snapshot.child("Answers").child("3").getValue(int.class);
                        answers5 = snapshot.child("Answers").child("4").getValue(int.class);
                        answers6 = snapshot.child("Answers").child("5").getValue(int.class);
                        answers7 = snapshot.child("Answers").child("6").getValue(int.class);
                        answers8 = snapshot.child("Answers").child("7").getValue(int.class);
                        answers9 = snapshot.child("Answers").child("8").getValue(int.class);
                        answers10 = snapshot.child("Answers").child("9").getValue(int.class);*/

                        updateUI();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

            case 6:
                tests.child("Tenses").child("Future Simple").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        questions1 = snapshot.child("Questions").child("0").getValue(String.class);
                        questions2 = snapshot.child("Questions").child("1").getValue(String.class);
                        questions3 = snapshot.child("Questions").child("2").getValue(String.class);
                        questions4 = snapshot.child("Questions").child("3").getValue(String.class);
                        questions5 = snapshot.child("Questions").child("4").getValue(String.class);

                        question1After = snapshot.child("Questions").child("After").child("0").getValue(String.class);
                        question2After = snapshot.child("Questions").child("After").child("1").getValue(String.class);;
                        question3After = snapshot.child("Questions").child("After").child("2").getValue(String.class);;

                        answers1 = snapshot.child("Answers").child("0").child("0").getValue(String.class);
                        answers2 = snapshot.child("Answers").child("1").child("0").getValue(String.class);
                        answers3 = snapshot.child("Answers").child("2").child("1").getValue(String.class);
                        answers4 = snapshot.child("Answers").child("3").getValue(String.class);
                        answers5 = snapshot.child("Answers").child("4").getValue(String.class);

                        slot1[0] = snapshot.child("Answers").child("0").child("0").getValue(String.class);
                        slot1[1] = snapshot.child("Answers").child("0").child("1").getValue(String.class);

                        slot2[0] = snapshot.child("Answers").child("1").child("0").getValue(String.class);
                        slot2[1] = snapshot.child("Answers").child("1").child("1").getValue(String.class);

                        slot3[0] = snapshot.child("Answers").child("2").child("0").getValue(String.class);
                        slot3[1] = snapshot.child("Answers").child("2").child("1").getValue(String.class);

                        setArrays();

                        updateUI();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        user.setTestsStarted(user.getTestsStarted() + 1);
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("testsStarted").setValue(user.getTestsStarted());

        View rootView =
                inflater.inflate(R.layout.fragment_tenses_test,container,false);

        dropDownMenu1 = rootView.findViewById(R.id.dropdown_menu1);
        dropDownMenu2 = rootView.findViewById(R.id.dropdown_menu2);
        dropDownMenu3 = rootView.findViewById(R.id.dropdown_menu3);

        btnEnd = rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(this::onClickEnd);

        textViewResult = rootView.findViewById(R.id.textViewCompleted);

        resultView = rootView.findViewById(R.id.resultView);

        etAnswer4 = rootView.findViewById(R.id.etAnswer4);
        etAnswer5 = rootView.findViewById(R.id.etAnswer5);

        questionTextView1 = rootView.findViewById(R.id.questionTextView1);
        questionTextView2 = rootView.findViewById(R.id.questionTextView2);
        questionTextView3 = rootView.findViewById(R.id.questionTextView3);
        questionTextView4 = rootView.findViewById(R.id.questionTextView4);
        questionTextView5 = rootView.findViewById(R.id.questionTextView5);

        questionTextViewAfter1 = rootView.findViewById(R.id.questionTextViewAfter1);
        questionTextViewAfter2 = rootView.findViewById(R.id.questionTextViewAfter2);
        questionTextViewAfter3 = rootView.findViewById(R.id.questionTextViewAfter3);

        ruleTextView = rootView.findViewById(R.id.ruleTextView);
        textViewHint = rootView.findViewById(R.id.textViewHint);

        //Слушатели для первых для трёх полей
        dropDownMenu1.setOnItemClickListener((parent, view, position, id) -> slot1Res = (String)parent.getItemAtPosition(position));
        dropDownMenu2.setOnItemClickListener((parent, view, position, id) -> slot2Res = (String)parent.getItemAtPosition(position));
        dropDownMenu3.setOnItemClickListener((parent, view, position, id) -> slot3Res = (String)parent.getItemAtPosition(position));

        if (testType == 4) {
            presentSimpleTest();
        } else if (testType == 5) {
            pastSimpleTest();
        } else if (testType == 6) {
            futureSimpleTest();
        }

        return rootView;
    }

    private void setArrays() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, slot1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu1.setAdapter(adapter1);
        //dropDownMenu1.setSelection(2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, slot2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu2.setAdapter(adapter2);
        //dropDownMenu2.setSelection(2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, slot3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu3.setAdapter(adapter3);

    }

    private void updateUI() {

        questionTextView1.setText(questions1);questionTextViewAfter1.setText(question1After);
        questionTextView2.setText(questions2);questionTextViewAfter2.setText(question2After);
        questionTextView3.setText(questions3);questionTextViewAfter3.setText(question3After);
        questionTextView4.setText(questions4);
        questionTextView5.setText(questions5);

    }

    private void presentSimpleTest() {
        updateUI();
    }


    private void onClickEnd(View view) {

        slot4Res = etAnswer4.getText().toString();
        slot5Res = etAnswer5.getText().toString();

        //КостыльTechnologies
        if (slot1Res == null) {
            slot1Res = " ";
        }
        if (slot2Res == null) {
            slot2Res = " ";
        }
        if (slot3Res == null) {
            slot3Res = " ";
        }
        if (slot4Res == null) {
            slot4Res = " ";
        }
        if (slot5Res == null) {
            slot5Res = " ";
        }
        //Cringe

        if (slot1Res.equals(answers1)) {
            completed+=10;
        }
        if (slot2Res.equals(answers2)) {
            completed+=10;
        }
        if (slot3Res.equals(answers3)) {
            completed+=10;
        }
        if (slot4Res.equals(answers4)) {
            completed+=10;
        }
        if (slot5Res.equals(answers5)) {
            completed+=10;
        }

        ((TensesActivity)getContext()).setFragResult(slot1Res,slot2Res,slot3Res,slot4Res,slot5Res,
                                                        answers1,answers2,answers3,answers4,answers5,
                                                        completed);

    }

    private void saver() {
        //Intent intent = new Intent(getActivity(),SaverActivity.class);
        switch (testType) {
            case 4:
                user.setTest4Interest(completed);
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test4Interest").setValue(user.getTest4Interest());
                //intent.putExtra("result4", completed);
                break;
            case 5:
                user.setTest5Interest(completed);
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test5Interest").setValue(user.getTest5Interest());
                //intent.putExtra("result5", completed);
                break;
            case 6:
                user.setTest6Interest(completed);
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test6Interest").setValue(user.getTest6Interest());
                //intent.putExtra("result6", completed);
                break;
            default:
                Toast.makeText(getActivity(), "Error",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        //intent.putExtra("testType",testType);
        //startActivity(intent);
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }

    private void pastSimpleTest() {
        updateUI();
    }

    private void futureSimpleTest() {
        updateUI();
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
            /*case R.id.btn1:
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
                break;*/
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
