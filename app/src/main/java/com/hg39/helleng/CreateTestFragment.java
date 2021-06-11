package com.hg39.helleng;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.hg39.helleng.TestMakerActivity.ACTION_CREATE_TEST;
import static com.hg39.helleng.TestMakerActivity.ACTION_EDIT_TEST;
import static com.hg39.helleng.TestMakerActivity.TASK_1;
import static com.hg39.helleng.TestMakerActivity.TASK_2;
import static com.hg39.helleng.TestMakerActivity.TASK_3;
import static com.hg39.helleng.TestMakerActivity.TASK_4;
import static com.hg39.helleng.TestMakerActivity.TASK_5;
import static com.hg39.helleng.TestMakerActivity.TASK_6;
import static com.hg39.helleng.TestMakerActivity.TASK_7;
import static com.hg39.helleng.TestMakerActivity.TASK_ANSWER;
import static com.hg39.helleng.TestMakerActivity.TASK_DESCRIPTION;
import static com.hg39.helleng.TestMakerActivity.TASK_QUESTION;
import static com.hg39.helleng.TestMakerActivity.TASK_TYPE;
import static com.hg39.helleng.TestMakerActivity.TYPE_TEXT_FIELD;

public class CreateTestFragment extends Fragment {

    com.google.android.material.textfield.TextInputEditText etTestId, etTestName;
    AutoCompleteTextView timeDropdown,tasksDropdown;
    Button btnStep1ExpandMore;
    boolean isVisibleStep1;
    LinearLayout containerStep1;

    com.google.android.material.textfield.TextInputLayout layoutTestId;

    LinearLayout containerTextFieldTask1,containerTextFieldTask2,containerTextFieldTask3,containerTextFieldTask4,
            containerTextFieldTask5,containerTextFieldTask6,containerTextFieldTask7;

    com.google.android.material.textfield.TextInputEditText
            etTask1Description, etTask1Question, etTask1Answer;
    com.google.android.material.textfield.TextInputEditText
            etTask2Description, etTask2Question, etTask2Answer;
    com.google.android.material.textfield.TextInputEditText
            etTask3Description, etTask3Question, etTask3Answer;
    com.google.android.material.textfield.TextInputEditText
            etTask4Description, etTask4Question, etTask4Answer;
    com.google.android.material.textfield.TextInputEditText
            etTask5Description, etTask5Question, etTask5Answer;
    com.google.android.material.textfield.TextInputEditText
            etTask6Description, etTask6Question, etTask6Answer;
    com.google.android.material.textfield.TextInputEditText
            etTask7Description, etTask7Question, etTask7Answer;

    LinearLayout containerTask1, containerTask2, containerTask3, containerTask4, containerTask5,
            containerTask6, containerTask7;

    //General
    com.google.android.material.appbar.MaterialToolbar toolbar;
    androidx.coordinatorlayout.widget.CoordinatorLayout root;
    Button btnSave;
    ProgressDialog loadingBar;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userTestsIDRef, userTestsStorageRef;

    ArrayAdapter<String> adapterTasks;
    ArrayAdapter<String> adapterTime;

    //Saved data
    String testId, testName;
    String task1Description, task1Question, task1Answer,
            task2Description, task2Question, task2Answer,
            task3Description, task3Question, task3Answer,
            task4Description, task4Question, task4Answer,
            task5Description, task5Question, task5Answer,
            task6Description, task6Question, task6Answer,
            task7Description, task7Question, task7Answer;

    String dateText;

    String[] countOfTasks = { "3", "5", "7" };
    String[] time = {"Unlimited"};//,"10:00", "15:00", "20:00", "30:00", "40:00", "50:00", "60:00", "120:00", "180:00", "240:00"};
    String[] tasksType = {"TextField", "RadioGroup"};

    String action;

    String testTimeRes, countOfTasksRes;

