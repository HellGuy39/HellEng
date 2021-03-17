package com.hg39.helleng;

import android.content.Context;
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

public class ProfileFragment extends Fragment {

    private Button btnEdit,btnSignOut;
    private TextView textViewFirstName,textViewLastName,textViewUserStatus,textViewReg,textViewTestsStarted,textViewTestsFullCompleted,textViewUserId;
    ImageView profileImage;
    com.google.android.material.appbar.MaterialToolbar topAppBar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    StorageReference storageReference;
    StorageReference profileRef;

    int testsStarted,testsFullCompleted;
    String firstNStr,lastNStr,statusStr,regDate,userId;

    User user = new User();

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
                testsFullCompleted = dataSnapshot.child("testsFullCompleted").getValue(Integer.class);
                testsStarted= dataSnapshot.child("testsStarted").getValue(Integer.class);
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
        textViewFirstName = rootView.findViewById(R.id.textViewFirstName);
        textViewLastName = rootView.findViewById(R.id.textViewLastName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);
        profileImage = rootView.findViewById(R.id.profileImage);


        //btnEdit = rootView.findViewById(R.id.btnEdit);
        //btnSignOut = rootView.findViewById(R.id.btnSingOut);

        //btnSignOut.setOnClickListener(this::onClick);
        //btnEdit.setOnClickListener(this::onClick);

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
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
                        break;

                    case R.id.reference:
                        break;

                    case R.id.aboutTheApp:
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
        textViewReg.setText("Registered since " + regDate);
        textViewTestsStarted.setText("Tests started: " + testsStarted);
        textViewTestsFullCompleted.setText("Tests completed 100%: " + testsFullCompleted);
        textViewFirstName.setText(firstNStr);
        textViewLastName.setText(lastNStr);
        textViewUserStatus.setText(statusStr);
        textViewUserId.setText("Id: " + userId);

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    /*@Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.btnEdit) {
            ((MainActivity)getActivity())
                    .setEditProfileFragment();
        } else if (v.getId() == R.id.btnSingOut) {
            ((MainActivity)getActivity())
                    .signOut();
            } else {
                Toast.makeText(v.getContext(),"Error",Toast.LENGTH_SHORT).show();
            }

        }*/
    }
