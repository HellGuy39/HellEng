package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestResultFragment extends Fragment implements View.OnClickListener{

    TextView tvUserAnswer1,tvUserAnswer2,tvUserAnswer3,tvUserAnswer4,tvUserAnswer5,tvUserAnswer6,
            tvUserAnswer7,tvUserAnswer8,tvUserAnswer9,tvUserAnswer10,tvUserAnswer11,tvUserAnswer12;

    TextView tvTrueAnswer1,tvTrueAnswer2,tvTrueAnswer3,tvTrueAnswer4,tvTrueAnswer5,tvTrueAnswer6,
            tvTrueAnswer7,tvTrueAnswer8,tvTrueAnswer9,tvTrueAnswer10,tvTrueAnswer11,tvTrueAnswer12;

    TextView tvCompleted;

    LinearLayout container1,container2,container3,container4,container5,container6,
            container7,container8,container9,container10,container11,container12;

    Button btnOk;

    FirebaseAuth mAuth;

    String userAnswer1,userAnswer2,userAnswer3,userAnswer4,userAnswer5,userAnswer6,
            userAnswer7,userAnswer8,userAnswer9,userAnswer10,userAnswer11,userAnswer12;

    String trueAnswer1,trueAnswer2,trueAnswer3,trueAnswer4,trueAnswer5,trueAnswer6,
            trueAnswer7,trueAnswer8,trueAnswer9,trueAnswer10,trueAnswer11,trueAnswer12;

    int completed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser userF = mAuth.getInstance().getCurrentUser();

        assert getArguments() != null;
        userAnswer1 = getArguments().getString("task1UserAnswer"," ");
        userAnswer2 = getArguments().getString("task2UserAnswer"," ");
        userAnswer3 = getArguments().getString("task3UserAnswer"," ");
        userAnswer4 = getArguments().getString("task4UserAnswer"," ");
        userAnswer5 = getArguments().getString("task5UserAnswer"," ");
        userAnswer6 = getArguments().getString("task6UserAnswer"," ");
        userAnswer7 = getArguments().getString("task7UserAnswer"," ");
        userAnswer8 = getArguments().getString("task8UserAnswer"," ");
        userAnswer9 = getArguments().getString("task9UserAnswer"," ");
        userAnswer10 = getArguments().getString("task10UserAnswer"," ");
        userAnswer11 = getArguments().getString("task11UserAnswer"," ");
        userAnswer12 = getArguments().getString("task12UserAnswer"," ");

        trueAnswer1 = getArguments().getString("task1TrueAnswer"," ");
        trueAnswer2 = getArguments().getString("task2TrueAnswer"," ");
        trueAnswer3 = getArguments().getString("task3TrueAnswer"," ");
        trueAnswer4 = getArguments().getString("task4TrueAnswer"," ");
        trueAnswer5 = getArguments().getString("task5TrueAnswer"," ");
        trueAnswer6 = getArguments().getString("task6TrueAnswer"," ");
        trueAnswer7 = getArguments().getString("task7TrueAnswer"," ");
        trueAnswer8 = getArguments().getString("task8TrueAnswer"," ");
        trueAnswer9 = getArguments().getString("task9TrueAnswer"," ");
        trueAnswer10 = getArguments().getString("task10TrueAnswer"," ");
        trueAnswer11 = getArguments().getString("task11TrueAnswer"," ");
        trueAnswer12 = getArguments().getString("task12TrueAnswer"," ");

        completed = getArguments().getInt("completedInt", 0);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView
                = inflater.inflate(R.layout.fragment_test_result,container,false);

        tvUserAnswer1 = rootView.findViewById(R.id.tvUserAnswer1);
        tvUserAnswer2 = rootView.findViewById(R.id.tvUserAnswer2);
        tvUserAnswer3 = rootView.findViewById(R.id.tvUserAnswer3);
        tvUserAnswer4 = rootView.findViewById(R.id.tvUserAnswer4);
        tvUserAnswer5 = rootView.findViewById(R.id.tvUserAnswer5);
        tvUserAnswer6 = rootView.findViewById(R.id.tvUserAnswer6);
        tvUserAnswer7 = rootView.findViewById(R.id.tvUserAnswer7);
        tvUserAnswer8 = rootView.findViewById(R.id.tvUserAnswer8);
        tvUserAnswer9 = rootView.findViewById(R.id.tvUserAnswer9);
        tvUserAnswer10 = rootView.findViewById(R.id.tvUserAnswer10);
        tvUserAnswer11 = rootView.findViewById(R.id.tvUserAnswer11);
        tvUserAnswer12 = rootView.findViewById(R.id.tvUserAnswer12);

        tvTrueAnswer1 = rootView.findViewById(R.id.tvTrueAnswer1);
        tvTrueAnswer2 = rootView.findViewById(R.id.tvTrueAnswer2);
        tvTrueAnswer3 = rootView.findViewById(R.id.tvTrueAnswer3);
        tvTrueAnswer4 = rootView.findViewById(R.id.tvTrueAnswer4);
        tvTrueAnswer5 = rootView.findViewById(R.id.tvTrueAnswer5);
        tvTrueAnswer6 = rootView.findViewById(R.id.tvTrueAnswer6);
        tvTrueAnswer7 = rootView.findViewById(R.id.tvTrueAnswer7);
        tvTrueAnswer8 = rootView.findViewById(R.id.tvTrueAnswer8);
        tvTrueAnswer9 = rootView.findViewById(R.id.tvTrueAnswer9);
        tvTrueAnswer10 = rootView.findViewById(R.id.tvTrueAnswer10);
        tvTrueAnswer11 = rootView.findViewById(R.id.tvTrueAnswer11);
        tvTrueAnswer12 = rootView.findViewById(R.id.tvTrueAnswer12);

        btnOk = rootView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this::onClick);

        tvCompleted = rootView.findViewById(R.id.tvCompleted);

        updateUI();

        return rootView;
    }

    private void updateUI() {

        tvUserAnswer1.setText(userAnswer1);
        tvUserAnswer2.setText(userAnswer2);
        tvUserAnswer3.setText(userAnswer3);
        tvUserAnswer4.setText(userAnswer4);
        tvUserAnswer5.setText(userAnswer5);
        tvUserAnswer6.setText(userAnswer6);
        tvUserAnswer7.setText(userAnswer7);
        tvUserAnswer8.setText(userAnswer8);
        tvUserAnswer9.setText(userAnswer9);
        tvUserAnswer10.setText(userAnswer10);
        tvUserAnswer11.setText(userAnswer11);
        tvUserAnswer12.setText(userAnswer12);

        tvTrueAnswer1.setText(trueAnswer1);
        tvTrueAnswer2.setText(trueAnswer2);
        tvTrueAnswer3.setText(trueAnswer3);
        tvTrueAnswer4.setText(trueAnswer4);
        tvTrueAnswer5.setText(trueAnswer5);
        tvTrueAnswer6.setText(trueAnswer6);
        tvTrueAnswer7.setText(trueAnswer7);
        tvTrueAnswer8.setText(trueAnswer8);
        tvTrueAnswer9.setText(trueAnswer9);
        tvTrueAnswer10.setText(trueAnswer10);
        tvTrueAnswer11.setText(trueAnswer11);
        tvTrueAnswer12.setText(trueAnswer12);

        tvCompleted.setText(Integer.toString(completed) + "");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOk) {
            startActivity(new Intent(getContext(),MainActivity.class));
            ((TensesActivity)getContext()).finishActivity();
        }
    }
}
