package com.hg39.helleng;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.hg39.helleng.MainActivity.version;
import static java.security.AccessController.getContext;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference appDataRef;
    FirebaseDatabase database;
    String actualVersion, installedVersion;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        installedVersion = version;

        if (!isOnline()) {
            new AlertDialog.Builder(SplashScreen.this)
                    .setTitle("No internet connection")
                    .setMessage("The application may not work completely and correctly, are you sure want to continue?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(SplashScreen.this,MainActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        appDataRef = database.getReference().child("App Data").getRef();

        if (currentUser != null) {
            Objects.requireNonNull(mAuth.getCurrentUser()).reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                        finish();
                    } else {
                        //something
                    }
                }
            });
        } else {
            startActivity(new Intent(SplashScreen.this,LoginActivity.class));
            finish();
        }

        /*appDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                actualVersion = snapshot.child("version").getValue(String.class);
                start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void start() {
        if (actualVersion.equalsIgnoreCase(installedVersion)) {
            if (currentUser != null) {
                mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(SplashScreen.this,MainActivity.class));
                            finish();

                        } else {
                            //something
                        }
                    }
                });
            } else {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        } else {
            Intent intent = new Intent(SplashScreen.this,ChillZoneActivty.class);
            intent.putExtra("version", version);
            intent.putExtra("actualVersion", actualVersion);
            startActivity(intent);
            finish();
        }
    }
}
