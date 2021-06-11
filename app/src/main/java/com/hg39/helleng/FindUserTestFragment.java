package com.hg39.helleng;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FindUserTestFragment extends Fragment {

    androidx.coordinatorlayout.widget.CoordinatorLayout root;

    com.google.android.material.textfield.TextInputEditText etTestId;
    com.google.android.material.appbar.MaterialToolbar toolbar;
    Button btnSearch;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userTestsStorage, usersRef;

    String cont = "Start Test";

    public FindUserTestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userTestsStorage = database.getReference("User Tests Storage");

        usersRef = database.getReference("Users");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_user_test, container, false);

        btnSearch = rootView.findViewById(R.id.btnSearch);
        etTestId = rootView.findViewById(R.id.etTestId);
        root = rootView.findViewById(R.id.root);

        btnSearch.setOnClickListener(this::onClickSearch);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> ((TestMakerActivity) requireContext()).onBackPressed());

        return rootView;
    }

    protected void onClickSearch(View view) {

        if (TextUtils.isEmpty(Objects.requireNonNull(etTestId.getText()).toString()))
        {
            Snackbar.make(root,"Enter test ID please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etTestId.getText().toString().length() < 5)
        {
            Snackbar.make(root,"Test ID must be more than 5 characters", Snackbar.LENGTH_LONG).show();
            return;
        }

        userTestsStorage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(etTestId.getText().toString()))
                {
                    String testName = snapshot.child(etTestId.getText().toString()).child("testName").getValue(String.class);
                    String creatorId = snapshot.child(etTestId.getText().toString()).child("creatorID").getValue(String.class);

                    assert creatorId != null;
                    if (creatorId.equals(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {
                        Snackbar.make(root,"Nice try, we know this is your test", Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    usersRef.child(creatorId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String creatorName = snapshot.child("fullName").getValue(String.class);

                            new AlertDialog.Builder(requireContext())
                                    .setTitle("Search result")
                                    .setMessage("Test Name: " + testName +  "\nBy " + creatorName)
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(cont, (arg0, arg1) ->
                                            ((TestMakerActivity) requireContext()).setFragCompleteOtherTest(etTestId.getText().toString())).create().show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Snackbar.make(root,"We didn't find anything :(", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}