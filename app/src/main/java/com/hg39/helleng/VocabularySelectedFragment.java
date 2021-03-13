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
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VocabularySelectedFragment extends Fragment implements View.OnClickListener{

    Button btnFrnTest,btnFoodTest,btnSchSupTest;
    TextView tvCompletedFurniture,tvCompletedFood,tvCompletedSchoolSupplies;
    TextView tvFurniture,tvFood,tvSchoolSupplies;
    int completedInterestFurniture,completedInterestSchoolSupplies,completedInterestFood;
    Intent intentWords;
    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        intentWords = new Intent(getContext(), WordsActivity.class);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        //users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("test1Interest").get();

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedInterestFurniture = dataSnapshot.child("test1Interest").getValue(int.class);
                completedInterestSchoolSupplies = dataSnapshot.child("test2Interest").getValue(int.class);
                completedInterestFood = dataSnapshot.child("test3Interest").getValue(int.class);

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
                inflater.inflate(R.layout.fragment_vocabulary_selected,container,false);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).setFragCourses();
            }
        });

        rootView.findViewById(R.id.btnFurnitureTest).setOnClickListener(this);
        rootView.findViewById(R.id.btnSchoolSuppliesTest).setOnClickListener(this);
        rootView.findViewById(R.id.btnFoodTest).setOnClickListener(this);

        //Tests completed info
        tvCompletedFurniture = rootView.findViewById(R.id.tvCompletedFurniture);
        tvCompletedSchoolSupplies = rootView.findViewById(R.id.tvCompletedSchoolSupplies);
        tvCompletedFood = rootView.findViewById(R.id.tvCompletedFood);
        //Tests tittles
        tvFurniture = rootView.findViewById(R.id.tvFurniture);
        tvSchoolSupplies = rootView.findViewById(R.id.tvSchoolSupplies);
        tvFood = rootView.findViewById(R.id.tvFood);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUI();

    }

    private void updateUI(){

        tvCompletedFurniture.setText(String.format("%d%%", completedInterestFurniture));
        tvCompletedSchoolSupplies.setText(String.format("%d%%", completedInterestSchoolSupplies));
        tvCompletedFood.setText(String.format("%d%%", completedInterestFood));

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

            case R.id.btnFurnitureTest:
                intentWords.putExtra("testType", 1);
                startActivity(intentWords);
                break;

            case R.id.btnSchoolSuppliesTest:
                intentWords.putExtra("testType", 2);
                startActivity(intentWords);
                break;

            case R.id.btnFoodTest:
                intentWords.putExtra("testType", 3);
                startActivity(intentWords);
                break;

            default:
                break;
        }
    }
}
