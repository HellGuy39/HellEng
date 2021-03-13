package com.hg39.helleng;

import android.app.ActionBar;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GrammarSelectedFragment extends Fragment implements View.OnClickListener{

    Button btnPrSimpleTest,btnPsSimpleTest,btnFtSimpleTest;
    TextView tvCompletedPrSimple,tvCompletedPsSimple,tvCompletedFtSimple;
    TextView tvPrSimple,tvPsSimple,tvRFtSimple;
    int completedPrSimple,completedPsSimple,completedFrSimple;
    Intent intentTenses;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentTenses = new Intent(getContext(), TensesActivity.class);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");


        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        /*1 - furniture
         *2 - school supplies
         *3 - food
         *4 - present
         *5 - past
         *6 - future
         */

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedPrSimple = dataSnapshot.child("test4Interest").getValue(int.class);
                completedPsSimple = dataSnapshot.child("test5Interest").getValue(int.class);
                completedFrSimple = dataSnapshot.child("test6Interest").getValue(int.class);

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

        tvCompletedPrSimple.setText(String.format("%d%%", completedPrSimple));
        tvCompletedPsSimple.setText(String.format("%d%%", completedPsSimple));
        tvCompletedFtSimple.setText(String.format("%d%%", completedFrSimple));

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

        switch (v.getId()) {

            case R.id.btnPrSimpleTest:
                intentTenses.putExtra("testType", 4);
                startActivity(intentTenses);
                break;

            case R.id.btnSchoolSuppliesTest:
                intentTenses.putExtra("testType", 2);
                startActivity(intentTenses);
                break;

            case R.id.btnFoodTest:
                intentTenses.putExtra("testType", 3);
                startActivity(intentTenses);
                break;

            default:
                break;
        }
    }
}
