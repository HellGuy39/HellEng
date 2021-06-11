package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.hg39.helleng.MainActivity.futureSimple;
import static com.hg39.helleng.MainActivity.groupGrammar;
import static com.hg39.helleng.MainActivity.pastSimple;
import static com.hg39.helleng.MainActivity.presentSimple;

public class GrammarTestFragment extends Fragment {
    TextView ruleTextView,textViewHint1,textViewHint2;
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

    String questions1,questions2,questions3,questions4,questions5;
    String question1After,question2After,question3After;
    String answers1,answers2,answers3,answers4,answers5;
    String hint1,hint2;

    //private int currentAnswer = 1;
    int completed;
    String testName;
    //private int testsStarted;
    String completedString;

    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference tests;

    TestProgressControl testProgressControl = new TestProgressControl();

    ValueEventListener loadData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        tests = database.getReference("Tests");
        users = database.getReference("Users");

        assert getArguments() != null;
        testName = getArguments().getString("testName", "null");

        slot1 = new String[3];
        slot2 = new String[3];
        slot3 = new String[3];

        loadData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                questions1 = snapshot.child("Questions").child("0").getValue(String.class);
                questions2 = snapshot.child("Questions").child("1").getValue(String.class);
                questions3 = snapshot.child("Questions").child("2").getValue(String.class);
                questions4 = snapshot.child("Questions").child("3").getValue(String.class);
                questions5 = snapshot.child("Questions").child("4").getValue(String.class);

                question1After = snapshot.child("Questions").child("After").child("0").getValue(String.class);
                question2After = snapshot.child("Questions").child("After").child("1").getValue(String.class);
                question3After = snapshot.child("Questions").child("After").child("2").getValue(String.class);

                slot1[0] = snapshot.child("Answers").child("0").child("0").getValue(String.class);
                slot1[1] = snapshot.child("Answers").child("0").child("1").getValue(String.class);

                slot2[0] = snapshot.child("Answers").child("1").child("0").getValue(String.class);
                slot2[1] = snapshot.child("Answers").child("1").child("1").getValue(String.class);

                slot3[0] = snapshot.child("Answers").child("2").child("0").getValue(String.class);
                slot3[1] = snapshot.child("Answers").child("2").child("1").getValue(String.class);

                hint1 = snapshot.child("Hint").child("0").getValue(String.class);
                hint2 = snapshot.child("Hint").child("1").getValue(String.class);

                switch (testName) {
                    case pastSimple:

                        answers1 = snapshot.child("Answers").child("0").child("1").getValue(String.class);
                        answers2 = snapshot.child("Answers").child("1").child("0").getValue(String.class);
                        answers3 = snapshot.child("Answers").child("2").child("1").getValue(String.class);
                        answers4 = snapshot.child("Answers").child("3").getValue(String.class);
                        answers5 = snapshot.child("Answers").child("4").getValue(String.class);

                        slot1[2] = " ";

                        slot2[2] = snapshot.child("Answers").child("1").child("2").getValue(String.class);

                        slot3[2] = snapshot.child("Answers").child("2").child("2").getValue(String.class);

                        break;
                    case presentSimple:

                        answers1 = snapshot.child("Answers").child("0").child("0").getValue(String.class);
                        answers2 = snapshot.child("Answers").child("1").child("1").getValue(String.class);
                        answers3 = snapshot.child("Answers").child("2").child("2").getValue(String.class);
                        answers4 = snapshot.child("Answers").child("3").getValue(String.class);
                        answers5 = snapshot.child("Answers").child("4").getValue(String.class);

                        slot1[2] = snapshot.child("Answers").child("0").child("2").getValue(String.class);

                        slot2[2] = snapshot.child("Answers").child("1").child("2").getValue(String.class);

                        slot3[2] = snapshot.child("Answers").child("2").child("2").getValue(String.class);

                        break;
                    case futureSimple:
                        answers1 = snapshot.child("Answers").child("0").child("0").getValue(String.class);
                        answers2 = snapshot.child("Answers").child("1").child("0").getValue(String.class);
                        answers3 = snapshot.child("Answers").child("2").child("1").getValue(String.class);
                        answers4 = snapshot.child("Answers").child("3").getValue(String.class);
                        answers5 = snapshot.child("Answers").child("4").getValue(String.class);

                        slot1[2] = " ";

                        slot2[2] = " ";

                        slot3[2] = " ";
                        break;
                }

                updateUI();
                setArrays();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_grammar_test,container,false);

        dropDownMenu1 = rootView.findViewById(R.id.dropdown_menu1);
        dropDownMenu2 = rootView.findViewById(R.id.dropdown_menu2);
        dropDownMenu3 = rootView.findViewById(R.id.dropdown_menu3);

        btnEnd = rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(this::onClickEnd);

        textViewResult = rootView.findViewById(R.id.textViewCompleted);

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
        textViewHint1 = rootView.findViewById(R.id.textViewHint1);
        textViewHint2 = rootView.findViewById(R.id.textViewHint2);

        //Слушатели для первых для трёх полей
        dropDownMenu1.setOnItemClickListener((parent, view, position, id) -> slot1Res = (String)parent.getItemAtPosition(position));
        dropDownMenu2.setOnItemClickListener((parent, view, position, id) -> slot2Res = (String)parent.getItemAtPosition(position));
        dropDownMenu3.setOnItemClickListener((parent, view, position, id) -> slot3Res = (String)parent.getItemAtPosition(position));

        //updateUI();

        tests.child("Tenses").child(testName).addValueEventListener(loadData);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //updateUI();
    }

    private void setArrays() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, slot1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, slot2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, slot3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu3.setAdapter(adapter3);

    }

    private void updateUI() {

        questionTextView1.setText(questions1);questionTextViewAfter1.setText(question1After);
        questionTextView2.setText(questions2);questionTextViewAfter2.setText(question2After);
        questionTextView3.setText(questions3);questionTextViewAfter3.setText(question3After);
        questionTextView4.setText(questions4);
        questionTextView5.setText(questions5);

        textViewHint1.setText(hint1);
        textViewHint2.setText(hint2);

        tests.child("Tenses").child(testName).removeEventListener(loadData);

    }

    private void onClickEnd(View view) {

        slot4Res = etAnswer4.getText().toString();
        slot5Res = etAnswer5.getText().toString();

        //КостыльTechnologies
        if (slot1Res == null)
        {
            slot1Res = " ";
        }
        if (slot2Res == null)
        {
            slot2Res = " ";
        }
        if (slot3Res == null)
        {
            slot3Res = " ";
        }
        if (slot4Res == null)
        {
            slot4Res = " ";
        }
        if (slot5Res == null)
        {
            slot5Res = " ";
        }
        //Cringe

        if (slot1Res.equalsIgnoreCase(answers1))
        {
            completed+=20;
        }
        if (slot2Res.equalsIgnoreCase(answers2))
        {
            completed+=20;
        }
        if (slot3Res.equalsIgnoreCase(answers3))
        {
            completed+=20;
        }
        if (slot4Res.equalsIgnoreCase(answers4))
        {
            completed+=20;
        }
        if (slot5Res.equalsIgnoreCase(answers5))
        {
            completed+=20;
        }

        completedString = Integer.toString(completed);
        testProgressControl.SaveTestProgress(groupGrammar,testName,completedString);

        ((GrammarActivity) requireContext()).setFragResult(slot1Res,slot2Res,slot3Res,slot4Res,slot5Res,
                                                        answers1,answers2,answers3,answers4,answers5,
                                                        completed, 5,testName);

    }
}
