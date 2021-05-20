package com.hg39.helleng;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

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

public class CompleteOtherTestFragment extends Fragment {

    androidx.coordinatorlayout.widget.CoordinatorLayout root;

    String testID;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userTestsIDRef, userTestsStorageRef;

    com.google.android.material.card.MaterialCardView cardTask1,cardTask2,cardTask3,cardTask4,cardTask5,
            cardTask6,cardTask7;
    Button btnEnd;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    com.google.android.material.textfield.TextInputEditText etTask1Answer, etTask2Answer, etTask3Answer,
            etTask4Answer, etTask5Answer, etTask6Answer, etTask7Answer;

    TextView tvTask1Question, tvTask1SecondaryText,
            tvTask2Question, tvTask2SecondaryText,
            tvTask3Question, tvTask3SecondaryText,
            tvTask4Question, tvTask4SecondaryText,
            tvTask5Question, tvTask5SecondaryText,
            tvTask6Question, tvTask6SecondaryText,
            tvTask7Question, tvTask7SecondaryText;

    TextView tvTestName,tvTestID,tvTime;

    String description1,description2,description3,description4,description5,description6,description7;
    String question1,question2,question3,question4,question5,question6,question7;
    String correctAnswer1,correctAnswer2,correctAnswer3,correctAnswer4,correctAnswer5,correctAnswer6,
            correctAnswer7;
    String userAnswer1,userAnswer2,userAnswer3,userAnswer4,userAnswer5,userAnswer6,userAnswer7;
    String countOfTasks, time, testName;

    ProgressDialog loadingBar;

    int completed;

    public CompleteOtherTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        testID = getArguments().getString("testID");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userTestsIDRef = database.getReference("User Tests ID");
        userTestsStorageRef = database.getReference("User Tests Storage");

        loadingBar = new ProgressDialog(getContext());
        loadingBar.setTitle("Loading...");
        loadingBar.setMessage("Getting information about a test...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        userTestsStorageRef.child(testID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                countOfTasks = snapshot.child("countOfTasks").getValue(String.class);
                time = snapshot.child("time").getValue(String.class);
                testName = snapshot.child("testName").getValue(String.class);

                question1 = snapshot.child(TASK_1).child(TASK_QUESTION).getValue(String.class);
                description1 = snapshot.child(TASK_1).child(TASK_DESCRIPTION).getValue(String.class);
                correctAnswer1 = snapshot.child(TASK_1).child(TASK_ANSWER).getValue(String.class);

                question2 = snapshot.child(TASK_2).child(TASK_QUESTION).getValue(String.class);
                description2 = snapshot.child(TASK_2).child(TASK_DESCRIPTION).getValue(String.class);
                correctAnswer2 = snapshot.child(TASK_2).child(TASK_ANSWER).getValue(String.class);

                question3 = snapshot.child(TASK_3).child(TASK_QUESTION).getValue(String.class);
                description3 = snapshot.child(TASK_3).child(TASK_DESCRIPTION).getValue(String.class);
                correctAnswer3 = snapshot.child(TASK_3).child(TASK_ANSWER).getValue(String.class);

                if (countOfTasks.equals("5"))
                {
                    question4 = snapshot.child(TASK_4).child(TASK_QUESTION).getValue(String.class);
                    description4 = snapshot.child(TASK_4).child(TASK_DESCRIPTION).getValue(String.class);
                    correctAnswer4 = snapshot.child(TASK_4).child(TASK_ANSWER).getValue(String.class);

                    question5 = snapshot.child(TASK_5).child(TASK_QUESTION).getValue(String.class);
                    description5 = snapshot.child(TASK_5).child(TASK_DESCRIPTION).getValue(String.class);
                    correctAnswer5 = snapshot.child(TASK_5).child(TASK_ANSWER).getValue(String.class);

                }
                else if (countOfTasks.equals("7"))
                {
                    question4 = snapshot.child(TASK_4).child(TASK_QUESTION).getValue(String.class);
                    description4 = snapshot.child(TASK_4).child(TASK_DESCRIPTION).getValue(String.class);
                    correctAnswer4 = snapshot.child(TASK_4).child(TASK_ANSWER).getValue(String.class);

                    question5 = snapshot.child(TASK_5).child(TASK_QUESTION).getValue(String.class);
                    description5 = snapshot.child(TASK_5).child(TASK_DESCRIPTION).getValue(String.class);
                    correctAnswer5 = snapshot.child(TASK_5).child(TASK_ANSWER).getValue(String.class);

                    question6 = snapshot.child(TASK_6).child(TASK_QUESTION).getValue(String.class);
                    description6 = snapshot.child(TASK_6).child(TASK_DESCRIPTION).getValue(String.class);
                    correctAnswer6 = snapshot.child(TASK_6).child(TASK_ANSWER).getValue(String.class);

                    question7 = snapshot.child(TASK_7).child(TASK_QUESTION).getValue(String.class);
                    description7 = snapshot.child(TASK_7).child(TASK_DESCRIPTION).getValue(String.class);
                    correctAnswer7 = snapshot.child(TASK_7).child(TASK_ANSWER).getValue(String.class);
                }

                updateUI();
                loadingBar.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            loadingBar.dismiss();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_complete_other_test, container, false);

