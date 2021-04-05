package com.hg39.helleng;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WebStatusControl {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    void setWebStatus(String state) {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        if (mAuth.getCurrentUser() != null) {
            if (state.equals("Online")) {
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("webStatus").setValue("Online");
            } else if (state.equals("Offline")) {
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("webStatus").setValue("Offline");
            }
        }
    }
}
