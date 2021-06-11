package com.hg39.helleng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TextView textViewGreeting,date;
    TextView dateMonth,dateDayOfWeek;
    String userName;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    ValueEventListener dataListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName = dataSnapshot.child("firstName").getValue(String.class);

                updateUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_home,container,false);
        dateMonth = rootView.findViewById(R.id.dateMonth);
        dateDayOfWeek = rootView.findViewById(R.id.dateDayOfWeek);
        date = rootView.findViewById(R.id.date);
        textViewGreeting = rootView.findViewById(R.id.textViewGreeting);

        Calendar calendar = Calendar.getInstance();
        dateMonth.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH));
        dateDayOfWeek.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG_FORMAT,Locale.ENGLISH));

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd"/*"dd.MM.yyyy"*/, Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        date.setText(dateText);

        users.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(dataListener);

        return rootView;
    }

    private void updateUI() {
        textViewGreeting.setText("Welcome back, " + userName + "!");
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        users.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).removeEventListener(dataListener);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

    }
}
