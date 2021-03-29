package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.hg39.helleng.MainActivity.PREFS_USER_NAME;

public class HomeFragment extends Fragment {

    TextView textViewGreeting,date;
    TextView dateMonth,dateDayOfWeek;
    //TextView textViewUnfinishedTest1,textViewUnfinishedTest2,textViewUnfinishedTest3;
    String userName;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        FirebaseUser userF = mAuth.getInstance().getCurrentUser();
        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userName = dataSnapshot.child("firstName").getValue(String.class);

                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_home,container,false);
        dateMonth = rootView.findViewById(R.id.dateMonth);
        dateDayOfWeek = rootView.findViewById(R.id.dateDayOfWeek);
        date = rootView.findViewById(R.id.date);
        textViewGreeting = rootView.findViewById(R.id.textViewGreeting);
        //textViewUnfinishedTest1 = rootView.findViewById(R.id.textViewUnfinishedTest1);
        //textViewUnfinishedTest2 = rootView.findViewById(R.id.textViewUnfinishedTest2);
        //textViewUnfinishedTest3 = rootView.findViewById(R.id.textViewUnfinishedTest3);

        Calendar calendar = Calendar.getInstance();
        dateMonth.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH));
        dateDayOfWeek.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG_FORMAT,Locale.ENGLISH));

        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd"/*"dd.MM.yyyy"*/, Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        // Форматирование времени как "часы:минуты:секунды"
        //DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        //String timeText = timeFormat.format(currentDate);

        date.setText(dateText);
        //textViewTime.setText(timeText);

        textViewGreeting.setText("Welcome back, " + userName + "!");

        return rootView;
    }

    private void updateUI() {
        textViewGreeting.setText("Welcome back, " + userName + "!");
    }

}
