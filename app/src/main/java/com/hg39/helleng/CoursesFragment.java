package com.hg39.helleng;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Update;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;

import static android.content.Context.MODE_PRIVATE;

public class CoursesFragment extends Fragment {

    private Button btnCnt1,btnCnt2,btnCnt3, btnCnt4,btnCnt5,btnCnt6, btnCnt7,btnCnt8,btnCnt9,
            btnCnt10,btnCnt11,btnCnt12,btnCnt13,btnCnt14,btnCnt15,btnCnt16;
    private TextView textViewCompletedInterest1,textViewCompletedInterest2,textViewCompletedInterest3,
            textViewCompletedInterest4,textViewCompletedInterest5,textViewCompletedInterest6,
            textViewCompletedInterest7,textViewCompletedInterest8,textViewCompletedInterest9,
            textViewCompletedInterest10,textViewCompletedInterest11,textViewCompletedInterest12,
            textViewCompletedInterest13,textViewCompletedInterest14,textViewCompletedInterest15,
            textViewCompletedInterest16;
    private int completedInterest1,completedInterest2,completedInterest3,
            completedInterest4,completedInterest5,completedInterest6, completedInterest8,
            completedInterest7,completedInterest9, completedInterest10,completedInterest11,
            completedInterest12, completedInterest13,completedInterest14,completedInterest15,
            completedInterest16;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_courses,container,false);
        View rootView =
                inflater.inflate(R.layout.fragment_courses, container, false);

        btnCnt1 = rootView.findViewById(R.id.btnCnt1);
        btnCnt2 = rootView.findViewById(R.id.btnCnt2);
        btnCnt3 = rootView.findViewById(R.id.btnCnt3);
        btnCnt4 = rootView.findViewById(R.id.btnCnt4);
        btnCnt5 = rootView.findViewById(R.id.btnCnt5);
        btnCnt6 = rootView.findViewById(R.id.btnCnt6);
        btnCnt7 = rootView.findViewById(R.id.btnCnt7);
        btnCnt8 = rootView.findViewById(R.id.btnCnt8);
        btnCnt9 = rootView.findViewById(R.id.btnCnt9);
        btnCnt10 = rootView.findViewById(R.id.btnCnt10);
        btnCnt11 = rootView.findViewById(R.id.btnCnt11);
        btnCnt12 = rootView.findViewById(R.id.btnCnt12);
        btnCnt13 = rootView.findViewById(R.id.btnCnt13);
        btnCnt14 = rootView.findViewById(R.id.btnCnt14);
        btnCnt15 = rootView.findViewById(R.id.btnCnt15);
        btnCnt16 = rootView.findViewById(R.id.btnCnt16);

        btnCnt1.setOnClickListener(this::onClickBtnCnt);
        btnCnt2.setOnClickListener(this::onClickBtnCnt);
        btnCnt3.setOnClickListener(this::onClickBtnCnt);
        btnCnt4.setOnClickListener(this::onClickBtnCnt);
        btnCnt5.setOnClickListener(this::onClickBtnCnt);
        btnCnt6.setOnClickListener(this::onClickBtnCnt);
        btnCnt7.setOnClickListener(this::onClickBtnCnt);
        btnCnt8.setOnClickListener(this::onClickBtnCnt);
        btnCnt9.setOnClickListener(this::onClickBtnCnt);
        btnCnt10.setOnClickListener(this::onClickBtnCnt);
        btnCnt11.setOnClickListener(this::onClickBtnCnt);
        btnCnt12.setOnClickListener(this::onClickBtnCnt);
        btnCnt13.setOnClickListener(this::onClickBtnCnt);
        btnCnt14.setOnClickListener(this::onClickBtnCnt);
        btnCnt15.setOnClickListener(this::onClickBtnCnt);
        btnCnt16.setOnClickListener(this::onClickBtnCnt);

        /*completedInterest1 = 0;
        completedInterest2 = 0;
        completedInterest3 = 0;
        completedInterest4 = 0;
        completedInterest5 = 0;
        completedInterest6 = 0;
        completedInterest7 = 0;
        completedInterest8 = 0;
        completedInterest9 = 0;
        completedInterest10 = 0;
        completedInterest11 = 0;
        completedInterest12 = 0;
        completedInterest13 = 0;
        completedInterest14 = 0;
        completedInterest15 = 0;
        completedInterest16 = 0;*/

        textViewCompletedInterest1 = rootView.findViewById(R.id.textViewCompletedInterest1);
        textViewCompletedInterest2 = rootView.findViewById(R.id.textViewCompletedInterest2);
        textViewCompletedInterest3 = rootView.findViewById(R.id.textViewCompletedInterest3);
        textViewCompletedInterest4 = rootView.findViewById(R.id.textViewCompletedInterest4);
        textViewCompletedInterest5 = rootView.findViewById(R.id.textViewCompletedInterest5);
        textViewCompletedInterest6 = rootView.findViewById(R.id.textViewCompletedInterest6);
        textViewCompletedInterest7 = rootView.findViewById(R.id.textViewCompletedInterest7);
        textViewCompletedInterest8 = rootView.findViewById(R.id.textViewCompletedInterest8);
        textViewCompletedInterest9 = rootView.findViewById(R.id.textViewCompletedInterest9);
        textViewCompletedInterest10 = rootView.findViewById(R.id.textViewCompletedInterest10);
        textViewCompletedInterest11 = rootView.findViewById(R.id.textViewCompletedInterest11);
        textViewCompletedInterest12 = rootView.findViewById(R.id.textViewCompletedInterest12);
        textViewCompletedInterest13 = rootView.findViewById(R.id.textViewCompletedInterest13);
        textViewCompletedInterest14 = rootView.findViewById(R.id.textViewCompletedInterest14);
        textViewCompletedInterest15 = rootView.findViewById(R.id.textViewCompletedInterest15);
        textViewCompletedInterest16 = rootView.findViewById(R.id.textViewCompletedInterest16);

        //assert getArguments() != null;
        /*completedInterest1 = getArguments().getInt("inf1");
        completedInterest2 = getArguments().getInt("inf2");
        completedInterest3 = getArguments().getInt("inf3");
        completedInterest4 = getArguments().getInt("inf4");
        completedInterest5 = getArguments().getInt("inf5");
        completedInterest6 = getArguments().getInt("inf6");
        completedInterest7 = getArguments().getInt("inf7");
        completedInterest8 = getArguments().getInt("inf8");
        completedInterest9 = getArguments().getInt("inf9");
        completedInterest10 = getArguments().getInt("inf10");
        completedInterest11 = getArguments().getInt("inf11");
        completedInterest12 = getArguments().getInt("inf12");
        completedInterest13 = getArguments().getInt("inf13");
        completedInterest14 = getArguments().getInt("inf14");
        completedInterest15 = getArguments().getInt("inf15");
        completedInterest16 = getArguments().getInt("inf16");*/

        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        //users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test1Interest").get();

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedInterest1 = dataSnapshot.child("test1Interest").getValue(int.class);
                completedInterest2 = dataSnapshot.child("test2Interest").getValue(int.class);
                completedInterest3 = dataSnapshot.child("test3Interest").getValue(int.class);
                completedInterest4 = dataSnapshot.child("test4Interest").getValue(int.class);
                completedInterest5 = dataSnapshot.child("test5Interest").getValue(int.class);
                completedInterest6 = dataSnapshot.child("test6Interest").getValue(int.class);
                completedInterest7 = dataSnapshot.child("test7Interest").getValue(int.class);
                completedInterest8 = dataSnapshot.child("test8Interest").getValue(int.class);
                completedInterest9 = dataSnapshot.child("test9Interest").getValue(int.class);
                completedInterest10 = dataSnapshot.child("test10Interest").getValue(int.class);
                completedInterest11 = dataSnapshot.child("test11Interest").getValue(int.class);
                completedInterest12 = dataSnapshot.child("test12Interest").getValue(int.class);
                completedInterest13 = dataSnapshot.child("test13Interest").getValue(int.class);
                completedInterest14 = dataSnapshot.child("test14Interest").getValue(int.class);
                completedInterest15 = dataSnapshot.child("test15Interest").getValue(int.class);
                completedInterest16 = dataSnapshot.child("test16Interest").getValue(int.class);

                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        updateUI();

        return rootView;
    }

    private void updateUI(){
        textViewCompletedInterest1.setText(String.format("%d%%", completedInterest1));
        textViewCompletedInterest2.setText(String.format("%d%%", completedInterest2));
        textViewCompletedInterest3.setText(String.format("%d%%", completedInterest3));
        textViewCompletedInterest4.setText(String.format("%d%%", completedInterest4));
        textViewCompletedInterest5.setText(String.format("%d%%", completedInterest5));
        textViewCompletedInterest6.setText(String.format("%d%%", completedInterest6));
        textViewCompletedInterest7.setText(String.format("%d%%", completedInterest7));
        textViewCompletedInterest8.setText(String.format("%d%%", completedInterest8));
        textViewCompletedInterest9.setText(String.format("%d%%", completedInterest9));
        textViewCompletedInterest10.setText(String.format("%d%%", completedInterest10));
        textViewCompletedInterest11.setText(String.format("%d%%", completedInterest11));
        textViewCompletedInterest12.setText(String.format("%d%%", completedInterest12));
        textViewCompletedInterest13.setText(String.format("%d%%", completedInterest13));
        textViewCompletedInterest14.setText(String.format("%d%%", completedInterest14));
        textViewCompletedInterest15.setText(String.format("%d%%", completedInterest15));
        textViewCompletedInterest16.setText(String.format("%d%%", completedInterest16));
    }

    public void onClickBtnCnt(View view) {
        Intent intentWords = new Intent(getActivity(), WordsActivity.class);
        Intent intentTenses = new Intent(getActivity(), TensesActivity.class);
        /*1 - furniture
         *2 - school supplies
         *3 - food
         *4 - present
         *5 - past
         *6 - future
         */
        switch (view.getId()) {
            case R.id.btnCnt1:
                intentWords.putExtra("testType", 1);
                startActivity(intentWords);
                break;
            case R.id.btnCnt2:
                intentWords.putExtra("testType", 2);
                startActivity(intentWords);
                break;
            case R.id.btnCnt3:
                intentWords.putExtra("testType", 3);
                startActivity(intentWords);
                break;
            case R.id.btnCnt4:
                intentTenses.putExtra("testType", 4);
                startActivity(intentTenses);
                break;
            case R.id.btnCnt5:
                intentTenses.putExtra("testType", 5);
                startActivity(intentTenses);
                break;
            case R.id.btnCnt6:
                intentTenses.putExtra("testType", 6);
                startActivity(intentTenses);
                break;
            case R.id.btnCnt7:
                //intent.putExtra("testType", 7);
                break;
            case R.id.btnCnt8:
                //intent.putExtra("testType", 8);
                break;
            case R.id.btnCnt9:
                //intent.putExtra("testType", 9);
                break;
            case R.id.btnCnt10:
                //intent.putExtra("testType", 10);
                break;
            case R.id.btnCnt11:
                //intent.putExtra("testType", 11);
                break;
            case R.id.btnCnt12:
                //intent.putExtra("testType", 12);
                break;
            case R.id.btnCnt13:
                //intent.putExtra("testType", 13);
                break;
            case R.id.btnCnt14:
                //intent.putExtra("testType", 14);
                break;
            case R.id.btnCnt15:
                //intent.putExtra("testType", 15);
                break;
            case R.id.btnCnt16:
                //intent.putExtra("testType", 16);
                break;
            default:
                Toast.makeText(getActivity(), "Error",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        /*
        Toast.makeText(getActivity(), "Вы нажали на кнопку",
                Toast.LENGTH_SHORT).show();*/
    }
}
