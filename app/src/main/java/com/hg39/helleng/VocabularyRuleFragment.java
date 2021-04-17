package com.hg39.helleng;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class VocabularyRuleFragment extends Fragment {

    private TextView word1,word2,word3,word4,word5,word6,word7,word8,word9,word10;
    private String[] wordsArr;

    FirebaseDatabase database;
    DatabaseReference tests;

    com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton fltStartTest;

    String testName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordsArr = new String[10];

        database = FirebaseDatabase.getInstance();
        tests = database.getReference("Tests");

        testName = getArguments().getString("testName","null");

        setWordsArr(testName);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_vocabulary_rule,container,false);

        word1 = rootView.findViewById(R.id.word1);
        word2 = rootView.findViewById(R.id.word2);
        word3 = rootView.findViewById(R.id.word3);
        word4 = rootView.findViewById(R.id.word4);
        word5 = rootView.findViewById(R.id.word5);
        word6 = rootView.findViewById(R.id.word6);
        word7 = rootView.findViewById(R.id.word7);
        word8 = rootView.findViewById(R.id.word8);
        word9 = rootView.findViewById(R.id.word9);
        word10 = rootView.findViewById(R.id.word10);

        fltStartTest = rootView.findViewById(R.id.fltStartTest);
        fltStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                        .setTitle("Continuation confirmation")
                        .setMessage("Are you sure want to continue?")
                        .setNegativeButton(android.R.string.no,null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((VocabularyActivity)getContext()).setFragVocabularyTest();
                            }
                        }).create().show();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //updateUI();
    }

    private void updateUI() {
        word1.setText(wordsArr[0]);
        word2.setText(wordsArr[1]);
        word3.setText(wordsArr[2]);
        word4.setText(wordsArr[3]);
        word5.setText(wordsArr[4]);
        word6.setText(wordsArr[5]);
        word7.setText(wordsArr[6]);
        word8.setText(wordsArr[7]);
        word9.setText(wordsArr[8]);
        word10.setText(wordsArr[9]);
    }

    private void setWordsArr(String testName) {

        tests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wordsArr[0] = snapshot.child("Words").child(testName).child("Transcription").child("0").getValue(String.class);
                wordsArr[1] = snapshot.child("Words").child(testName).child("Transcription").child("1").getValue(String.class);
                wordsArr[2] = snapshot.child("Words").child(testName).child("Transcription").child("2").getValue(String.class);
                wordsArr[3] = snapshot.child("Words").child(testName).child("Transcription").child("3").getValue(String.class);
                wordsArr[4] = snapshot.child("Words").child(testName).child("Transcription").child("4").getValue(String.class);
                wordsArr[5] = snapshot.child("Words").child(testName).child("Transcription").child("5").getValue(String.class);
                wordsArr[6] = snapshot.child("Words").child(testName).child("Transcription").child("6").getValue(String.class);
                wordsArr[7] = snapshot.child("Words").child(testName).child("Transcription").child("7").getValue(String.class);
                wordsArr[8] = snapshot.child("Words").child(testName).child("Transcription").child("8").getValue(String.class);
                wordsArr[9] = snapshot.child("Words").child(testName).child("Transcription").child("9").getValue(String.class);

                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
