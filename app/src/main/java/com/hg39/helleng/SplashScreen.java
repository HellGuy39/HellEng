package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        startActivity(new Intent(SplashScreen.this,MainActivity.class));

                        /*Toast.makeText(SplashScreen.this,
                                "Reload successful!",
                                Toast.LENGTH_SHORT).show();*/
                        finish();

                    } else {

                        Toast.makeText(SplashScreen.this,
                                "Failed to reload user.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            startActivity(new Intent(SplashScreen.this,LoginActivity.class));
            finish();
        }
    }
}