    public CreateTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userTestsIDRef = database.getReference("User Tests ID");
        userTestsStorageRef = database.getReference("User Tests Storage");

        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);

        isVisibleStep1 = true;

        assert getArguments() != null;
        action = getArguments().getString("action","Action is indefinite");

        if (action.equals(ACTION_EDIT_TEST))
        {
            testId = getArguments().getString("testID", null);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_test, container, false);

        containerTextFieldTask1 = rootView.findViewById(R.id.containerTextFieldTask1);
        containerTextFieldTask2 = rootView.findViewById(R.id.containerTextFieldTask2);
        containerTextFieldTask3 = rootView.findViewById(R.id.containerTextFieldTask3);
        containerTextFieldTask4 = rootView.findViewById(R.id.containerTextFieldTask4);
        containerTextFieldTask5 = rootView.findViewById(R.id.containerTextFieldTask5);
        containerTextFieldTask6 = rootView.findViewById(R.id.containerTextFieldTask6);
        containerTextFieldTask7 = rootView.findViewById(R.id.containerTextFieldTask7);

        layoutTestId = rootView.findViewById(R.id.layoutTestId);

        //General
        btnSave = rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this::onClickSave);
        root = rootView.findViewById(R.id.root);
        loadingBar = new ProgressDialog(getContext());

        //Step 1
        etTestId = rootView.findViewById(R.id.etTestId);
        etTestName= rootView.findViewById(R.id.etTestName);
        timeDropdown = rootView.findViewById(R.id.timeDropdown);
        tasksDropdown = rootView.findViewById(R.id.tasksDropdown);

        containerStep1 = rootView.findViewById(R.id.containerStep1);

        btnStep1ExpandMore = rootView.findViewById(R.id.btnStep1ExpandMore);
        btnStep1ExpandMore.setOnClickListener(this::onClickExpand);
        btnStep1ExpandMore.setRotation(180);

        adapterTasks = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, countOfTasks);
        adapterTasks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasksDropdown.setAdapter(adapterTasks);

        adapterTime = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, time);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeDropdown.setAdapter(adapterTime);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tasksType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etTask1Description = rootView.findViewById(R.id.etTask1Description);
        etTask1Question = rootView.findViewById(R.id.etTask1Question);
        etTask1Answer = rootView.findViewById(R.id.etTask1Answer);
        containerTask1 = rootView.findViewById(R.id.container_task1);

        etTask2Description = rootView.findViewById(R.id.etTask2Description);
        etTask2Question = rootView.findViewById(R.id.etTask2Question);
        etTask2Answer = rootView.findViewById(R.id.etTask2Answer);
        containerTask2 = rootView.findViewById(R.id.container_task2);

        etTask3Description = rootView.findViewById(R.id.etTask3Description);
        etTask3Question = rootView.findViewById(R.id.etTask3Question);
        etTask3Answer = rootView.findViewById(R.id.etTask3Answer);
        containerTask3 = rootView.findViewById(R.id.container_task3);

        etTask4Description = rootView.findViewById(R.id.etTask4Description);
        etTask4Question = rootView.findViewById(R.id.etTask4Question);
        etTask4Answer = rootView.findViewById(R.id.etTask4Answer);
        containerTask4 = rootView.findViewById(R.id.container_task4);

        etTask5Description = rootView.findViewById(R.id.etTask5Description);
        etTask5Question = rootView.findViewById(R.id.etTask5Question);
        etTask5Answer = rootView.findViewById(R.id.etTask5Answer);
        containerTask5 = rootView.findViewById(R.id.container_task5);

        etTask6Description = rootView.findViewById(R.id.etTask6Description);
        etTask6Question = rootView.findViewById(R.id.etTask6Question);
        etTask6Answer = rootView.findViewById(R.id.etTask6Answer);
        containerTask6 = rootView.findViewById(R.id.container_task6);

        etTask7Description = rootView.findViewById(R.id.etTask7Description);
        etTask7Question = rootView.findViewById(R.id.etTask7Question);
        etTask7Answer = rootView.findViewById(R.id.etTask7Answer);
        containerTask7 = rootView.findViewById(R.id.container_task7);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> ((TestMakerActivity) requireContext()).onBackPressed());

        if (action.equals(ACTION_CREATE_TEST))
            toolbar.setTitle("Create Test");
        else if (action.equals(ACTION_EDIT_TEST))
            toolbar.setTitle("Edit Test");
        else
            toolbar.setTitle("Action is indefinite");

        countOfTasksRes = countOfTasks[1];
        testTimeRes = time[0];

        tasksDropdown.setOnItemClickListener((parent, view, position, id) -> {
            countOfTasksRes = (String) parent.getItemAtPosition(position);
            changingTasksCount(countOfTasksRes);
        });

        timeDropdown.setOnItemClickListener((parent, view, position, id) -> testTimeRes = (String)parent.getItemAtPosition(position));

        tasksDropdown.setText(adapterTasks.getItem(1), false);
        timeDropdown.setText(adapterTime.getItem(0), false);

        if (action.equals(ACTION_EDIT_TEST))
        {
            loadTestData(testId);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        changingTasksCount(countOfTasksRes);
    }

    protected void onClickSave(View view) {
        if (view.getId() == R.id.btn_save)
        {
            checkSteps();
        }
    }

    private void loadTestData(String testID) {

        etTestId.setEnabled(false);
        layoutTestId.setEnabled(false);

        ValueEventListener testData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                testName = snapshot.child("testName").getValue(String.class);
                countOfTasksRes = snapshot.child("countOfTasks").getValue(String.class);
                testTimeRes = snapshot.child("time").getValue(String.class);

                //userTestsStorageRef.child(testId).child(TASK_1).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                task1Description = snapshot.child(TASK_1).child(TASK_DESCRIPTION).getValue(String.class);
                task1Question = snapshot.child(TASK_1).child(TASK_QUESTION).getValue(String.class);
                task1Answer = snapshot.child(TASK_1).child(TASK_ANSWER).getValue(String.class);

                //userTestsStorageRef.child(testId).child(TASK_2).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                task2Description = snapshot.child(TASK_2).child(TASK_DESCRIPTION).getValue(String.class);
                task2Question = snapshot.child(TASK_2).child(TASK_QUESTION).getValue(String.class);
                task2Answer = snapshot.child(TASK_2).child(TASK_ANSWER).getValue(String.class);

                //userTestsStorageRef.child(testId).child(TASK_4).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                task3Description = snapshot.child(TASK_3).child(TASK_DESCRIPTION).getValue(String.class);
                task3Question = snapshot.child(TASK_3).child(TASK_QUESTION).getValue(String.class);
                task3Answer = snapshot.child(TASK_3).child(TASK_ANSWER).getValue(String.class);

                if (countOfTasksRes.equals("5")) {
                    //userTestsStorageRef.child(testId).child(TASK_4).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                    task4Description = snapshot.child(TASK_4).child(TASK_DESCRIPTION).getValue(String.class);
                    task4Question = snapshot.child(TASK_4).child(TASK_QUESTION).getValue(String.class);
                    task4Answer = snapshot.child(TASK_4).child(TASK_ANSWER).getValue(String.class);

                    //userTestsStorageRef.child(testId).child(TASK_4).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                    task5Description = snapshot.child(TASK_5).child(TASK_DESCRIPTION).getValue(String.class);
                    task5Question = snapshot.child(TASK_5).child(TASK_QUESTION).getValue(String.class);
                    task5Answer = snapshot.child(TASK_5).child(TASK_ANSWER).getValue(String.class);
                }
                else if (countOfTasksRes.equals("7"))
                {
                    //userTestsStorageRef.child(testId).child(TASK_4).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                    task4Description = snapshot.child(TASK_4).child(TASK_DESCRIPTION).getValue(String.class);
                    task4Question = snapshot.child(TASK_4).child(TASK_QUESTION).getValue(String.class);
                    task4Answer = snapshot.child(TASK_4).child(TASK_ANSWER).getValue(String.class);

                    //userTestsStorageRef.child(testId).child(TASK_5).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                    task5Description = snapshot.child(TASK_5).child(TASK_DESCRIPTION).getValue(String.class);
                    task5Question = snapshot.child(TASK_5).child(TASK_QUESTION).getValue(String.class);
                    task5Answer = snapshot.child(TASK_5).child(TASK_ANSWER).getValue(String.class);

                    //userTestsStorageRef.child(testId).child(TASK_6).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                    task6Description = snapshot.child(TASK_6).child(TASK_DESCRIPTION).getValue(String.class);
                    task6Question = snapshot.child(TASK_6).child(TASK_QUESTION).getValue(String.class);
                    task6Answer = snapshot.child(TASK_6).child(TASK_ANSWER).getValue(String.class);

                    //userTestsStorageRef.child(testId).child(TASK_7).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                    task7Description = snapshot.child(TASK_7).child(TASK_DESCRIPTION).getValue(String.class);
                    task7Question = snapshot.child(TASK_7).child(TASK_QUESTION).getValue(String.class);
                    task7Answer = snapshot.child(TASK_7).child(TASK_ANSWER).getValue(String.class);
                }
                changingTasksCount(countOfTasksRes);
                setLoadedTestData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        userTestsStorageRef.child(testID).addValueEventListener(testData);


    }

    private void setLoadedTestData() {

        etTestName.setText(testName);
        etTestId.setText(testId);

        etTask1Answer.setText(task1Answer);
        etTask1Description.setText(task1Description);
        etTask1Question.setText(task1Question);

        etTask2Answer.setText(task2Answer);
        etTask2Description.setText(task2Description);
        etTask2Question.setText(task2Question);

        etTask3Answer.setText(task3Answer);
        etTask3Description.setText(task3Description);
        etTask3Question.setText(task3Question);

        switch (countOfTasksRes) {
            case "3":
                tasksDropdown.setText(adapterTasks.getItem(0), false);
                break;

            case "5":
                etTask4Answer.setText(task4Answer);
                etTask4Description.setText(task4Description);
                etTask4Question.setText(task4Question);

                etTask5Answer.setText(task5Answer);
                etTask5Description.setText(task5Description);
                etTask5Question.setText(task5Question);

                tasksDropdown.setText(adapterTasks.getItem(1), false);

                break;

            case "7":
                etTask4Answer.setText(task4Answer);
                etTask4Description.setText(task4Description);
                etTask4Question.setText(task4Question);

                etTask5Answer.setText(task5Answer);
                etTask5Description.setText(task5Description);
                etTask5Question.setText(task5Question);

                etTask6Answer.setText(task6Answer);
                etTask6Description.setText(task6Description);
                etTask6Question.setText(task6Question);

                etTask7Answer.setText(task7Answer);
                etTask7Description.setText(task7Description);
                etTask7Question.setText(task7Question);

                tasksDropdown.setText(adapterTasks.getItem(2), false);
                break;
        }

    }

    private void onClickExpand(View view) {

        if (view.getId() == R.id.btnStep1ExpandMore)
        {

            if (isVisibleStep1)
            {
                view.setRotation(0);
                containerStep1.setVisibility(View.GONE);
                isVisibleStep1 = false;
            }
            else
            {
                view.setRotation(180);
                containerStep1.setVisibility(View.VISIBLE);
                isVisibleStep1 = true;
            }

        }
    }

    private void changingTasksCount(String countOfTasks) {
        switch (countOfTasks) {
            case "3":
                containerTask1.setVisibility(View.VISIBLE);
                containerTask2.setVisibility(View.VISIBLE);
                containerTask3.setVisibility(View.VISIBLE);
                containerTask4.setVisibility(View.GONE);
                containerTask5.setVisibility(View.GONE);
                containerTask6.setVisibility(View.GONE);
                containerTask7.setVisibility(View.GONE);
                break;
            case "5":

                containerTask1.setVisibility(View.VISIBLE);
                containerTask2.setVisibility(View.VISIBLE);
                containerTask3.setVisibility(View.VISIBLE);
                containerTask4.setVisibility(View.VISIBLE);
                containerTask5.setVisibility(View.VISIBLE);
                containerTask6.setVisibility(View.GONE);
                containerTask7.setVisibility(View.GONE);

                break;
            case "7":
                containerTask1.setVisibility(View.VISIBLE);
                containerTask2.setVisibility(View.VISIBLE);
                containerTask3.setVisibility(View.VISIBLE);
                containerTask4.setVisibility(View.VISIBLE);
                containerTask5.setVisibility(View.VISIBLE);
                containerTask6.setVisibility(View.VISIBLE);
                containerTask7.setVisibility(View.VISIBLE);

                break;
        }

    }

    private void checkSteps() {

        //Step 1
        if (TextUtils.isEmpty(Objects.requireNonNull(etTestId.getText()).toString()))
        {
            Snackbar.make(root,"Enter test ID please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTestName.getText()).toString()))
        {
            Snackbar.make(root,"Enter test name please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etTestId.getText().toString().length() < 5)
        {
            Snackbar.make(root,"Test ID must be more than 5 characters", Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTestId.getText().toString().length() > 24)
        {
            Snackbar.make(root,"Test ID must be less than 24 characters", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (etTestName.getText().toString().length() < 3)
        {
            Snackbar.make(root,"Test Name must be more than 3 characters", Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTestName.getText().toString().length() > 64)
        {

            Snackbar.make(root,"Test Name must be less than 64 characters", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Step 3
        //Task 1
        if (TextUtils.isEmpty(Objects.requireNonNull(etTask1Description.getText()).toString()))
        {
            Snackbar.make(root,"Enter Description for Task 1 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask1Description.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Description for Task 1 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask1Question.getText()).toString()))
        {
            Snackbar.make(root,"Enter Question for Task 1 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask1Question.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Question for Task 1 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask1Answer.getText()).toString()))
        {
            Snackbar.make(root,"Enter Answer for Task 1 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask1Answer.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Correct Answer for Task 1 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        //Task 2
        if (TextUtils.isEmpty(Objects.requireNonNull(etTask2Description.getText()).toString()))
        {
            Snackbar.make(root,"Enter Description for Task 2 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask2Description.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Description for Task 2 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask2Question.getText()).toString()))
        {
            Snackbar.make(root,"Enter Question for Task 2 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask2Question.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Question for Task 2 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask2Answer.getText()).toString()))
        {
            Snackbar.make(root,"Enter Answer for Task 2 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask2Answer.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Correct Answer for Task 2 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        //Task 3
        if (TextUtils.isEmpty(Objects.requireNonNull(etTask3Description.getText()).toString()))
        {
            Snackbar.make(root,"Enter Description for Task 3 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask3Description.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Description for Task 3 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask3Question.getText()).toString()))
        {
            Snackbar.make(root,"Enter Question for Task 3 please",Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask3Question.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Question for Task 3 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask3Answer.getText()).toString())) {
            Snackbar.make(root, "Enter Answer for Task 3 please", Snackbar.LENGTH_LONG).show();
            return;
        }
        else if (etTask3Answer.getText().toString().length() > 512)
        {
            Snackbar.make(root,"Correct Answer for Task 3 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (countOfTasksRes.equals("5"))
        {
            //Task 4
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Description.getText()).toString()))
            {
                Snackbar.make(root,"Enter Description for Task 4 please",Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask4Description.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Description for Task 4 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Question.getText()).toString()))
            {
                Snackbar.make(root,"Enter Question for Task 4 please",Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask4Question.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Question for Task 4 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Answer.getText()).toString()))
            {
                Snackbar.make(root, "Enter Answer for Task 4 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask4Answer.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Correct Answer for Task 4 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            //Task 5
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Description.getText()).toString()))
            {
                Snackbar.make(root, "Enter Description for Task 5 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask5Description.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Description for Task 5 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Question.getText()).toString()))
            {
                Snackbar.make(root, "Enter Question for Task 5 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask5Question.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Question for Task 5 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Answer.getText()).toString()))
            {
                Snackbar.make(root, "Enter Answer for Task 5 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask5Answer.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Correct Answer for Task 5 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
        }

        if (countOfTasksRes.equals("7"))
        {
            //Task 4
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Description.getText()).toString()))
            {
                Snackbar.make(root,"Enter Description for Task 4 please",Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask4Description.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Description for Task 4 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Question.getText()).toString()))
            {
                Snackbar.make(root,"Enter Question for Task 4 please",Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask4Question.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Question for Task 4 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Answer.getText()).toString()))
            {
                Snackbar.make(root, "Enter Answer for Task 4 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask4Answer.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Correct Answer for Task 4 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            //Task 5
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Description.getText()).toString()))
            {
                Snackbar.make(root, "Enter Description for Task 5 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask5Description.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Description for Task 5 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Question.getText()).toString()))
            {
                Snackbar.make(root, "Enter Question for Task 5 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask5Question.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Question for Task 5 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Answer.getText()).toString()))
            {
                Snackbar.make(root, "Enter Answer for Task 5 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask5Answer.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Correct Answer for Task 5 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }

            //Task 6
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask6Description.getText()).toString()))
            {
                Snackbar.make(root,"Enter Description for Task 6 please",Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask6Description.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Description for Task 6 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etTask6Question.getText()).toString()))
            {
                Snackbar.make(root,"Enter Question for Task 6 please",Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask6Question.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Question for Task 6 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etTask6Answer.getText()).toString()))
            {
                Snackbar.make(root, "Enter Answer for Task 6 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask6Answer.getText().toString().length() > 512)
            {
                Snackbar.make(root,"Correct Answer for Task 6 must be less than 512 characters",Snackbar.LENGTH_LONG).show();
                return;
            }

            //Task 7
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask7Description.getText()).toString()))
            {
                Snackbar.make(root, "Enter Description for Task 7 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask7Description.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Description for Task 7 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask7Question.getText()).toString()))
            {
                Snackbar.make(root, "Enter Question for Task 7 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask7Question.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Question for Task 7 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etTask7Answer.getText()).toString()))
            {
                Snackbar.make(root, "Enter Answer for Task 7 please", Snackbar.LENGTH_LONG).show();
                return;
            }
            else if (etTask7Answer.getText().toString().length() > 512)
            {
                Snackbar.make(root, "Correct Answer for Task 7 must be less than 512 characters", Snackbar.LENGTH_LONG).show();
                return;
            }
        }

        if (!TextUtils.isDigitsOnly(etTestId.getText().toString())) {
            loadingBar.setTitle("Saving...");
            loadingBar.setMessage("Saving your test, please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            captureAllData();
            saveTest();
        }
        else
        {
            Snackbar.make(root, "ID must contain characters",Snackbar.LENGTH_LONG).show();
        }

    }

    private void captureAllData() {

        testId = Objects.requireNonNull(etTestId.getText()).toString();
        testName = Objects.requireNonNull(etTestName.getText()).toString();

        task1Description = Objects.requireNonNull(etTask1Description.getText()).toString();
        task1Question = Objects.requireNonNull(etTask1Question.getText()).toString();
        task1Answer = Objects.requireNonNull(etTask1Answer.getText()).toString();

        task2Description = Objects.requireNonNull(etTask2Description.getText()).toString();
        task2Question = Objects.requireNonNull(etTask2Question.getText()).toString();
        task2Answer = Objects.requireNonNull(etTask2Answer.getText()).toString();

        task3Description = Objects.requireNonNull(etTask3Description.getText()).toString();
        task3Question = Objects.requireNonNull(etTask3Question.getText()).toString();
        task3Answer = Objects.requireNonNull(etTask3Answer.getText()).toString();

        if (countOfTasksRes.equals("5"))
        {
            task4Description = Objects.requireNonNull(etTask4Description.getText()).toString();
            task4Question = Objects.requireNonNull(etTask4Question.getText()).toString();
            task4Answer = Objects.requireNonNull(etTask4Answer.getText()).toString();

            task5Description = Objects.requireNonNull(etTask5Description.getText()).toString();
            task5Question = Objects.requireNonNull(etTask5Question.getText()).toString();
            task5Answer = Objects.requireNonNull(etTask5Answer.getText()).toString();
        }
        else if (countOfTasksRes.equals("7"))
        {
            task4Description = Objects.requireNonNull(etTask4Description.getText()).toString();
            task4Question = Objects.requireNonNull(etTask4Question.getText()).toString();
            task4Answer = Objects.requireNonNull(etTask4Answer.getText()).toString();

            task5Description = Objects.requireNonNull(etTask5Description.getText()).toString();
            task5Question = Objects.requireNonNull(etTask5Question.getText()).toString();
            task5Answer = Objects.requireNonNull(etTask5Answer.getText()).toString();

            task6Description = Objects.requireNonNull(etTask6Description.getText()).toString();
            task6Question = Objects.requireNonNull(etTask6Question.getText()).toString();
            task6Answer = Objects.requireNonNull(etTask6Answer.getText()).toString();

            task7Description = Objects.requireNonNull(etTask7Description.getText()).toString();
            task7Question = Objects.requireNonNull(etTask7Question.getText()).toString();
            task7Answer = Objects.requireNonNull(etTask7Answer.getText()).toString();
        }

        testId = testId.trim();
        testName = testName.trim();

        task1Description = task1Description.trim();
        task1Question = task1Question.trim();
        task1Answer = task1Answer.trim();

        task2Description = task2Description.trim();
        task2Question = task2Question.trim();
        task2Answer = task2Answer.trim();

        task3Description = task3Description.trim();
        task3Question = task3Question.trim();
        task3Answer = task3Answer.trim();

        if (countOfTasksRes.equals("5"))
        {
            task4Description = task4Description.trim();
            task4Question = task4Question.trim();
            task4Answer = task4Answer.trim();

            task5Description = task5Description.trim();
            task5Question = task5Question.trim();
            task5Answer = task5Answer.trim();
        }
        else if (countOfTasksRes.equals("7"))
        {
            task4Description = task4Description.trim();
            task4Question = task4Question.trim();
            task4Answer = task4Answer.trim();

            task5Description = task5Description.trim();
            task5Question = task5Question.trim();
            task5Answer = task5Answer.trim();

            task6Description = task6Description.trim();
            task6Question = task6Question.trim();
            task6Answer = task6Answer.trim();

            task7Description = task7Description.trim();
            task7Question = task7Question.trim();
            task7Answer = task7Answer.trim();
        }

    }

    private void saveTest() {

        userTestsStorageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(testId) & action.equals(ACTION_CREATE_TEST))
                {
                    Snackbar.make(root,"This ID is already used",Snackbar.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
                else
                {
                    userTestsIDRef.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(testId).child("testID").setValue(testId);
                    //userTestsIDRef.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(testId).child("testName").setValue(testName);

                    userTestsStorageRef.child(testId).child("testName").setValue(testName).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            userTestsStorageRef.child(testId).child("creatorID").setValue(mAuth.getCurrentUser().getUid());
                            userTestsStorageRef.child(testId).child("dateОfCreation").setValue(dateText);
                            userTestsStorageRef.child(testId).child("countOfTasks").setValue(countOfTasksRes);
                            userTestsStorageRef.child(testId).child("time").setValue(testTimeRes);

                            userTestsStorageRef.child(testId).child(TASK_1).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                            userTestsStorageRef.child(testId).child(TASK_1).child(TASK_DESCRIPTION).setValue(task1Description);
                            userTestsStorageRef.child(testId).child(TASK_1).child(TASK_QUESTION).setValue(task1Question);
                            userTestsStorageRef.child(testId).child(TASK_1).child(TASK_ANSWER).setValue(task1Answer);

                            userTestsStorageRef.child(testId).child(TASK_2).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                            userTestsStorageRef.child(testId).child(TASK_2).child(TASK_DESCRIPTION).setValue(task2Description);
                            userTestsStorageRef.child(testId).child(TASK_2).child(TASK_QUESTION).setValue(task2Question);
                            userTestsStorageRef.child(testId).child(TASK_2).child(TASK_ANSWER).setValue(task2Answer);

                            userTestsStorageRef.child(testId).child(TASK_3).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                            userTestsStorageRef.child(testId).child(TASK_3).child(TASK_DESCRIPTION).setValue(task3Description);
                            userTestsStorageRef.child(testId).child(TASK_3).child(TASK_QUESTION).setValue(task3Question);
                            userTestsStorageRef.child(testId).child(TASK_3).child(TASK_ANSWER).setValue(task3Answer);

                            if (countOfTasksRes.equals("5"))
                            {
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_DESCRIPTION).setValue(task4Description);
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_QUESTION).setValue(task4Question);
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_ANSWER).setValue(task4Answer);

                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_DESCRIPTION).setValue(task5Description);
                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_QUESTION).setValue(task5Question);
                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_ANSWER).setValue(task5Answer);
                            }

                            if (countOfTasksRes.equals("7"))
                            {
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_DESCRIPTION).setValue(task4Description);
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_QUESTION).setValue(task4Question);
                                userTestsStorageRef.child(testId).child(TASK_4).child(TASK_ANSWER).setValue(task4Answer);

                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_DESCRIPTION).setValue(task5Description);
                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_QUESTION).setValue(task5Question);
                                userTestsStorageRef.child(testId).child(TASK_5).child(TASK_ANSWER).setValue(task5Answer);

                                userTestsStorageRef.child(testId).child(TASK_6).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                                userTestsStorageRef.child(testId).child(TASK_6).child(TASK_DESCRIPTION).setValue(task6Description);
                                userTestsStorageRef.child(testId).child(TASK_6).child(TASK_QUESTION).setValue(task6Question);
                                userTestsStorageRef.child(testId).child(TASK_6).child(TASK_ANSWER).setValue(task6Answer);

                                userTestsStorageRef.child(testId).child(TASK_7).child(TASK_TYPE).setValue(TYPE_TEXT_FIELD);
                                userTestsStorageRef.child(testId).child(TASK_7).child(TASK_DESCRIPTION).setValue(task7Description);
                                userTestsStorageRef.child(testId).child(TASK_7).child(TASK_QUESTION).setValue(task7Question);
                                userTestsStorageRef.child(testId).child(TASK_7).child(TASK_ANSWER).setValue(task7Answer);
                            }

                            loadingBar.dismiss();

                            //Toast.makeText(getContext(), "Test Succsefull created!", Toast.LENGTH_SHORT).show();
                            ((TestMakerActivity) requireContext()).finish();

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingBar.dismiss();
            }
        });
    }
}