        root = rootView.findViewById(R.id.root);

        tvTestID = rootView.findViewById(R.id.tvTestID);
        tvTime = rootView.findViewById(R.id.tvTime);
        tvTestName = rootView.findViewById(R.id.tvTestName);

        cardTask1 = rootView.findViewById(R.id.cardTask1);
        cardTask2 = rootView.findViewById(R.id.cardTask2);
        cardTask3 = rootView.findViewById(R.id.cardTask3);
        cardTask4 = rootView.findViewById(R.id.cardTask4);
        cardTask5 = rootView.findViewById(R.id.cardTask5);
        cardTask6 = rootView.findViewById(R.id.cardTask6);
        cardTask7 = rootView.findViewById(R.id.cardTask7);

        etTask1Answer = rootView.findViewById(R.id.etTask1Answer);
        etTask2Answer = rootView.findViewById(R.id.etTask2Answer);
        etTask3Answer = rootView.findViewById(R.id.etTask3Answer);
        etTask4Answer = rootView.findViewById(R.id.etTask4Answer);
        etTask5Answer = rootView.findViewById(R.id.etTask5Answer);
        etTask6Answer = rootView.findViewById(R.id.etTask6Answer);
        etTask7Answer = rootView.findViewById(R.id.etTask7Answer);

        tvTask1Question = rootView.findViewById(R.id.tvTask1Question);
        tvTask2Question = rootView.findViewById(R.id.tvTask2Question);
        tvTask3Question = rootView.findViewById(R.id.tvTask3Question);
        tvTask4Question = rootView.findViewById(R.id.tvTask4Question);
        tvTask5Question = rootView.findViewById(R.id.tvTask5Question);
        tvTask6Question = rootView.findViewById(R.id.tvTask6Question);
        tvTask7Question = rootView.findViewById(R.id.tvTask7Question);

        tvTask1SecondaryText = rootView.findViewById(R.id.tvTask1SecondaryText);
        tvTask2SecondaryText = rootView.findViewById(R.id.tvTask2SecondaryText);
        tvTask3SecondaryText = rootView.findViewById(R.id.tvTask3SecondaryText);
        tvTask4SecondaryText = rootView.findViewById(R.id.tvTask4SecondaryText);
        tvTask5SecondaryText = rootView.findViewById(R.id.tvTask5SecondaryText);
        tvTask6SecondaryText = rootView.findViewById(R.id.tvTask6SecondaryText);
        tvTask7SecondaryText = rootView.findViewById(R.id.tvTask7SecondaryText);

