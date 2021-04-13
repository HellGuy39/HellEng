package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.hg39.helleng.MainActivity.futureSimple;
import static com.hg39.helleng.MainActivity.groupGrammar;
import static com.hg39.helleng.MainActivity.pastSimple;
import static com.hg39.helleng.MainActivity.presentSimple;

public class GrammarSelectedFragment extends Fragment implements View.OnClickListener{

    TextView tvCompletedPrSimple,tvCompletedPsSimple,tvCompletedFtSimple;
    TextView tvPrSimple,tvPsSimple,tvRFtSimple;
    String completedPrSimple,completedPsSimple,completedFrSimple;
    Intent intentTenses;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersTestsProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentTenses = new Intent(getContext(), TensesActivity.class);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        usersTestsProgress = database.getReference("Users Tests Progress");

        /*1 - furniture
         *2 - school supplies
         *3 - food
         *4 - present
         *5 - past
         *6 - future
         */

        usersTestsProgress.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(groupGrammar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedPrSimple = dataSnapshot.child(presentSimple).getValue(String.class);
                completedPsSimple = dataSnapshot.child(pastSimple).getValue(String.class);
                completedFrSimple = dataSnapshot.child(futureSimple).getValue(String.class);

                updateUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
                inflater.inflate(R.layout.fragment_grammar_selected,container,false);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).setFragCourses();
            }
        });

        rootView.findViewById(R.id.btnPrSimpleTest).setOnClickListener(this);
        rootView.findViewById(R.id.btnPsSimpleTest).setOnClickListener(this);
        rootView.findViewById(R.id.btnFtSimpleTest).setOnClickListener(this);

        //Tests completed info
        tvCompletedPrSimple = rootView.findViewById(R.id.tvCompletedPrSimple);
        tvCompletedPsSimple = rootView.findViewById(R.id.tvCompletedPsSimple);
        tvCompletedFtSimple = rootView.findViewById(R.id.tvCompletedFtSimple);
        //Tests tittles
        tvPrSimple = rootView.findViewById(R.id.tvFurniture);
        tvPsSimple = rootView.findViewById(R.id.tvSchoolSupplies);
        tvRFtSimple= rootView.findViewById(R.id.tvFood);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUI();

    }

    private void updateUI(){

        if (completedFrSimple == null) {
            completedFrSimple = "0";
        }

        if (completedPsSimple == null) {
            completedPsSimple = "0";
        }

        if (completedPrSimple == null) {
            completedPrSimple = "0";
        }

        tvCompletedPrSimple.setText(completedPrSimple + " %");
        tvCompletedPsSimple.setText(completedPsSimple + " %");
        tvCompletedFtSimple.setText(completedFrSimple + " %");

    }

    @Override
    public void onClick(View v) {

        /*1 - furniture
         *2 - school supplies
         *3 - food
         *4 - present
         *5 - past
         *6 - future
         */

        if (v.getId() == R.id.btnPrSimpleTest) {
            intentTenses.putExtra("testType", 4);
            startActivity(intentTenses);
        } else if (v.getId() == R.id.btnPsSimpleTest) {
            intentTenses.putExtra("testType", 5);
            startActivity(intentTenses);
        } else if (v.getId() == R.id.btnFtSimpleTest) {
            intentTenses.putExtra("testType", 6);
            startActivity(intentTenses);
        }
    }
}
