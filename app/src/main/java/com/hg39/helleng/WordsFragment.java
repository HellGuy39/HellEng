package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

public class WordsFragment extends Fragment {

    private TextView word1,word2,word3,word4,word5,word6,word7,word8,word9,word10;
    private String[] wordsArr;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference tests;
    DatabaseReference users;
    FirebaseUser userF;

    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordsArr = new String[10];

        database = FirebaseDatabase.getInstance();
        tests = database.getReference("Tests");

        int testType = getArguments().getInt("testType");

        setWordsArr(testType);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        users = database.getReference("Users");

        View rootView =
                inflater.inflate(R.layout.fragment_words,container,false);

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

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
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

    private void setWordsArr(int testType) {
        if (testType == 1) {
            tests.child("Words").child("Furniture").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    wordsArr[0] = snapshot.child("Transcription").child("0").getValue(String.class);
                    wordsArr[1] = snapshot.child("Transcription").child("1").getValue(String.class);
                    wordsArr[2] = snapshot.child("Transcription").child("2").getValue(String.class);
                    wordsArr[3] = snapshot.child("Transcription").child("3").getValue(String.class);
                    wordsArr[4] = snapshot.child("Transcription").child("4").getValue(String.class);
                    wordsArr[5] = snapshot.child("Transcription").child("5").getValue(String.class);
                    wordsArr[6] = snapshot.child("Transcription").child("6").getValue(String.class);
                    wordsArr[7] = snapshot.child("Transcription").child("7").getValue(String.class);
                    wordsArr[8] = snapshot.child("Transcription").child("8").getValue(String.class);
                    wordsArr[9] = snapshot.child("Transcription").child("9").getValue(String.class);
                    updateUI();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(),"Failed to update data.", Toast.LENGTH_SHORT).show();
                }
            });

            /*wordsArr[0] = "Armchair [ ˈɑːmˈʧɛə ] кресло";
            wordsArr[1] = "Bed [ bed ] кровать";
            wordsArr[2] = "Bench [ benʧ ] скамья";
            wordsArr[3] = "Blanket [ ˈblæŋkɪt ] одеяло";
            wordsArr[4] = "Bookshelf [ ˈbʊkʃelf ] книжная полка";
            wordsArr[5] = "Bookcase [ ˈbʊkkeɪs ] книжный шкаф";
            wordsArr[6] = "Cabinet [ ˈkæbɪnɪt ] шкаф";
            wordsArr[7] = "Carpet [ ˈkɑːpɪt ] ковер";
            wordsArr[8] = "Chair [ ʧɛə ] стул";
            wordsArr[9] = "Lamp [ læmp ] лампа";*/
        }
        if (testType == 2) {

            tests.child("Words").child("School supplies").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    wordsArr[0] = snapshot.child("Transcription").child("0").getValue(String.class);
                    wordsArr[1] = snapshot.child("Transcription").child("1").getValue(String.class);
                    wordsArr[2] = snapshot.child("Transcription").child("2").getValue(String.class);
                    wordsArr[3] = snapshot.child("Transcription").child("3").getValue(String.class);
                    wordsArr[4] = snapshot.child("Transcription").child("4").getValue(String.class);
                    wordsArr[5] = snapshot.child("Transcription").child("5").getValue(String.class);
                    wordsArr[6] = snapshot.child("Transcription").child("6").getValue(String.class);
                    wordsArr[7] = snapshot.child("Transcription").child("7").getValue(String.class);
                    wordsArr[8] = snapshot.child("Transcription").child("8").getValue(String.class);
                    wordsArr[9] = snapshot.child("Transcription").child("9").getValue(String.class);
                    updateUI();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(),"Failed to update data.", Toast.LENGTH_SHORT).show();
                }
            });

            /*wordsArr[0] = "Pencil case [ pɛnsl keɪs ] пенал";
            wordsArr[1] = "Backpack [ˈbækˌpæk ] рюкзак";
            wordsArr[2] = "Clip [ klɪp ] скрепка";
            wordsArr[3] = "Pencil [ ˈpɛnsl ] карандаш";
            wordsArr[4] = "Ballpoint [ ˈbɔːlˌpɔɪnt ] шариковая ручка";
            wordsArr[5] = "Eraser [ ɪˈreɪzə ] ластик";
            wordsArr[6] = "Ruler [ ˈruːlə ] линейка";
            wordsArr[7] = "Blackboard [ ˈblækbɔːd ] доска";
            wordsArr[8] = "Book [ bʊk ] книга";
            wordsArr[9] = "Stapler [ ˈsteɪplə ] степлер";*/
        }
        if (testType == 3) {

            tests.child("Words").child("Food").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    wordsArr[0] = snapshot.child("Transcription").child("0").getValue(String.class);
                    wordsArr[1] = snapshot.child("Transcription").child("1").getValue(String.class);
                    wordsArr[2] = snapshot.child("Transcription").child("2").getValue(String.class);
                    wordsArr[3] = snapshot.child("Transcription").child("3").getValue(String.class);
                    wordsArr[4] = snapshot.child("Transcription").child("4").getValue(String.class);
                    wordsArr[5] = snapshot.child("Transcription").child("5").getValue(String.class);
                    wordsArr[6] = snapshot.child("Transcription").child("6").getValue(String.class);
                    wordsArr[7] = snapshot.child("Transcription").child("7").getValue(String.class);
                    wordsArr[8] = snapshot.child("Transcription").child("8").getValue(String.class);
                    wordsArr[9] = snapshot.child("Transcription").child("9").getValue(String.class);
                    updateUI();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(),"Failed to update data.", Toast.LENGTH_SHORT).show();
                }
            });

            /*wordsArr[0] = "Cheese [ ʧiːz ] сыр";
            wordsArr[1] = "Doughnut / Donut [ 'dənʌt ] пончик, жаренный пирожок";
            wordsArr[2] = "Eggs [ eg ] яйца";
            wordsArr[3] = "Ham [ ham ] ветчина";
            wordsArr[4] = "Jelly [ ˈjelē ] джем";
            wordsArr[5] = "Lemonade [ ˈleməˌnād ] лимонад";
            wordsArr[6] = "Burger [ 'bɜ:gə ] бургер";
            wordsArr[7] = "Pizza [ 'pi:tsə ] пицца";
            wordsArr[8] = "Sandwich [ ˈsanˌ(d)wiCH ] сэндвич";
            wordsArr[9] = "Potato [ pəˈtātō ] картофель";*/
        }
    }
}
