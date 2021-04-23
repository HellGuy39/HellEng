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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.hg39.helleng.MainActivity.version;

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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        appDataRef = database.getReference().child("App Data").getRef();

        appDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                actualVersion = snapshot.child("version").getValue(String.class);
                start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
