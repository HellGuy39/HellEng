package com.hg39.helleng;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class TestProgressControl {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersTestsProgress;

    void SaveTestProgress(String group, String testName, String progress) {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersTestsProgress = database.getReference("Users Tests Progress");

        usersTestsProgress.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(group).child(testName).setValue(progress);

    }
}
