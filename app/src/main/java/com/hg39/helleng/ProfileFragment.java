package com.hg39.helleng;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
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

public class ProfileFragment extends Fragment {

    Button btnEdit,btnSignOut;
    TextView textViewUserStatus,textViewReg,textViewTestsStarted,textViewTestsFullCompleted,textViewUserId;
    TextView textViewFullName;
    TextView textViewWebStatus;
    CircleImageView profileImage;
    com.google.android.material.appbar.MaterialToolbar topAppBar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    StorageReference storageReference;
    StorageReference profileRef;

    int testsStarted,testsFullCompleted;
    String firstNStr,lastNStr,statusStr,regDate,userId;
    String profileImageUri;
    String webStatus;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        if (mAuth != null) {
            userId = mAuth.getCurrentUser().getUid();
        }

        FirebaseUser userF = mAuth.getCurrentUser();

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                firstNStr = dataSnapshot.child("firstName").getValue(String.class);
                lastNStr = dataSnapshot.child("lastName").getValue(String.class);
                statusStr = dataSnapshot.child("status").getValue(String.class);
                regDate = dataSnapshot.child("registerDate").getValue(String.class);
                webStatus = dataSnapshot.child("webStatus").getValue(String.class);
                //testsFullCompleted = dataSnapshot.child("testsFullCompleted").getValue(int.class);
                //testsStarted = dataSnapshot.child("testsStarted").getValue(int.class);
                profileImageUri = dataSnapshot.child("profileImage").getValue(String.class);
                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
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

        topAppBar = rootView.findViewById(R.id.topAppBar);
        textViewUserId = rootView.findViewById(R.id.textViewUserId);
        textViewReg = rootView.findViewById(R.id.textViewReg);
        textViewTestsFullCompleted = rootView.findViewById(R.id.textViewTestFullCompleted);
        textViewTestsStarted = rootView.findViewById(R.id.textViewTestStarted);
        textViewFullName = rootView.findViewById(R.id.textViewFullName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);
        profileImage = rootView.findViewById(R.id.profileImage);
        textViewWebStatus = rootView.findViewById(R.id.textViewWebStatus);

        //btnEdit = rootView.findViewById(R.id.btnEdit);
        //btnSignOut = rootView.findViewById(R.id.btnSingOut);

        //btnSignOut.setOnClickListener(this::onClick);
        //btnEdit.setOnClickListener(this::onClick);

        topAppBar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_settings_24));
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Хз для чего это нужно
            }
        });

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.editProfile:
                        ((MainActivity)getActivity())
                                .setEditProfileFragment();
                        break;

                    case R.id.settings:
                        startActivity(new Intent(getContext(),SettingsActivity.class));
                        break;

                    case R.id.reference:
                        break;

                    case R.id.aboutTheApp:
                        startActivity(new Intent(getContext(),AboutTheAppActivity.class));
                        break;

                    case R.id.signOut:
                        ((MainActivity)getActivity())
                                .signOut();
                        break;
                }

                return false;
            }
        });

        updateUI();

        return rootView;
    }

    private void updateUI() {

        textViewWebStatus.setText(webStatus);
        textViewReg.setText("Registered since " + regDate);
        textViewTestsStarted.setText("Tests started: " + testsStarted);
        textViewTestsFullCompleted.setText("Tests completed 100%: " + testsFullCompleted);
        textViewFullName.setText(firstNStr + " " + lastNStr);
        textViewUserStatus.setText(statusStr);
        textViewUserId.setText("Id: " + userId);

        if (profileImageUri != null) {
            Picasso.get().load(profileImageUri).into(profileImage);
        } else {
            //profileImage.setImageDrawable(getResources().getDrawable(R.drawable.no_avatar));
            profileImage.setImageResource(R.drawable.no_avatar);
        }

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
