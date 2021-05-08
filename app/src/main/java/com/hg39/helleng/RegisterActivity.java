package com.hg39.helleng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hg39.helleng.Models.RegUser;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout root;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    //DatabaseReference userTestsProgress;

    Button btnRegister;
    EditText etEmail, etPassword, etFirstName, etLastName;

    RegUser regUser = new RegUser();

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_register);

        root = findViewById(R.id.root);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        //userTestsProgress = database.getReference("Users Tests Progress");

        loadingBar = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        //Проверка на вшивость
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            Snackbar.make(root, "Enter your email please", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            Snackbar.make(root, "Enter your password please", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            Snackbar.make(root, "Enter your first name please", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            Snackbar.make(root, "Enter your last name please", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etPassword.getText().toString().length() < 5) {
            Snackbar.make(root, "Password must be more than 5 characters", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etPassword.getText().toString().length() < 3) {
            Snackbar.make(root, "First name must be more than 3 characters", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etPassword.getText().toString().length() < 3) {
            Snackbar.make(root, "Last name must be more than 3 characters", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (v.getId() == R.id.btnRegister) {
            registration(etEmail.getText().toString(), etPassword.getText().toString(),
                    etFirstName.getText().toString(), etLastName.getText().toString());

            loadingBar.setTitle("Registration...");
            loadingBar.setMessage("Creating new account, please wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

        }
    }

    public void registration(String email, String password, String firstName, String lastName) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(RegisterActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();

                    // Текущее время
                    Date currentDate = new Date();
                    // Форматирование времени как "день.месяц.год"
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String dateText = dateFormat.format(currentDate);

                    regUser.setEmail(email);
                    regUser.setPassword(password);
                    regUser.setFirstName(firstName);
                    regUser.setLastName(lastName);
                    regUser.setFullName(firstName + " " + lastName);
                    regUser.setRegisterDate(dateText);

                    users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .setValue(regUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

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
                                                        Toast.makeText(RegisterActivity.this, "Welcome to the club buddy!"/*"Successfully"*/,Toast.LENGTH_SHORT).show();

                                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                        finish();
                                                    }
                                                });

                                            }
                                        }
                                    });

                                    //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    //finish();

                                }
                            });
                } else {
                    loadingBar.dismiss();
                    //Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Snackbar.make(root, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Snackbar.make(root, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
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
