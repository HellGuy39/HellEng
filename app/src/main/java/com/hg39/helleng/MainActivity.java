package com.hg39.helleng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //Constants party
    //by invitation only
    public static final String groupGrammar = "Grammar";
    public static final String groupVocabulary = "Vocabulary";
    public static final String groupAudacity = "Audition";
    public static final String presentSimple = "Present Simple";
    public static final String pastSimple = "Past Simple";
    public static final String futureSimple = "Future Simple";
    public static final String food = "Food";
    public static final String furniture = "Furniture";
    public static final String schoolSupplies = "School Supplies";

    private SharedPreferences userData;
    private String userName,strFName,strLName,strStatus;

    boolean userOnMainMenu,userOnCoursesSelection,userOnEditProfile;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    Fragment fragHome;
    Fragment fragChat;
    Fragment fragCourses;
    Fragment fragProfile;
    Fragment fragEditProfile;
    Fragment fragSelectedVocabulary;
    Fragment fragSelectedGrammar;
    Fragment fragFriends;
    Fragment fragFindFriends;
    Fragment fragViewOtherProfile;

    WebStatusControl webStatusControl = new WebStatusControl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        userOnMainMenu = true; userOnCoursesSelection = false; userOnEditProfile = false;

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //Фрагментики
        fragEditProfile = new EditProfileFragment();
        fragHome = new HomeFragment();
        fragChat = new ChatFragment();
        fragCourses = new CoursesFragment();
        fragProfile = new ProfileFragment();
        fragSelectedVocabulary = new VocabularySelectedFragment();
        fragSelectedGrammar = new GrammarSelectedFragment();
        fragFriends = new FriendsFragment();
        fragFindFriends = new FindFriendsFragment();
        fragViewOtherProfile = new ViewOtherProfileFragment();

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
        webStatusControl.setWebStatus("Online");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webStatusControl.setWebStatus("Online");
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    userOnMainMenu = true; userOnCoursesSelection = false; userOnEditProfile = false;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = fragHome;
                            break;
                        case R.id.nav_chat:
                            selectedFragment = fragChat;
                            break;
                        case R.id.nav_courses:
                            selectedFragment = fragCourses;
                            break;
                        case R.id.nav_profile:
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

    protected void setFragViewOtherProfile(String userKey) {
        Bundle bundle = new Bundle();
        bundle.putString("userKey",userKey);
        fragViewOtherProfile.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragViewOtherProfile)
                .commit();
    }

    protected void setFragCourses() {
        userOnMainMenu = true; userOnCoursesSelection = false; userOnEditProfile = false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragCourses)
                .commit();
    }

    protected void setFragSelectedVocabulary() {
        userOnMainMenu = false; userOnCoursesSelection = true; userOnEditProfile = false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragSelectedVocabulary)
                .commit();
    }

    protected void setFragHome(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragHome)
                .commit();
    }

    protected void setFragFriends() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragFriends)
                .commit();
    }

    protected void setFragFindFriends() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragFindFriends)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (userOnMainMenu) {
            finish();
        } else if (userOnEditProfile) {
            outEditProfileFragment();
        } else if (userOnCoursesSelection) {
            setFragCourses();
        } else {
            super.onBackPressed();
        }

    }

    protected void setFragSelectedGrammar() {
        userOnMainMenu = false; userOnCoursesSelection = true; userOnEditProfile = false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragSelectedGrammar)
                .commit();
    }

    protected void setFragChat() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragChat)
                .commit();
    }

    protected void setEditProfileFragment() {
        userOnMainMenu = false; userOnCoursesSelection = false; userOnEditProfile = true;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragEditProfile)
                .commit();
    }
    protected void outEditProfileFragment() {
        userOnMainMenu = true; userOnCoursesSelection = false; userOnEditProfile = false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragProfile)
                .commit();
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
        webStatusControl.setWebStatus("Offline");

    }

    @Override
    protected void onStop() {
        super.onStop();
        webStatusControl.setWebStatus("Offline");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webStatusControl.setWebStatus("Offline");

    }
}