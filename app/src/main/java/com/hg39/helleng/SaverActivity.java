package com.hg39.helleng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hg39.helleng.Models.User;

import static com.hg39.helleng.MainActivity.PREFS_FILE;
import static com.hg39.helleng.MainActivity.PREFS_TEST1;
import static com.hg39.helleng.MainActivity.PREFS_TEST10;
import static com.hg39.helleng.MainActivity.PREFS_TEST11;
import static com.hg39.helleng.MainActivity.PREFS_TEST12;
import static com.hg39.helleng.MainActivity.PREFS_TEST13;
import static com.hg39.helleng.MainActivity.PREFS_TEST14;
import static com.hg39.helleng.MainActivity.PREFS_TEST15;
import static com.hg39.helleng.MainActivity.PREFS_TEST16;
import static com.hg39.helleng.MainActivity.PREFS_TEST2;
import static com.hg39.helleng.MainActivity.PREFS_TEST3;
import static com.hg39.helleng.MainActivity.PREFS_TEST4;
import static com.hg39.helleng.MainActivity.PREFS_TEST5;
import static com.hg39.helleng.MainActivity.PREFS_TEST6;
import static com.hg39.helleng.MainActivity.PREFS_TEST7;
import static com.hg39.helleng.MainActivity.PREFS_TEST8;
import static com.hg39.helleng.MainActivity.PREFS_TEST9;

public class SaverActivity extends AppCompatActivity {

    SharedPreferences testsInfo;
    SharedPreferences.Editor prefEditor;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saver);

        Bundle args = getIntent().getExtras();

        int testType = args.getInt("testType");
        int result1 = args.getInt("result1");
        int result2 = args.getInt("result2");
        int result3 = args.getInt("result3");
        int result4 = args.getInt("result4");
        int result5 = args.getInt("result5");
        int result6 = args.getInt("result6");
        int result7 = args.getInt("result7");
        int result8 = args.getInt("result8");
        int result9 = args.getInt("result9");
        int result10 = args.getInt("result10");
        int result11 = args.getInt("result11");
        int result12 = args.getInt("result12");
        int result13 = args.getInt("result13");
        int result14 = args.getInt("result14");
        int result15 = args.getInt("result15");
        int result16 = args.getInt("result16");

        testsInfo = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        prefEditor = testsInfo.edit();
        switch (testType) {
            case 1:
                prefEditor.putInt(PREFS_TEST1, result1);
                break;
            case 2:
                prefEditor.putInt(PREFS_TEST2, result2);
                break;
            case 3:
                prefEditor.putInt(PREFS_TEST3, result3);
                break;
            case 4:
                prefEditor.putInt(PREFS_TEST4, result4);
                break;
            case 5:
                prefEditor.putInt(PREFS_TEST5, result5);
                break;
            case 6:
                prefEditor.putInt(PREFS_TEST6, result6);
                break;
            case 7:
                prefEditor.putInt(PREFS_TEST7, result7);
                break;
            case 8:
                prefEditor.putInt(PREFS_TEST8, result8);
                break;
            case 9:
                prefEditor.putInt(PREFS_TEST9, result9);
                break;
            case 10:
                prefEditor.putInt(PREFS_TEST10, result10);
                break;
            case 11:
                prefEditor.putInt(PREFS_TEST11, result11);
                break;
            case 12:
                prefEditor.putInt(PREFS_TEST12, result12);
                break;
            case 13:
                prefEditor.putInt(PREFS_TEST13, result13);
                break;
            case 14:
                prefEditor.putInt(PREFS_TEST14, result14);
                break;
            case 15:
                prefEditor.putInt(PREFS_TEST15, result15);
                break;
            case 16:
                prefEditor.putInt(PREFS_TEST16, result16);
                break;

        }
        prefEditor.apply();

        Intent intent = new Intent(SaverActivity.this,MainActivity.class);
        startActivity(intent);

    }
}