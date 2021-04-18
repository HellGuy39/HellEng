package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hg39.helleng.AuditionActivity;
import com.hg39.helleng.MainActivity;
import com.hg39.helleng.R;

import java.util.Objects;

import static com.hg39.helleng.MainActivity.groupAudition;
import static com.hg39.helleng.MainActivity.groupGrammar;
import static com.hg39.helleng.MainActivity.humor;
import static com.hg39.helleng.MainActivity.superman;

public class AuditionSelectedFragment extends Fragment implements View.OnClickListener{

    TextView tvCompletedHumor,tvCompletedSuperman;
    TextView tvHumor,tvSuperman;
    String completedHumor,completedSuperman;
    Intent intent;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersTestsProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(getContext(), AuditionActivity.class);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        usersTestsProgress = database.getReference("Users Tests Progress");

        usersTestsProgress.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(groupAudition).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedHumor = dataSnapshot.child(humor).getValue(String.class);
                completedSuperman = dataSnapshot.child(superman).getValue(String.class);

                updateUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.fragment_audition_selected,container,false);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity)getContext()).setFragCourses());

        rootView.findViewById(R.id.cardHumor).setOnClickListener(this);
        rootView.findViewById(R.id.cardSuperman).setOnClickListener(this);

        //Tests completed info
        tvCompletedHumor = rootView.findViewById(R.id.tvCompletedHumor);
        tvCompletedSuperman = rootView.findViewById(R.id.tvCompletedSuperman);
        //Tests tittles
        tvHumor = rootView.findViewById(R.id.tvHumor);
        tvSuperman = rootView.findViewById(R.id.tvSuperman);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUI();

    }

    private void updateUI(){

        if (completedHumor == null) {
            completedHumor = "0/3";
        }

        if (completedSuperman == null) {
            completedSuperman = "0/3";
        }

        tvCompletedHumor.setText(completedHumor);
        tvCompletedSuperman.setText(completedSuperman);

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

        if (v.getId() == R.id.cardHumor) {
            intent.putExtra("testName", humor);
            startActivity(intent);
        } else if (v.getId() == R.id.cardSuperman) {
            intent.putExtra("testName", superman);
            startActivity(intent);
        }
    }
}
