package com.hg39.helleng;

import android.content.Intent;
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
import com.hg39.helleng.Models.User;

public class WordsTestFragment extends Fragment {

    private Button btnNext;
    private TextView wordView,resultView;
    private EditText editTextField;
    private int words;
    private int completed;
    private String completedString;
    private boolean res1,res2,res3,res4,res5,res6,res7,res8,res9,res10;
    private String currentAnswer;
    private String word1,word2,word3,word4,word5,word6,word7,word8,word9,word10;
    private String word1Ru,word2Ru,word3Ru,word4Ru,word5Ru,word6Ru,word7Ru,word8Ru,word9Ru,word10Ru;
    private int testType;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference tests;
    DatabaseReference users;

    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        tests = database.getReference("Tests");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        users = database.getReference("Users");

        View rootView =
                inflater.inflate(R.layout.fragment_words_test,container,false);

        testType = getArguments().getInt("testType");

        btnNext = rootView.findViewById(R.id.btnNext);
        editTextField = rootView.findViewById(R.id.editTextField);
        wordView = rootView.findViewById(R.id.wordView);
        btnNext.setOnClickListener(this::onClickBtnNext);
        btnNext.setText("Start");
        resultView = rootView.findViewById(R.id.resultView);
        words = 0;
        completed = 0;
        wordView.setText(word1);
        editTextField.setEnabled(false);

        setWordToTest(testType);

