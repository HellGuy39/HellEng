package com.hg39.helleng;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hg39.helleng.Models.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewOtherProfileFragment extends Fragment {

    Button btnEdit,btnSignOut;
    TextView textViewUserStatus,textViewReg,textViewTestsStarted,textViewTestsFullCompleted,textViewUserId;
    TextView textViewFullName;
    CircleImageView profileImage;
    com.google.android.material.appbar.MaterialToolbar topAppBar;

    FirebaseUser mUser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mUserRef;
    StorageReference storageReference;
    StorageReference profileRef;

    int testsStarted,testsFullCompleted;
    String firstNStr,lastNStr,statusStr,regDate,userId;
    String profileImageUri;

    User user = new User();

    String userID;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = getArguments().getString("userKey","Nope");
        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mUserRef = database.getReference("Users").child(userID);

        mUser = mAuth.getCurrentUser();
        FirebaseUser userF = mAuth.getCurrentUser();

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    profileImageUri = snapshot.child("profileImage").getValue(String.class);
                    firstNStr = snapshot.child("firstName").getValue(String.class);
                    lastNStr = snapshot.child("lastName").getValue(String.class);
                    statusStr = snapshot.child("status").getValue(String.class);
                    regDate = snapshot.child("registerDate").getValue(String.class);

                    updateUI();

                }
                else
                {
                    Toast.makeText(getContext(),"Data not found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_view_other_profile,container,false);

        topAppBar = rootView.findViewById(R.id.topAppBar);
        textViewUserId = rootView.findViewById(R.id.textViewUserId);
        textViewReg = rootView.findViewById(R.id.textViewReg);
        textViewTestsFullCompleted = rootView.findViewById(R.id.textViewTestFullCompleted);
        textViewTestsStarted = rootView.findViewById(R.id.textViewTestStarted);
        textViewFullName = rootView.findViewById(R.id.textViewFullName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);
        profileImage = rootView.findViewById(R.id.profileImage);



        topAppBar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_settings_24));
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).setFragFindFriends();
            }
        });

        updateUI();

        return rootView;
    }

    private void updateUI() {

        textViewReg.setText("Registered since " + regDate);
        textViewTestsStarted.setText("Tests started: " + testsStarted);
        textViewTestsFullCompleted.setText("Tests completed 100%: " + testsFullCompleted);
        textViewFullName.setText(firstNStr + " " + lastNStr);
        textViewUserStatus.setText(statusStr);
        textViewUserId.setText("Id: " + userId);

        Picasso.get().load(profileImageUri).into(profileImage);

    }

    @Override
    public void onStart() {
        super.onStart();
        //updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    }
