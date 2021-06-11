package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText etEmail;
    Button btnSent;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.etEmail);
        btnSent = findViewById(R.id.btnSent);

        mAuth = FirebaseAuth.getInstance();


        btnSent.setOnClickListener(v -> {

            String userEmail = Objects.requireNonNull(etEmail.getText()).toString();

            if (TextUtils.isEmpty(userEmail)) {
                Toast.makeText(ResetPasswordActivity.this, "Maybe you write something?", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Check your Email!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    } else {
                        String message = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(ResetPasswordActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}