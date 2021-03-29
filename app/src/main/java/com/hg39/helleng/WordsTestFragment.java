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

    Button btnNext;
    TextView wordView,resultView;
    EditText editTextField;
    int words;
    int completed;
    String completedString;
    String userRes1,userRes2,userRes3,userRes4,userRes5,userRes6,userRes7,userRes8,userRes9,userRes10;
    String currentAnswer;
    String word1,word2,word3,word4,word5,word6,word7,word8,word9,word10;
    String word1Ru,word2Ru,word3Ru,word4Ru,word5Ru,word6Ru,word7Ru,word8Ru,word9Ru,word10Ru;
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
        //currentAnswer = editTextField.getText().toString();
        //currentAnswer.replaceAll("\\s","");
        //currentAnswer.trim();

        switch (words) {
            case 0:
                btnNext.setText("Next");
                wordView.setText(word1Ru);
                break;
            case 1:
                userRes1 = editTextField.getText().toString();
                userRes1.trim();

                if (word1.equalsIgnoreCase(userRes1)) {
                    completed += 10;
                }

                wordView.setText(word2Ru);
                break;
            case 2:
                userRes2 = editTextField.getText().toString();
                userRes2.trim();

                if (word2.equalsIgnoreCase(userRes2)) {
                    completed += 10;
                }

                wordView.setText(word3Ru);
                break;
            case 3:
                userRes3 = editTextField.getText().toString();
                userRes3.trim();

                if (word3.equalsIgnoreCase(userRes3)) {
                    completed += 10;
                }

                wordView.setText(word4Ru);
                break;
            case 4:
                userRes4 = editTextField.getText().toString();
                userRes4.trim();

                if (word4.equalsIgnoreCase(userRes4)) {
                    completed += 10;
                }

                wordView.setText(word5Ru);
                break;
            case 5:
                userRes5 = editTextField.getText().toString();
                userRes5.trim();

                if (word5.equalsIgnoreCase(userRes5)) {
                    completed += 10;
                }

                wordView.setText(word6Ru);
                break;
            case 6:
                userRes6 = editTextField.getText().toString();
                userRes6.trim();

                if (word6.equalsIgnoreCase(userRes6)) {
                    completed += 10;
                }

                wordView.setText(word7Ru);
                break;
            case 7:
                userRes7 = editTextField.getText().toString();
                userRes7.trim();

                if (word7.equalsIgnoreCase(userRes7)) {
                    completed += 10;
                }

                wordView.setText(word8Ru);
                break;
            case 8:
                userRes8 = editTextField.getText().toString();
                userRes8.trim();

                if (word8.equalsIgnoreCase(userRes8)) {
                    completed += 10;
                }

                wordView.setText(word9Ru);
                break;
            case 9:
                userRes9 = editTextField.getText().toString();
                userRes9.trim();

                if (word9.equalsIgnoreCase(userRes9)) {
                    completed += 10;
                }

                wordView.setText(word10Ru);
                break;
            case 10:
                userRes10 = editTextField.getText().toString();
                userRes10.trim();

                if (word10.equalsIgnoreCase(userRes10)) {
                    completed += 10;
                }

                wordView.setText("Test is end.");
                completedString = Integer.toString(completed);
                btnNext.setText("Exit");
                editTextField.setEnabled(false);
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

                ((WordsActivity)getContext()).setFragResult(userRes1,userRes2,userRes3,userRes4,userRes5,userRes6,userRes7,userRes8,userRes9,userRes10,
                                                            word1,word2,word3,word4,word5,word6,word7,word8,word9,word10,completed,10,testType);

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
