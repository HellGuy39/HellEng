package com.hg39.helleng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private Button btnEdit,btnSignOut;
    private TextView textViewFirstName,textViewLastName,textViewUserStatus,textViewReg,textViewTestsStarted,textViewTestsFullCompleted;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    int testsStarted,testsFullCompleted;
    String firstNStr,lastNStr,statusStr,regDate;

    User user = new User();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                firstNStr = dataSnapshot.child("firstName").getValue(String.class);
                lastNStr = dataSnapshot.child("lastName").getValue(String.class);
                statusStr = dataSnapshot.child("status").getValue(String.class);
                regDate = dataSnapshot.child("registerDate").getValue(String.class);
                testsFullCompleted = dataSnapshot.child("testsFullCompleted").getValue(Integer.class);
                testsStarted= dataSnapshot.child("testsStarted").getValue(Integer.class);
                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_profile,container,false);

        textViewReg = rootView.findViewById(R.id.textViewReg);
        textViewTestsFullCompleted = rootView.findViewById(R.id.textViewTestFullCompleted);
        textViewTestsStarted = rootView.findViewById(R.id.textViewTestStarted);
        textViewFirstName = rootView.findViewById(R.id.textViewFirstName);
        textViewLastName = rootView.findViewById(R.id.textViewLastName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);

        btnEdit = rootView.findViewById(R.id.btnEdit);
        btnSignOut = rootView.findViewById(R.id.btnSingOut);

        btnSignOut.setOnClickListener(this::onClick);
        btnEdit.setOnClickListener(this::onClick);

        //textViewUserStatus.setText(getArguments().getString("userStatus"));
        //textViewFirstName.setText(getArguments().getString("userFName"));
        //textViewLastName.setText(getArguments().getString("userLName"));

        updateUI();

        return rootView;
    }

    private void updateUI() {
        textViewReg.setText("Registered since " + regDate);
        textViewTestsStarted.setText("Tests started: " + testsStarted);
        textViewTestsFullCompleted.setText("Tests completed 100%: " + testsFullCompleted);
        textViewFirstName.setText(firstNStr);
        textViewLastName.setText(lastNStr);
        textViewUserStatus.setText(statusStr);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEdit) {
            ((MainActivity)getActivity())
                    .setEditProfileFragment();
        } else if (v.getId() == R.id.btnSingOut) {
            ((MainActivity)getActivity())
                    .signOut();
            /*FirebaseAuth.getInstance().signOut();
            if (mAuth.getCurrentUser() == null) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                finish();*/
            } else {
                Toast.makeText(v.getContext(),"Error",Toast.LENGTH_SHORT).show();
            }

        }
    }

