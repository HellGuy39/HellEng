package com.hg39.helleng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    FirebaseAuth.AuthStateListener mAuthListener;
    ConstraintLayout root;

    Button btnSettings;

    EditText etEmail,etPassword;
    Button btnSignIn,btnRegister,btnForgotPass;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_login);

        root = findViewById(R.id.rootElement);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnForgotPass = findViewById(R.id.btnForgotPass);
        btnSettings = findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        loadingBar = new ProgressDialog(this);

        mAuthListener = firebaseAuth -> {
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
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSignIn)
        {
            //Проверка на вшивость
            if (TextUtils.isEmpty(etEmail.getText().toString()))
            {
                Snackbar.make(root,"Enter your email please",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(etPassword.getText().toString()))
            {
                Snackbar.make(root,"Enter your password please",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (etPassword.getText().toString().length() < 5)
            {
                Snackbar.make(root,"Password must be more than 5 characters", Snackbar.LENGTH_LONG).show();
                return;
            }

            loadingBar.setTitle("Logging...");
            loadingBar.setMessage("Trying to log into your account, please wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            singing(etEmail.getText().toString(),etPassword.getText().toString());
        }
        else if (view.getId() == R.id.btnRegister)
        {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
        else if (view.getId() == R.id.btnForgotPass)
        {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        }
        else if (view.getId() == R.id.btnSettings)
        {
            startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
        }
    }
    public void singing(String email , String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful())
            {
                //Notification Update
                String currentUserId = mAuth.getCurrentUser().getUid();
                final String[] deviceToken = new String[1];
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            deviceToken[0] = task.getResult();

                            users.child(currentUserId).child("deviceToken").setValue(deviceToken[0]).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful())
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(LoginActivity.this,"Back to the HellEng!",Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                }
                                else
                                {
                                    loadingBar.dismiss();
                                    Snackbar.make(root, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            loadingBar.dismiss();
                            Snackbar.make(root, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else
            {
                loadingBar.dismiss();
                Snackbar.make(root, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                //Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void setLanguage() {
        SharedPreferences sp = getSharedPreferences(CONFIG_FILE, 0);
        String language = sp.getString(CONFIG_LANGUAGE, "en");

        Locale locale = new Locale(language);

        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }

}