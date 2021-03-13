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

    Button btnVocabulary,btnGrammar;
    TextView textViewCompletedInterest1,textViewCompletedInterest2;

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

        btnVocabulary = rootView.findViewById(R.id.btnVocabulary);
        btnGrammar = rootView.findViewById(R.id.btnGrammar);

        btnVocabulary.setOnClickListener(this::onClickBtnCnt);
        btnGrammar.setOnClickListener(this::onClickBtnCnt);

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

        //textViewCompletedInterest1 = rootView.findViewById(R.id.textViewCompletedInterest1);
        //textViewCompletedInterest2 = rootView.findViewById(R.id.textViewCompletedInterest2);

        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        //users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test1Interest").get();

        /*users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
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

                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        updateUI();*/

        return rootView;
    }

    private void updateUI(){
        /*textViewCompletedInterest1.setText(String.format("%d%%", completedInterest1));
        textViewCompletedInterest2.setText(String.format("%d%%", completedInterest2));
        textViewCompletedInterest3.setText(String.format("%d%%", completedInterest3));
        textViewCompletedInterest4.setText(String.format("%d%%", completedInterest4));
        textViewCompletedInterest5.setText(String.format("%d%%", completedInterest5));
        textViewCompletedInterest6.setText(String.format("%d%%", completedInterest6));*/

    }

    public void onClickBtnCnt(View view) {
        //Intent intentWords = new Intent(getActivity(), WordsActivity.class);
        //Intent intentTenses = new Intent(getActivity(), TensesActivity.class);
        /*1 - furniture
         *2 - school supplies
         *3 - food
         *4 - present
         *5 - past
         *6 - future
         */
        switch (view.getId()) {
            case R.id.btnVocabulary:
                ((MainActivity)getContext()).setFragSelectedVocabulary();
                break;
            case R.id.btnGrammar:
                ((MainActivity)getContext()).setFragSelectedGrammar();
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
