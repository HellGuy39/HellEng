package com.hg39.helleng;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;

public class EditProfileFragment extends Fragment {

    private Button btnBack,btnSave;
    private EditText editTextFirstName,editTextLastName,editTextUserStatus;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    User user = new User();
    String firstNStr,lastNStr,statusStr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

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

                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        View rootView =
                inflater.inflate(R.layout.fragment_edit_profile,container,false);

        btnBack = rootView.findViewById(R.id.btnEdit);
        btnSave = rootView.findViewById(R.id.btnSave);

        btnBack.setOnClickListener(this::onClickBtnBack);
        btnSave.setOnClickListener(this::onClickBtnSave);

        editTextFirstName = rootView.findViewById(R.id.editTextFirstName);
        editTextLastName = rootView.findViewById(R.id.editTExtLastName);
        editTextUserStatus = rootView.findViewById(R.id.editTextUserStatus);

        editTextFirstName.setText(firstNStr);
        editTextLastName.setText(lastNStr);
        editTextUserStatus.setText(statusStr);
        //updateUI();

        //editTextFirstName.setText(getArguments().getString("userFName"));
        //editTextLastName.setText(getArguments().getString("userLName"));
        //editTextUserStatus.setText(getArguments().getString("userStatus"));

        return rootView;
    }

    private void updateUI() {
        editTextFirstName.setText(firstNStr);
        editTextLastName.setText(lastNStr);
        editTextUserStatus.setText(statusStr);
    }

    protected void onClickBtnBack(View view) {
        ((MainActivity)getActivity())
                .outEditProfileFragment();
    }

    protected void onClickBtnSave(View view) {

        String strStatus = editTextUserStatus.getText().toString();
        String strFName = editTextFirstName.getText().toString();
        String strLName = editTextLastName.getText().toString();

        if (strFName.length() > 32) {
            Toast.makeText(getActivity(),"First name must be no more than 32 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if (strLName.length() > 32) {
            Toast.makeText(getActivity(),"Last name must be no more than 32 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if (strStatus.length() > 64) {
            Toast.makeText(getActivity(),"Status must be no more than 64 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        //((MainActivity)getActivity())
        //           .saveProfileInfo(strStatus,strFName,strLName);

        User user = new User();
        user.setFirstName(editTextFirstName.getText().toString());
        user.setLastName(editTextLastName.getText().toString());
        user.setStatus(editTextUserStatus.getText().toString());

        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("firstName").setValue(user.getFirstName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lastName").setValue(user.getLastName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue(user.getStatus());

        Toast.makeText(getActivity(),
                "What a save!",
                Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity())
                .outEditProfileFragment();


    }

}
