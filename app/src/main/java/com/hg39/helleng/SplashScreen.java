package com.hg39.helleng;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference appDataRef;
    FirebaseDatabase database;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isOnline())
        {
            new AlertDialog.Builder(SplashScreen.this)
                    .setTitle("No internet connection")
                    .setMessage("The application may not work completely and correctly, are you sure want to continue?")
                    .setNegativeButton(android.R.string.no, (dialog, which) -> finish())
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                        finish();
                    })
                    .create()
                    .show();
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        appDataRef = database.getReference().child("App Data").getRef();

        if (currentUser != null)
        {
            Objects.requireNonNull(mAuth.getCurrentUser()).reload().addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            startActivity(new Intent(SplashScreen.this,LoginActivity.class));
            finish();
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
