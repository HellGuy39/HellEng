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

import static com.hg39.helleng.MainActivity.food;
import static com.hg39.helleng.MainActivity.furniture;
import static com.hg39.helleng.MainActivity.groupVocabulary;
import static com.hg39.helleng.MainActivity.schoolSupplies;

public class VocabularySelectedFragment extends Fragment implements View.OnClickListener{

    Button btnFrnTest,btnFoodTest,btnSchSupTest;
    TextView tvCompletedFurniture,tvCompletedFood,tvCompletedSchoolSupplies;
    TextView tvFurniture,tvFood,tvSchoolSupplies;
    String completedInterestFurniture,completedInterestSchoolSupplies,completedInterestFood;
    Intent intentWords;
    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersTestsProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentWords = new Intent(getContext(), VocabularyActivity.class);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        usersTestsProgress = database.getReference("Users Tests Progress");

        usersTestsProgress.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(groupVocabulary).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedInterestFurniture      = dataSnapshot.child(furniture).getValue(String.class);
                completedInterestSchoolSupplies = dataSnapshot.child(schoolSupplies).getValue(String.class);
                completedInterestFood           = dataSnapshot.child(food).getValue(String.class);

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

        if (completedInterestFood == null) {
            completedInterestFood = "0";
        }

        if (completedInterestFurniture == null) {
            completedInterestFurniture = "0";
        }

        if (completedInterestSchoolSupplies == null) {
            completedInterestSchoolSupplies = "0";
        }

        tvCompletedFurniture.setText(completedInterestFurniture + " %");
        tvCompletedSchoolSupplies.setText(completedInterestSchoolSupplies + " %");
        tvCompletedFood.setText(completedInterestFood + " %");

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnFurnitureTest) {
            intentWords.putExtra("testName", furniture);
            startActivity(intentWords);
        } else if (v.getId() == R.id.btnSchoolSuppliesTest) {
            intentWords.putExtra("testName", schoolSupplies);
            startActivity(intentWords);
        } else if (v.getId() == R.id.btnFoodTest) {
            intentWords.putExtra("testName", food);
            startActivity(intentWords);
        }
    }
}
