package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import static com.hg39.helleng.MainActivity.food;
import static com.hg39.helleng.MainActivity.furniture;
import static com.hg39.helleng.MainActivity.futureSimple;
import static com.hg39.helleng.MainActivity.humor;
import static com.hg39.helleng.MainActivity.pastSimple;
import static com.hg39.helleng.MainActivity.presentSimple;
import static com.hg39.helleng.MainActivity.schoolSupplies;
import static com.hg39.helleng.MainActivity.superman;

public class TestResultFragment extends Fragment implements View.OnClickListener{

    TextView tvUserAnswer1,tvUserAnswer2,tvUserAnswer3,tvUserAnswer4,tvUserAnswer5,tvUserAnswer6,
            tvUserAnswer7,tvUserAnswer8,tvUserAnswer9,tvUserAnswer10,tvUserAnswer11,tvUserAnswer12;

    TextView tvTrueAnswer1,tvTrueAnswer2,tvTrueAnswer3,tvTrueAnswer4,tvTrueAnswer5,tvTrueAnswer6,
            tvTrueAnswer7,tvTrueAnswer8,tvTrueAnswer9,tvTrueAnswer10,tvTrueAnswer11,tvTrueAnswer12;

    TextView tvCompleted;

    LinearLayout container1,container2,container3,container4,container5,container6,
            container7,container8,container9,container10,container11,container12;

    Button btnOk;

    String userAnswer1,userAnswer2,userAnswer3,userAnswer4,userAnswer5,userAnswer6,
            userAnswer7,userAnswer8,userAnswer9,userAnswer10,userAnswer11,userAnswer12;

    String trueAnswer1,trueAnswer2,trueAnswer3,trueAnswer4,trueAnswer5,trueAnswer6,
            trueAnswer7,trueAnswer8,trueAnswer9,trueAnswer10,trueAnswer11,trueAnswer12;

    int completed,countOfTasks;

    String testName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        countOfTasks = getArguments().getInt("countOfTasks", 0);
        testName = getArguments().getString("testName", "null");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView
                = inflater.inflate(R.layout.fragment_test_result,container,false);

        container1 = rootView.findViewById(R.id.container1);
        container2 = rootView.findViewById(R.id.container2);
        container3 = rootView.findViewById(R.id.container3);
        container4 = rootView.findViewById(R.id.container4);
        container5 = rootView.findViewById(R.id.container5);
        container6 = rootView.findViewById(R.id.container6);
        container7 = rootView.findViewById(R.id.container7);
        container8 = rootView.findViewById(R.id.container8);
        container9 = rootView.findViewById(R.id.container9);
        container10 = rootView.findViewById(R.id.container10);
        container11 = rootView.findViewById(R.id.container11);
        container12 = rootView.findViewById(R.id.container12);

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
        btnOk.setOnClickListener(this);

        tvCompleted = rootView.findViewById(R.id.tvCompleted);

        disableUselessContainers();
        colorSetter();
        updateUI();

        return rootView;
    }

    private void disableUselessContainers() {

        if (countOfTasks == 3)
        {
            container4.setVisibility(View.GONE);
            container5.setVisibility(View.GONE);
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            container9.setVisibility(View.GONE);
            container10.setVisibility(View.GONE);
            container11.setVisibility(View.GONE);
            container12.setVisibility(View.GONE);
        }
        else if (countOfTasks == 5)
        {
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            container9.setVisibility(View.GONE);
            container10.setVisibility(View.GONE);
            container11.setVisibility(View.GONE);
            container12.setVisibility(View.GONE);
        }
        else if (countOfTasks == 7)
        {
            container8.setVisibility(View.GONE);
            container9.setVisibility(View.GONE);
            container10.setVisibility(View.GONE);
            container11.setVisibility(View.GONE);
            container12.setVisibility(View.GONE);
        }
        else if (countOfTasks == 10)
        {
            container11.setVisibility(View.GONE);
            container12.setVisibility(View.GONE);
        }
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

        if (testName.equals(humor) || testName.equals(superman))
        {
            tvCompleted.setText(completed + "/" + countOfTasks);
        }
        else if (testName.equals(presentSimple) || testName.equals(pastSimple) || testName.equals(futureSimple) ||
                testName.equals(schoolSupplies) || testName.equals(furniture) || testName.equals(food)){
            tvCompleted.setText(completed + "%");
        }
        else
        {
            tvCompleted.setText(completed + "/" + countOfTasks);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOk)
        {
            if (testName.equals("null"))
            {
                Toast.makeText(getContext(),"Something wrong!",Toast.LENGTH_SHORT).show();
            }
            else if (testName.equals(furniture) || testName.equals(food) ||testName.equals(schoolSupplies))
            {
                ((VocabularyActivity) requireContext()).finishActivity();
            }
            else if (testName.equals(presentSimple) || testName.equals(pastSimple) || testName.equals(futureSimple))
            {
                ((GrammarActivity) requireContext()).finishActivity();
            }
            else if (testName.equals(humor) || testName.equals(superman))
            {
                ((AuditionActivity) requireContext()).finishActivity();
            }
            else
            {
                ((TestMakerActivity) requireContext()).finish();
            }
        }
    }

    private void colorSetter() {

        tvTrueAnswer1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer5.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer6.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer7.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer8.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer9.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer10.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer11.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        tvTrueAnswer12.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));

        if (userAnswer1.equalsIgnoreCase(trueAnswer1)) {
            tvUserAnswer1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer2.equalsIgnoreCase(trueAnswer2)) {
            tvUserAnswer2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer3.equalsIgnoreCase(trueAnswer3)) {
            tvUserAnswer3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer4.equalsIgnoreCase(trueAnswer4)) {
            tvUserAnswer4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer5.equalsIgnoreCase(trueAnswer5)) {
            tvUserAnswer5.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer5.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer6.equalsIgnoreCase(trueAnswer6)) {
            tvUserAnswer6.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer6.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer7.equalsIgnoreCase(trueAnswer7)) {
            tvUserAnswer7.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer7.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer8.equalsIgnoreCase(trueAnswer8)) {
            tvUserAnswer8.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer8.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer9.equalsIgnoreCase(trueAnswer9)) {
            tvUserAnswer9.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer9.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer10.equalsIgnoreCase(trueAnswer10)) {
            tvUserAnswer10.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer10.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer11.equalsIgnoreCase(trueAnswer11)) {
            tvUserAnswer11.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer11.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }

        if (userAnswer12.equalsIgnoreCase(trueAnswer12)) {
            tvUserAnswer12.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_green, null));
        } else {
            tvUserAnswer12.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.light_red, null));
        }
    }
}
