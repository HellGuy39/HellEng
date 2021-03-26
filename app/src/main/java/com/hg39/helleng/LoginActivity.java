package com.hg39.helleng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.hg39.helleng.Models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public LoginActivity loginActivity;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    FirebaseAuth.AuthStateListener mAuthListener;
    ConstraintLayout root;

    private EditText etEmail,etPassword;
    private Button btnSignIn,btnRegister;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        root = findViewById(R.id.rootElement);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Toast.makeText(LoginActivity.this,"Signed in",Toast.LENGTH_SHORT)
                            .show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                    } else {
                    //User is signed out
                    Toast.makeText(LoginActivity.this,"Signed out",Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };

        //FirebaseUser user = mAuth.getCurrentUser();

        /*if (user != null) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        /*if(currentUser != null){
            reload();
        }*/

    }

    @Override
    public void onClick(View view) {

        //Проверка на вшивость
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            Snackbar.make(root,"Enter your email please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            Snackbar.make(root,"Enter your password please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etPassword.getText().toString().length() < 5) {
            Snackbar.make(root,"Password must be more than 5 characters", Snackbar.LENGTH_LONG).show();
            return;
        }
        //


        if (view.getId() == R.id.btnSignIn) {
            singing(etEmail.getText().toString(),etPassword.getText().toString());
        } else if (view.getId() == R.id.btnRegister) {
            registration(etEmail.getText().toString(),etPassword.getText().toString());
        }
    }

    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    //updateUI(mAuth.getCurrentUser());
                    Toast.makeText(LoginActivity.this,
                            "Reload successful!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Failed to reload user.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void singing(String email , String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration(String email , String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();

                    // Текущее время
                    Date currentDate = new Date();
                    // Форматирование времени как "день.месяц.год"
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String dateText = dateFormat.format(currentDate);

                    //user = new User();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setRegisterDate(dateText);

                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make(root,"User add success", Snackbar.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(root,"Error. " + e.getMessage(),Snackbar.LENGTH_LONG);
            }
        });
    }
}