        btnEnd = rootView.findViewById(R.id.btn_end);
        btnEnd.setOnClickListener(this::onClickEndTest);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> ((TestMakerActivity)getContext()).onBackPressed());

        return rootView;
    }

    private void updateUI() {

        tvTestID.setText("ID: " + testID);
        tvTestName.setText(testName);
        tvTime.setText("Time left: " + time);

        tvTask1Question.setText(question1);
        tvTask2Question.setText(question2);
        tvTask3Question.setText(question3);
        tvTask1SecondaryText.setText(description1);
        tvTask2SecondaryText.setText(description2);
        tvTask3SecondaryText.setText(description3);

        if (countOfTasks.equals("5"))
        {
            tvTask4Question.setText(question4);
            tvTask5Question.setText(question5);
            tvTask4SecondaryText.setText(description4);
            tvTask5SecondaryText.setText(description5);
        }
        else if (countOfTasks.equals("7"))
        {
            tvTask4Question.setText(question4);
            tvTask5Question.setText(question5);
            tvTask6Question.setText(question6);
            tvTask7Question.setText(question7);
            tvTask4SecondaryText.setText(description4);
            tvTask5SecondaryText.setText(description5);
            tvTask6SecondaryText.setText(description6);
            tvTask7SecondaryText.setText(description7);
        }

        if (countOfTasks.equals("3"))
        {
            cardTask4.setVisibility(View.GONE);
            cardTask5.setVisibility(View.GONE);
            cardTask6.setVisibility(View.GONE);
            cardTask7.setVisibility(View.GONE);
        }
        else if (countOfTasks.equals("5"))
        {
            cardTask6.setVisibility(View.GONE);
            cardTask7.setVisibility(View.GONE);
        }

    }

    protected void onClickEndTest(View view) {

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask1Answer.getText()).toString()))
        {
            Snackbar.make(root,"Enter answer for Task 1 please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask2Answer.getText()).toString()))
        {
            Snackbar.make(root,"Enter answer for Task 2 please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask3Answer.getText()).toString()))
        {
            Snackbar.make(root,"Enter answer for Task 3 please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask4Answer.getText()).toString()) && countOfTasks.equals("5") || countOfTasks.equals("7"))
        {
            Snackbar.make(root,"Enter answer for Task 4 please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask5Answer.getText()).toString()) && countOfTasks.equals("5") || countOfTasks.equals("7"))
        {
            Snackbar.make(root,"Enter answer for Task 5 please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask6Answer.getText()).toString()) && countOfTasks.equals("7"))
        {
            Snackbar.make(root,"Enter answer for Task 6 please",Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(etTask7Answer.getText()).toString()) && countOfTasks.equals("7"))
        {
            Snackbar.make(root,"Enter answer for Task 7 please",Snackbar.LENGTH_LONG).show();
            return;
        }


        new AlertDialog.Builder(getContext())
                .setTitle("End confirmation")
                .setMessage("Are you sure you want to finish the test?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        loadingBar.setTitle("Saving...");
                        loadingBar.setMessage("Just wait...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        captureUserAnswer();
                        saveUserResults();

                        loadingBar.dismiss();

                        ((TestMakerActivity)getContext()).setFragResult(userAnswer1,userAnswer2,userAnswer3,userAnswer4,userAnswer5,userAnswer6,userAnswer7,
                                correctAnswer1,correctAnswer2,correctAnswer3,correctAnswer4,correctAnswer5,correctAnswer6,correctAnswer7,
                                completed,countOfTasks,testName);

                    }
                }).create().show();

    }

    private void captureUserAnswer() {
        userAnswer1 = Objects.requireNonNull(etTask1Answer.getText()).toString().trim();
        userAnswer2 = Objects.requireNonNull(etTask2Answer.getText()).toString().trim();
        userAnswer3 = Objects.requireNonNull(etTask3Answer.getText()).toString().trim();
        userAnswer4 = Objects.requireNonNull(etTask4Answer.getText()).toString().trim();
        userAnswer5 = Objects.requireNonNull(etTask5Answer.getText()).toString().trim();
        userAnswer6 = Objects.requireNonNull(etTask6Answer.getText()).toString().trim();
        userAnswer7 = Objects.requireNonNull(etTask7Answer.getText()).toString().trim();

        if (userAnswer1.equalsIgnoreCase(correctAnswer1)) {
            completed += 1;
        }

        if (userAnswer2.equalsIgnoreCase(correctAnswer2)) {
            completed += 1;
        }

        if (userAnswer3.equalsIgnoreCase(correctAnswer3)) {
            completed += 1;
        }

        if (userAnswer4.equalsIgnoreCase(correctAnswer4) && countOfTasks.equals("5") || countOfTasks.equals("7")) {
            completed += 1;
        }

        if (userAnswer5.equalsIgnoreCase(correctAnswer5) && countOfTasks.equals("5") || countOfTasks.equals("7")) {
            completed += 1;
        }

        if (userAnswer6.equalsIgnoreCase(correctAnswer6) && countOfTasks.equals("7")) {
            completed += 1;
        }

        if (userAnswer7.equalsIgnoreCase(correctAnswer7) && countOfTasks.equals("7")) {
            completed += 1;
        }

    }

    private void saveUserResults() {

    }

}