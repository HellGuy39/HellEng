package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

import static com.hg39.helleng.MainActivity.food;
import static com.hg39.helleng.MainActivity.furniture;
import static com.hg39.helleng.MainActivity.groupVocabulary;
import static com.hg39.helleng.MainActivity.schoolSupplies;

public class VocabularyTestFragment extends Fragment {

    Button btnNext;
    TextView wordView,taskView;
    EditText editTextField;
    //int words;
    int completed;
    String completedString;
    //String userRes1,userRes2,userRes3,userRes4,userRes5,userRes6,userRes7,userRes8,userRes9,userRes10;
    //String currentAnswer;
    //String word1,word2,word3,word4,word5,word6,word7,word8,word9,word10;
    //String word1Ru,word2Ru,word3Ru,word4Ru,word5Ru,word6Ru,word7Ru,word8Ru,word9Ru,word10Ru;
    String testName;

    String[] wordArr, wordRuArr, userResArr;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference tests;
    DatabaseReference users;

    boolean first;

    TestProgressControl testProgressControl = new TestProgressControl();

    int i;

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wordArr = new String[10];
        wordRuArr = new String[10];
        userResArr = new String[10];

        testName = getArguments().getString("testName");

        database = FirebaseDatabase.getInstance();
        tests = database.getReference().child("Tests").child("Words");

        first = false;

        setWordToTest(testName);

        i = 0;

        //words = 0;
        completed = 0;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_vocabulary_test,container,false);

        btnNext = rootView.findViewById(R.id.btnNext);
        editTextField = rootView.findViewById(R.id.editTextField);
        wordView = rootView.findViewById(R.id.wordView);
        taskView = rootView.findViewById(R.id.textViewTask);

        btnNext.setOnClickListener(this::onClickBtnNext);
        editTextField.setEnabled(false);

        taskView.setText("You will see words in Russian, write them in English");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        wordView.setText(wordRuArr[i]);

        //Boyo!
        //wordView.setText(word1Ru);
        editTextField.setEnabled(true);
        //words = 1;
        btnNext.setText("Next");

    }

    @Override
    public void onResume() {
        super.onResume();

        //wordView.setText(word1Ru);

    }

    protected void onClickBtnNext(View view) {

        if (i == 9) {
            //userResArr[i] = editTextField.getText().toString();
            //Toast.makeText(getContext(), "That's it!", Toast.LENGTH_SHORT).show();

            if (editTextField.getText().toString().equals("")) { return; }

            userResArr[i] = editTextField.getText().toString();
            userResArr[i] = userResArr[i].trim();

            if (userResArr[i].equalsIgnoreCase(wordArr[i])) {
                completed += 10;
            }

            completedString = Integer.toString(completed);
            testProgressControl.SaveTestProgress(groupVocabulary,testName,completedString);

            System.out.println(testName);
            ((VocabularyActivity)getContext()).setFragResult(userResArr[0],userResArr[1],userResArr[2],userResArr[3],userResArr[4],userResArr[5],userResArr[6],userResArr[7],userResArr[8],userResArr[9],
                                                            wordArr[0],wordArr[1],wordArr[2],wordArr[3],wordArr[4],wordArr[5],wordArr[6],wordArr[7],wordArr[8],wordArr[9],
                                                            completed,10,testName);

            return;
        }

        if (editTextField.getText().toString().equals("")) { return; }

        userResArr[i] = editTextField.getText().toString();
        userResArr[i] = userResArr[i].trim();

        if (userResArr[i].equalsIgnoreCase(wordArr[i])) {
            completed += 1;//10;
        }

        editTextField.setText(null);

        i++;
        if (i < 10) {
            wordView.setText(wordRuArr[i]);
        }

        }

    private void setWordToTest(String testName) {
        tests.child(testName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wordArr[0] = snapshot.child("ENG").child("0").getValue(String.class);
                wordArr[1] = snapshot.child("ENG").child("1").getValue(String.class);
                wordArr[2] = snapshot.child("ENG").child("2").getValue(String.class);
                wordArr[3] = snapshot.child("ENG").child("3").getValue(String.class);
                wordArr[4] = snapshot.child("ENG").child("4").getValue(String.class);
                wordArr[5] = snapshot.child("ENG").child("5").getValue(String.class);
                wordArr[6] = snapshot.child("ENG").child("6").getValue(String.class);
                wordArr[7] = snapshot.child("ENG").child("7").getValue(String.class);
                wordArr[8] = snapshot.child("ENG").child("8").getValue(String.class);
                wordArr[9] = snapshot.child("ENG").child("9").getValue(String.class);

                wordRuArr[0] = snapshot.child("RU").child("0").getValue(String.class);
                wordRuArr[1] = snapshot.child("RU").child("1").getValue(String.class);
                wordRuArr[2] = snapshot.child("RU").child("2").getValue(String.class);
                wordRuArr[3] = snapshot.child("RU").child("3").getValue(String.class);
                wordRuArr[4] = snapshot.child("RU").child("4").getValue(String.class);
                wordRuArr[5] = snapshot.child("RU").child("5").getValue(String.class);
                wordRuArr[6] = snapshot.child("RU").child("6").getValue(String.class);
                wordRuArr[7] = snapshot.child("RU").child("7").getValue(String.class);
                wordRuArr[8] = snapshot.child("RU").child("8").getValue(String.class);
                wordRuArr[9] = snapshot.child("RU").child("9").getValue(String.class);

                if (!first) {
                    first = true;
                    wordView.setText(wordRuArr[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
