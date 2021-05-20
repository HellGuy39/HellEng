package com.hg39.helleng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity;

import java.util.Locale;

import static com.hg39.helleng.App.CHANNEL_1_ID;
import static com.hg39.helleng.App.CHANNEL_2_ID;
import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;
import static com.hg39.helleng.SettingsActivity.CONFIG_STARTING_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    //Constants party
    //by invitation only
    public static final String groupGrammar = "Grammar";
    public static final String groupVocabulary = "Vocabulary";
    public static final String groupAudition = "Audition";

    public static final String presentSimple = "Present Simple";
    public static final String pastSimple = "Past Simple";
    public static final String futureSimple = "Future Simple";

    public static final String food = "Food";
    public static final String furniture = "Furniture";
    public static final String schoolSupplies = "School supplies";

    public static final String humor = "Humor";
    public static final String superman = "Superman";

    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "msg_channel";
    public static final String CHANNEL_DESC = "Messages";

    public static final String version = "0.2.1";

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    BottomNavigationView bottomNav;

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
    Fragment fragSelectedAudition;
    Fragment fragFriendRequests;
    Fragment fragSelectedCreateTest;

    WebStatusControl webStatusControl = new WebStatusControl();

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //localeDelegate.setLocale(this, locale);

        //Фрагментики
        fragEditProfile = new EditProfileFragment();
        fragHome = new HomeFragment();
        fragChat = new ChatFragment();
        fragCourses = new CoursesFragment();
        fragProfile = new ProfileFragment();
        fragSelectedVocabulary = new VocabularySelectedFragment();
        fragSelectedGrammar = new GrammarSelectedFragment();
        fragSelectedAudition = new AuditionSelectedFragment();
        fragFriends = new FriendsFragment();
        fragFindFriends = new FindFriendsFragment();
        fragFriendRequests = new RequestsFragment();
        fragSelectedCreateTest = new CreateTestSelectedFragment();
        //fragViewOtherProfile = new ViewOtherProfileFragment();

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Вызов фрагмента home по умолчанию со старта приложения
        //Fragment fragHome = new HomeFragment();
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragHome)
                .commit();*/

        setStartingFragment();
        /*notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_chat_24)
                .setContentTitle("Channel 1")
                .setContentText("Are you gay?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);

        Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_chat_24)
                .setContentTitle("Channel 2")
                .setContentText("No, I don't!")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification2);*/

        FirebaseMessaging.getInstance().subscribeToTopic("topic");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
        //displayNotification();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        webStatusControl.setWebStatus("Online");
        
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

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

                    assert selectedFragment != null;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();

                    return true;
                }
            };

    protected void setStartingFragment() {

        sp = getSharedPreferences(CONFIG_FILE, 0);
        String stFrag = sp.getString(CONFIG_STARTING_FRAGMENT, "Home");

        if (stFrag.equalsIgnoreCase("Home"))
        {
            setFragHome();
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
        else if (stFrag.equalsIgnoreCase("Chats"))
        {
            setFragChats();
            bottomNav.setSelectedItemId(R.id.nav_chat);
        }
        else if (stFrag.equalsIgnoreCase("Courses"))
        {
            setFragCourses();
            bottomNav.setSelectedItemId(R.id.nav_courses);
        }
        else if (stFrag.equalsIgnoreCase("Profile"))
        {
            setFragProfile();
            bottomNav.setSelectedItemId(R.id.nav_profile);
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    protected void setLanguage() {
        sp = getSharedPreferences(CONFIG_FILE, 0);
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


    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

        /*if (userOnMainMenu) {
            finish();
        } else if (userOnEditProfile) {
            outEditProfileFragment();
        } else if (userOnCoursesSelection) {
            setFragCourses();
        } else {
            super.onBackPressed();
        }*/

    }

    protected void setFragSelectedCreateTest() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragSelectedCreateTest)
                .commit();
    }

    protected void setFragFriendRequests() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragFriendRequests)
                .commit();
    }

    protected void setFragViewOtherProfile(String userKey) {
        fragViewOtherProfile = new ViewOtherProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userKey",userKey);
        fragViewOtherProfile.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragViewOtherProfile)
                .commit();
    }

    protected void setFragCourses() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragCourses)
                .commit();
    }

    protected void setFragSelectedVocabulary() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragSelectedVocabulary)
                .commit();
    }

    protected void setFragSelectedAudition() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragSelectedAudition)
                .commit();
    }

    protected void setFragHome(){
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragHome)
                .commit();
    }

    protected void setFragFriends() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragFriends)
                .commit();
    }

    protected void setFragFindFriends() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,fragFindFriends)
                .commit();
    }

    protected void setFragSelectedGrammar() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragSelectedGrammar)
                .commit();
    }

    protected void setFragChats() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragChat)
                .commit();
    }

    protected void setEditProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragEditProfile)
                .commit();
    }
    protected void setFragProfile() { // Old Name outEditProfileFragment
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragProfile)
                .commit();
    }

    public void signOut() {
        webStatusControl.setWebStatus("Offline");
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