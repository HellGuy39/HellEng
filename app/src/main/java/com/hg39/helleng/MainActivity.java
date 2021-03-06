package com.hg39.helleng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_FILE = "User Data";
    public static final String PREFS_TEST1 = "Test1info";
    public static final String PREFS_TEST2 = "Test2info";
    public static final String PREFS_TEST3 = "Test3info";
    public static final String PREFS_TEST4 = "Test4info";
    public static final String PREFS_TEST5 = "Test5info";
    public static final String PREFS_TEST6 = "Test6info";
    public static final String PREFS_TEST7 = "Test7info";
    public static final String PREFS_TEST8 = "Test8info";
    public static final String PREFS_TEST9 = "Test9info";
    public static final String PREFS_TEST10 = "Test10info";
    public static final String PREFS_TEST11 = "Test11info";
    public static final String PREFS_TEST12 = "Test12info";
    public static final String PREFS_TEST13 = "Test13info";
    public static final String PREFS_TEST14 = "Test14info";
    public static final String PREFS_TEST15 = "Test15info";
    public static final String PREFS_TEST16 = "Test16info";
    public static final String PREFS_USER_NAME = "UserName";
    public static final String PREFS_USER_FIRST_NAME = "UserFirstName";
    public static final String PREFS_USER_LAST_NAME = "UserLastName";
    public static final String PREFS_USER_STATUS = "UserStatus";


    private SharedPreferences userData;
    private SharedPreferences.Editor prefEditor;
    private int inf1,inf2,inf3,inf4,inf5,inf6,inf7,inf8,inf9,inf10,inf11,inf12,inf13,inf14,inf15,inf16;
    private String userName,strFName,strLName,strStatus;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        user.setWebStatus("Online");

        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("webStatus").setValue(user.getWebStatus());

        //userDB.setOnline("Online");
        //users.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("webStatus").setValue(userDB.getOnline());

        /*myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        userData = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        userName = userData.getString(PREFS_USER_FIRST_NAME,"user");
        inf1 = userData.getInt(PREFS_TEST1,0);
        inf2 = userData.getInt(PREFS_TEST2,0);
        inf3 = userData.getInt(PREFS_TEST3,0);
        inf4 = userData.getInt(PREFS_TEST4,0);
        inf5 = userData.getInt(PREFS_TEST5,0);
        inf6 = userData.getInt(PREFS_TEST6,0);
        inf7 = userData.getInt(PREFS_TEST7,0);
        inf8 = userData.getInt(PREFS_TEST8,0);
        inf9 = userData.getInt(PREFS_TEST9,0);
        inf10 = userData.getInt(PREFS_TEST10,0);
        inf11 = userData.getInt(PREFS_TEST11,0);
        inf12 = userData.getInt(PREFS_TEST12,0);
        inf13 = userData.getInt(PREFS_TEST13,0);
        inf14 = userData.getInt(PREFS_TEST14,0);
        inf15 = userData.getInt(PREFS_TEST15,0);
        inf16 = userData.getInt(PREFS_TEST16,0);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Вызов фрагмента home по умолчанию со старта приложения
        Fragment fragHome = new HomeFragment();

        Bundle userData = new Bundle();
        userData.putString("userData", userName);
        fragHome.setArguments(userData);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragHome)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        user.setWebStatus("Online");

        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("webStatus").setValue(user.getWebStatus());
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Fragment fragHome = new HomeFragment();
                    Fragment fragChat = new ChatFragment();
                    Fragment fragCourses = new CoursesFragment();
                    Fragment fragProfile = new ProfileFragment();

                    Bundle argsTestsInfo = new Bundle();
                    argsTestsInfo.putInt("inf1", inf1);
                    argsTestsInfo.putInt("inf2", inf2);
                    argsTestsInfo.putInt("inf3", inf3);
                    argsTestsInfo.putInt("inf4", inf4);
                    argsTestsInfo.putInt("inf5", inf5);
                    argsTestsInfo.putInt("inf6", inf6);
                    argsTestsInfo.putInt("inf7", inf7);
                    argsTestsInfo.putInt("inf8", inf8);
                    argsTestsInfo.putInt("inf9", inf9);
                    argsTestsInfo.putInt("inf10", inf10);
                    argsTestsInfo.putInt("inf11", inf11);
                    argsTestsInfo.putInt("inf12", inf12);
                    argsTestsInfo.putInt("inf13", inf13);
                    argsTestsInfo.putInt("inf14", inf14);
                    argsTestsInfo.putInt("inf15", inf15);
                    argsTestsInfo.putInt("inf16", inf16);

                    strFName = userData.getString(PREFS_USER_FIRST_NAME,"First Name");
                    strLName = userData.getString(PREFS_USER_LAST_NAME, "Last Name");
                    strStatus = userData.getString(PREFS_USER_STATUS,"Status");

                    Bundle argsUserInf = new Bundle();
                    argsUserInf.putString("userFName", strFName);
                    argsUserInf.putString("userLName", strLName);
                    argsUserInf.putString("userStatus", strStatus);

                    Bundle userData = new Bundle();
                    userData.putString("userData", userName);

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            fragHome.setArguments(userData);
                            selectedFragment = fragHome;
                            break;
                        case R.id.nav_chat:
                            selectedFragment = fragChat;
                            break;
                        case R.id.nav_courses:
                            fragCourses.setArguments(argsTestsInfo);
                            selectedFragment = fragCourses;
                            break;
                        case R.id.nav_profile:
                            fragProfile.setArguments(argsUserInf);
                            selectedFragment = fragProfile;
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();

                    return true;
                }
            };
    protected void setEditProfileFragment() {

        Bundle argsUserInf = new Bundle();
        argsUserInf.putString("userFName", strFName);
        argsUserInf.putString("userLName", strLName);
        argsUserInf.putString("userStatus", strStatus);

        Fragment fragEditProfile = new EditProfileFragment();
        fragEditProfile.setArguments(argsUserInf);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragEditProfile)
                .commit();
    }
    protected void outEditProfileFragment() {

        strFName = userData.getString(PREFS_USER_FIRST_NAME,"First Name");
        strLName = userData.getString(PREFS_USER_LAST_NAME, "Last Name");
        strStatus = userData.getString(PREFS_USER_STATUS,"Status");

        Bundle argsUserInf = new Bundle();
        argsUserInf.putString("userFName", strFName);
        argsUserInf.putString("userLName", strLName);
        argsUserInf.putString("userStatus", strStatus);

        Fragment fragProfile = new ProfileFragment();
        fragProfile.setArguments(argsUserInf);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragProfile)
                .commit();
    }
    protected void saveProfileInfo(String status, String firstName, String lastName) {
        prefEditor = userData.edit();
        prefEditor.putString(PREFS_USER_STATUS,status);
        prefEditor.putString(PREFS_USER_FIRST_NAME,firstName);
        prefEditor.putString(PREFS_USER_LAST_NAME,lastName);
        prefEditor.apply();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

        user.setWebStatus("Offline");

        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("webStatus").setValue(user.getWebStatus());

    }
}