        return rootView;
    }

    protected void onClickBtnNext(View view) {
        editTextField.setEnabled(true);
        currentAnswer = editTextField.getText().toString();
        //currentAnswer.replaceAll("\\s","");
        currentAnswer.trim();

        switch (words) {
            case 0:
                btnNext.setText("Next");
                wordView.setText(word1Ru);
                break;
            case 1:
                if (word1.equalsIgnoreCase(currentAnswer)) {
                    res1 = true;
                    completed += 10;
                } else {
                    res1 = false;
                }
                wordView.setText(word2Ru);
                break;
            case 2:
                if (word2.equalsIgnoreCase(currentAnswer)) {
                    res2 = true;
                    completed += 10;
                } else {
                    res2 = false;
                }
                wordView.setText(word3Ru);
                break;
            case 3:
                if (word3.equalsIgnoreCase(currentAnswer)) {
                    res3 = true;
                    completed += 10;
                } else {
                    res3 = false;
                }
                wordView.setText(word4Ru);
                break;
            case 4:
                if (word4.equalsIgnoreCase(currentAnswer)) {
                    res4 = true;
                    completed += 10;
                } else {
                    res4 = false;
                }
                wordView.setText(word5Ru);
                break;
            case 5:
                if (word5.equalsIgnoreCase(currentAnswer)) {
                    res5 = true;
                    completed += 10;
                } else {
                    res5 = false;
                }
                wordView.setText(word6Ru);
                break;
            case 6:
                if (word6.equalsIgnoreCase(currentAnswer)) {
                    res6 = true;
                    completed += 10;
                } else {
                    res6 = false;
                }
                wordView.setText(word7Ru);
                break;
            case 7:
                if (word7.equalsIgnoreCase(currentAnswer)) {
                    res7 = true;
                    completed += 10;
                } else {
                    res7 = false;
                }
                wordView.setText(word8Ru);
                break;
            case 8:
                if (word8.equalsIgnoreCase(currentAnswer)) {
                    res8 = true;
                    completed += 10;
                } else {
                    res8 = false;
                }
                wordView.setText(word9Ru);
                break;
            case 9:
                if (word9.equalsIgnoreCase(currentAnswer)) {
                    res9 = true;
                    completed += 10;
                } else {
                    res9 = false;
                }
                wordView.setText(word10Ru);
                break;
            case 10:
                if (word10.equalsIgnoreCase(currentAnswer)) {
                    res10 = true;
                    completed += 10;
                } else {
                    res10 = false;
                }
                wordView.setText("Test is end.");
                completedString = Integer.toString(completed);
                btnNext.setText("Exit");
                editTextField.setEnabled(false);
                resultView.setText("You completed\n" + completedString + "%");
                break;
            case 11:
                //Intent intent = new Intent(getActivity(),SaverActivity.class);
                switch (testType) {
                    case 1:
                        user.setTest1Interest(completed);
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test1Interest").setValue(user.getTest1Interest());
                        //intent.putExtra("result1", completed);
                        break;
                    case 2:
                        user.setTest2Interest(completed);
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test2Interest").setValue(user.getTest2Interest());
                        //intent.putExtra("result2", completed);
                        break;
                    case 3:
                        user.setTest3Interest(completed);
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test3Interest").setValue(user.getTest3Interest());
                        //intent.putExtra("result3", completed);
                        break;
                    default:
                        Toast.makeText(getActivity(), "Error",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                //intent.putExtra("testType",testType);
                //startActivity(intent);
                startActivity(new Intent(getContext(),MainActivity.class));
                break;
        }
        editTextField.setText(null);
        words++;

    }

    private void setWordToTest(int testType) {

        switch (testType) {
            case 1:
                tests.child("Words").child("Furniture").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        word1Ru = snapshot.child("RU").child("0").getValue(String.class);
                        word2Ru = snapshot.child("RU").child("1").getValue(String.class);
                        word3Ru = snapshot.child("RU").child("2").getValue(String.class);
                        word4Ru = snapshot.child("RU").child("3").getValue(String.class);
                        word5Ru = snapshot.child("RU").child("4").getValue(String.class);
                        word6Ru = snapshot.child("RU").child("5").getValue(String.class);
                        word7Ru = snapshot.child("RU").child("6").getValue(String.class);
                        word8Ru = snapshot.child("RU").child("7").getValue(String.class);
                        word9Ru = snapshot.child("RU").child("8").getValue(String.class);
                        word10Ru = snapshot.child("RU").child("9").getValue(String.class);

                        word1 = snapshot.child("ENG").child("0").getValue(String.class);
                        word2 = snapshot.child("ENG").child("1").getValue(String.class);
                        word3 = snapshot.child("ENG").child("2").getValue(String.class);
                        word4 = snapshot.child("ENG").child("3").getValue(String.class);
                        word5 = snapshot.child("ENG").child("4").getValue(String.class);
                        word6 = snapshot.child("ENG").child("5").getValue(String.class);
                        word7 = snapshot.child("ENG").child("6").getValue(String.class);
                        word8 = snapshot.child("ENG").child("7").getValue(String.class);
                        word9 = snapshot.child("ENG").child("8").getValue(String.class);
                        word10 = snapshot.child("ENG").child("9").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),"Failed to update data.", Toast.LENGTH_SHORT).show();
                    }
                });

                /*word1 = "armchair"; word1Ru = "Кресло";
                word2 = "bed"; word2Ru = "Кровать";
                word3 = "bench"; word3Ru = "Скамья";
                word4 = "blanket"; word4Ru = "Одеяло";
                word5 = "bookshelf"; word5Ru = "Книжная полка";
                word6 = "bookcase"; word6Ru = "Книжный шкаф";
                word7 = "cabinet"; word7Ru = "Шкаф";
                word8 = "carpet"; word8Ru = "Ковёр";
                word9 = "chair"; word9Ru = "Стул";
                word10 = "lamp"; word10Ru = "Лампа";*/
                break;
            case 2:

                tests.child("Words").child("School supplies").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        word1Ru = snapshot.child("RU").child("0").getValue(String.class);
                        word2Ru = snapshot.child("RU").child("1").getValue(String.class);
                        word3Ru = snapshot.child("RU").child("2").getValue(String.class);
                        word4Ru = snapshot.child("RU").child("3").getValue(String.class);
                        word5Ru = snapshot.child("RU").child("4").getValue(String.class);
                        word6Ru = snapshot.child("RU").child("5").getValue(String.class);
                        word7Ru = snapshot.child("RU").child("6").getValue(String.class);
                        word8Ru = snapshot.child("RU").child("7").getValue(String.class);
                        word9Ru = snapshot.child("RU").child("8").getValue(String.class);
                        word10Ru = snapshot.child("RU").child("9").getValue(String.class);

                        word1 = snapshot.child("ENG").child("0").getValue(String.class);
                        word2 = snapshot.child("ENG").child("1").getValue(String.class);
                        word3 = snapshot.child("ENG").child("2").getValue(String.class);
                        word4 = snapshot.child("ENG").child("3").getValue(String.class);
                        word5 = snapshot.child("ENG").child("4").getValue(String.class);
                        word6 = snapshot.child("ENG").child("5").getValue(String.class);
                        word7 = snapshot.child("ENG").child("6").getValue(String.class);
                        word8 = snapshot.child("ENG").child("7").getValue(String.class);
                        word9 = snapshot.child("ENG").child("8").getValue(String.class);
                        word10 = snapshot.child("ENG").child("9").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),"Failed to update data.", Toast.LENGTH_SHORT).show();
                    }
                });

                /*word1 = "pencil case"; word1Ru = "Пенал";
                word2 = "backpack"; word2Ru = "Рюкзак";
                word3 = "clip"; word3Ru = "Скрепка";
                word4 = "pencil"; word4Ru = "Карандаш";
                word5 = "ballpoint"; word5Ru = "Шариковая ручка";
                word6 = "eraser"; word6Ru = "Ластик";
                word7 = "ruler"; word7Ru = "Линейка";
                word8 = "blackboard"; word8Ru = "Доска";
                word9 = "book"; word9Ru = "Книга";
                word10 = "stapler"; word10Ru = "Степлер";*/
                break;
            case 3:

                tests.child("Words").child("Food").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        word1Ru = snapshot.child("RU").child("0").getValue(String.class);
                        word2Ru = snapshot.child("RU").child("1").getValue(String.class);
                        word3Ru = snapshot.child("RU").child("2").getValue(String.class);
                        word4Ru = snapshot.child("RU").child("3").getValue(String.class);
                        word5Ru = snapshot.child("RU").child("4").getValue(String.class);
                        word6Ru = snapshot.child("RU").child("5").getValue(String.class);
                        word7Ru = snapshot.child("RU").child("6").getValue(String.class);
                        word8Ru = snapshot.child("RU").child("7").getValue(String.class);
                        word9Ru = snapshot.child("RU").child("8").getValue(String.class);
                        word10Ru = snapshot.child("RU").child("9").getValue(String.class);

                        word1 = snapshot.child("ENG").child("0").getValue(String.class);
                        word2 = snapshot.child("ENG").child("1").getValue(String.class);
                        word3 = snapshot.child("ENG").child("2").getValue(String.class);
                        word4 = snapshot.child("ENG").child("3").getValue(String.class);
                        word5 = snapshot.child("ENG").child("4").getValue(String.class);
                        word6 = snapshot.child("ENG").child("5").getValue(String.class);
                        word7 = snapshot.child("ENG").child("6").getValue(String.class);
                        word8 = snapshot.child("ENG").child("7").getValue(String.class);
                        word9 = snapshot.child("ENG").child("8").getValue(String.class);
                        word10 = snapshot.child("ENG").child("9").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),"Failed to update data.", Toast.LENGTH_SHORT).show();
                    }
                });

                /*word1 = "cheese"; word1Ru = "Сыр";
                word2 = "donut"; word2Ru = "Пончик";
                word3 = "eggs"; word3Ru = "Яйца";
                word4 = "ham"; word4Ru = "Ветчина";
                word5 = "jelly"; word5Ru = "Джем";
                word6 = "lemonade"; word6Ru = "Лимонад";
                word7 = "burger"; word7Ru = "Бургер";
                word8 = "pizza"; word8Ru = "Пицца";
                word9 = "sandwich"; word9Ru = "Сэндвич";
                word10 = "potato"; word10Ru = "Картофель";*/
                break;
        }

    }
}
