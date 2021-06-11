package com.hg39.helleng;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.hg39.helleng.MainActivity.groupAudition;
import static com.hg39.helleng.MainActivity.humor;
import static com.hg39.helleng.MainActivity.superman;

public class AuditionTestFragment extends Fragment {

    TextView ruleTextView,textViewHint1,textViewHint2;
    TextView mediaProgress;
    Button btnEnd;

    SeekBar seekBar;

    RadioButton task1RdBtn1,task1RdBtn2,task1RdBtn3,
                task2RdBtn1,task2RdBtn2,task2RdBtn3,
                task3RdBtn1,task3RdBtn2,task3RdBtn3;

    RadioGroup group1,group2,group3;

    MediaPlayer humourSound, supermanSound, actualSound;

    com.google.android.material.textfield.TextInputEditText etAnswer1,etAnswer2,etAnswer3;

    com.google.android.material.button.MaterialButton btnPlayOrPause, btnReplay, btnVolumeOnOrOff;

    com.google.android.material.card.MaterialCardView cardRadioButtons1,cardRadioButtons2,cardRadioButtons3,
                                                        cardInputText1,cardInputText2,cardInputText3;

    String[] questions1,questions2,questions3;
    String[] questionsBefore,questionsAfter;

    String answers1,answers2,answers3;
    String userAnswer1,userAnswer2,userAnswer3;
    String hint1,hint2;



    TextView taskView1,taskView2,taskView3;//task1Before,task2Before,task3Before,
            //task1After,task2After,task3After;

    //private int currentAnswer = 1;
    int completed;
    String testName;
    //private int testsStarted;
    String completedString;

    boolean isPlay, isVolumeOn;

    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference tests;


    TestProgressControl testProgressControl = new TestProgressControl();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionsAfter = new String[3];
        questionsBefore = new String[3];

        questions1 = new String[3];
        questions2 = new String[3];
        questions3 = new String[3];

        isPlay = false;
        isVolumeOn = true;

        database = FirebaseDatabase.getInstance();

        tests = database.getReference("Tests");
        users = database.getReference("Users");

        humourSound = MediaPlayer.create(getContext(), R.raw.humour);
        supermanSound = MediaPlayer.create(getContext(), R.raw.superman);

        testName = requireArguments().getString("testName", "null");

        if (testName.equals(humor))
        {
            actualSound = humourSound;
        }
        else if (testName.equals(superman))
        {
            actualSound = supermanSound;
        }

        tests.child(groupAudition).child(testName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (testName.equals(humor)) {

                    questions1[0] = snapshot.child("Variants").child("0").child("0").getValue(String.class);
                    questions1[1] = snapshot.child("Variants").child("0").child("1").getValue(String.class);
                    questions1[2] = snapshot.child("Variants").child("0").child("2").getValue(String.class);

                    questions2[0] = snapshot.child("Variants").child("1").child("0").getValue(String.class);
                    questions2[1] = snapshot.child("Variants").child("1").child("1").getValue(String.class);
                    questions2[2] = snapshot.child("Variants").child("1").child("2").getValue(String.class);

                    questions3[0] = snapshot.child("Variants").child("2").child("0").getValue(String.class);
                    questions3[1] = snapshot.child("Variants").child("2").child("1").getValue(String.class);
                    questions3[2] = snapshot.child("Variants").child("2").child("2").getValue(String.class);

                } else if (testName.equals(superman)) {

                    questionsBefore[0] = snapshot.child("Variants").child("Before").child("0").getValue(String.class);
                    questionsAfter[0] = snapshot.child("Variants").child("After").child("0").getValue(String.class);

                    questionsBefore[1] = snapshot.child("Variants").child("Before").child("1").getValue(String.class);
                    questionsAfter[1] = snapshot.child("Variants").child("After").child("1").getValue(String.class);

                    questionsBefore[2] = snapshot.child("Variants").child("Before").child("2").getValue(String.class);
                    questionsAfter[2] = snapshot.child("Variants").child("After").child("2").getValue(String.class);

                }

                answers1 = snapshot.child("Answers").child("0").getValue(String.class);
                answers2 = snapshot.child("Answers").child("1").getValue(String.class);
                answers3 = snapshot.child("Answers").child("2").getValue(String.class);

                hint1 = snapshot.child("Hint").child("0").getValue(String.class);
                hint2 = snapshot.child("Hint").child("1").getValue(String.class);

                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_audition_test,container,false);

        group1 = rootView.findViewById(R.id.group1);
        task1RdBtn1 = rootView.findViewById(R.id.task1RdBtn1);
        task1RdBtn2 = rootView.findViewById(R.id.task1RdBtn2);
        task1RdBtn3 = rootView.findViewById(R.id.task1RdBtn3);

        group2 = rootView.findViewById(R.id.group2);
        task2RdBtn1 = rootView.findViewById(R.id.task2RdBtn1);
        task2RdBtn2 = rootView.findViewById(R.id.task2RdBtn2);
        task2RdBtn3 = rootView.findViewById(R.id.task2RdBtn3);

        group3 = rootView.findViewById(R.id.group3);
        task3RdBtn1 = rootView.findViewById(R.id.task3RdBtn1);
        task3RdBtn2 = rootView.findViewById(R.id.task3RdBtn2);
        task3RdBtn3 = rootView.findViewById(R.id.task3RdBtn3);

        taskView1 = rootView.findViewById(R.id.tvTask1);
        taskView2 = rootView.findViewById(R.id.tvTask2);
        taskView3 = rootView.findViewById(R.id.tvTask3);

        etAnswer1 = rootView.findViewById(R.id.etAnswer1);
        etAnswer2 = rootView.findViewById(R.id.etAnswer2);
        etAnswer3 = rootView.findViewById(R.id.etAnswer3);

        cardRadioButtons1 = rootView.findViewById(R.id.cardRadioButtons1);
        cardRadioButtons2 = rootView.findViewById(R.id.cardRadioButtons2);
        cardRadioButtons3 = rootView.findViewById(R.id.cardRadioButtons3);

        cardInputText1 = rootView.findViewById(R.id.cardInputText1);
        cardInputText2 = rootView.findViewById(R.id.cardInputText2);
        cardInputText3 = rootView.findViewById(R.id.cardInputText3);

        if (testName.equals(humor))
        {

            cardRadioButtons1.setVisibility(View.VISIBLE);
            cardRadioButtons2.setVisibility(View.VISIBLE);
            cardRadioButtons3.setVisibility(View.VISIBLE);

            cardInputText1.setVisibility(View.GONE);
            cardInputText2.setVisibility(View.GONE);
            cardInputText3.setVisibility(View.GONE);

            radioButtonsSetListeners();
        }
        else if (testName.equals(superman))
        {
            cardRadioButtons1.setVisibility(View.GONE);
            cardRadioButtons2.setVisibility(View.GONE);
            cardRadioButtons3.setVisibility(View.GONE);

            cardInputText1.setVisibility(View.VISIBLE);
            cardInputText2.setVisibility(View.VISIBLE);
            cardInputText3.setVisibility(View.VISIBLE);

        }


        btnPlayOrPause = rootView.findViewById(R.id.btnPlayOrPause);
        btnVolumeOnOrOff = rootView.findViewById(R.id.btnVolumeOnOrOff);
        btnReplay = rootView.findViewById(R.id.btnReplay);

        mediaProgress = rootView.findViewById(R.id.textViewMediaProgress);

        seekBar = rootView.findViewById(R.id.seekBar);

        seekBar.setMax(actualSound.getDuration());
        seekBar.setProgress(actualSound.getCurrentPosition());

        mediaProgress.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(actualSound.getCurrentPosition()),
                TimeUnit.MILLISECONDS.toSeconds(actualSound.getCurrentPosition()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(actualSound.getCurrentPosition()))) + " / " +
                String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(actualSound.getDuration()),
                        TimeUnit.MILLISECONDS.toSeconds(actualSound.getDuration()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(actualSound.getDuration()))));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    soundPlay(actualSound, "change track pos");
                }
                    mediaProgress.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(actualSound.getCurrentPosition()),
                            TimeUnit.MILLISECONDS.toSeconds(actualSound.getCurrentPosition()) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(actualSound.getCurrentPosition()))) + " / " +
                            String.format("%02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(actualSound.getDuration()),
                                    TimeUnit.MILLISECONDS.toSeconds(actualSound.getDuration()) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(actualSound.getDuration()))));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlayOrPause.setOnClickListener(this::onClickControls);
        btnVolumeOnOrOff.setOnClickListener(this::onClickControls);
        btnReplay.setOnClickListener(this::onClickControls);

        btnEnd = rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(this::onClickEnd);

        ruleTextView = rootView.findViewById(R.id.ruleTextView);
        textViewHint1 = rootView.findViewById(R.id.textViewHint1);
        textViewHint2 = rootView.findViewById(R.id.textViewHint2);

        //updateUI();
        new Thread(() -> {
            while (actualSound != null) {
                try {
                    if (actualSound.isPlaying()) {
                        Message message = new Message();
                        message.what = actualSound.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return rootView;
    }

    @SuppressLint("HandlerLeak")
    final
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            seekBar.setProgress(msg.what);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        //updateUI();
    }

    @Override
    public void onStop() {
        super.onStop();
        soundPlay(actualSound, "pause");
        isPlay = false;
        btnPlayOrPause.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_play_arrow_24, null));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundPlay(actualSound, "pause");
        isPlay = false;
        btnPlayOrPause.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_play_arrow_24, null));
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPlay(actualSound, "pause");
        isPlay = false;
        btnPlayOrPause.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_play_arrow_24, null));
    }

    private void soundPlay(MediaPlayer sound, String ToDo) {
        switch (ToDo) {
            case "play":
                sound.start();
                break;

            case "pause":
                sound.pause();
                break;

            case "replay":
                //sound.reset();
                sound.seekTo(0);
                break;

            case "volumeOff":
                sound.setVolume(0f, 0f);
                break;

            case "volumeOn":
                sound.setVolume(1f, 1f);
                break;

            case "change track pos":
                sound.seekTo(seekBar.getProgress());
                break;
        }
    }

    private void onClickControls(View view) {

        if (view.getId() == R.id.btnPlayOrPause)
        {
            if (isPlay)
            {
                soundPlay(actualSound, "pause");
                isPlay = false;
                btnPlayOrPause.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_play_arrow_24, null));
            }
            else {
                soundPlay(actualSound, "play");
                isPlay = true;
                btnPlayOrPause.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_pause_24, null));
            }
        }
        else if (view.getId() == R.id.btnReplay)
        {
                soundPlay(actualSound, "replay");
        }
        else if (view.getId() == R.id.btnVolumeOnOrOff)
        {
            if (isVolumeOn)
            {
                soundPlay(actualSound, "volumeOff");
                isVolumeOn = false;
                btnVolumeOnOrOff.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_volume_off_24, null));
            }
            else
            {
                soundPlay(actualSound, "volumeOn");
                isVolumeOn = true;
                btnVolumeOnOrOff.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_volume_up_24, null));
            }
        }

    }

    private void updateUI() {

        if (actualSound == humourSound)
        {
            radioButtonsSetText();
        }
        else if (actualSound == supermanSound)
        {
            textViewsSetText();
        }

        textViewHint1.setText(hint1);
        textViewHint2.setText(hint2);

    }

    @SuppressLint("SetTextI18n")
    private void textViewsSetText() {

        taskView1.setText(questionsBefore[0] + "  _ _ _  " + questionsAfter[0]);
        taskView2.setText(questionsBefore[1] + "  _ _ _  " + questionsAfter[1]);
        taskView3.setText(questionsBefore[2] + "  _ _ _  " + questionsAfter[2]);

    }

    @SuppressLint("NonConstantResourceId")
    private void radioButtonsSetListeners() {
        group1.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.task1RdBtn1:
                    userAnswer1 = task1RdBtn1.getText().toString();
                    break;

                case R.id.task1RdBtn2:
                    userAnswer1 = task1RdBtn2.getText().toString();
                    break;

                case R.id.task1RdBtn3:
                    userAnswer1 = task1RdBtn3.getText().toString();
                    break;
            }
        });

        group2.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.task2RdBtn1:
                    userAnswer2 = task2RdBtn1.getText().toString();
                    break;

                case R.id.task2RdBtn2:
                    userAnswer2 = task2RdBtn2.getText().toString();
                    break;

                case R.id.task2RdBtn3:
                    userAnswer2 = task2RdBtn3.getText().toString();
                    break;
            }
        });

        group3.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.task3RdBtn1:
                    userAnswer3 = task3RdBtn1.getText().toString();
                    break;

                case R.id.task3RdBtn2:
                    userAnswer3 = task3RdBtn2.getText().toString();
                    break;

                case R.id.task3RdBtn3:
                    userAnswer3 = task3RdBtn3.getText().toString();
                    break;
            }
        });
    }

    private void radioButtonsSetText() {

        task1RdBtn1.setText(questions1[0]);
        task1RdBtn2.setText(questions1[1]);
        task1RdBtn3.setText(questions1[2]);

        task2RdBtn1.setText(questions2[0]);
        task2RdBtn2.setText(questions2[1]);
        task2RdBtn3.setText(questions2[2]);

        task3RdBtn1.setText(questions3[0]);
        task3RdBtn2.setText(questions3[1]);
        task3RdBtn3.setText(questions3[2]);
    }

    private void onClickEnd(View view) {

        //slot4Res = etAnswer4.getText().toString();
        //slot5Res = etAnswer5.getText().toString();

        if (testName.equals(superman)) {

            userAnswer1 = Objects.requireNonNull(etAnswer1.getText()).toString().trim();
            userAnswer2 = Objects.requireNonNull(etAnswer2.getText()).toString().trim();
            userAnswer3 = Objects.requireNonNull(etAnswer3.getText()).toString().trim();
        }

        //КостыльTechnologies
        if (userAnswer1 == null) {
            userAnswer1 = " ";
        }
        if (userAnswer2 == null) {
            userAnswer2 = " ";
        }
        if (userAnswer3 == null) {
            userAnswer3 = " ";
        }

        if (userAnswer1.equalsIgnoreCase(answers1)) {
            completed+=1;
        }
        if (userAnswer2.equalsIgnoreCase(answers2)) {
            completed+=1;
        }
        if (userAnswer3.equalsIgnoreCase(answers3)) {
            completed+=1;
        }

        completedString = completed + "/3";

        testProgressControl.SaveTestProgress(groupAudition,testName,completedString);

        ((AuditionActivity) requireContext()).setFragResult(userAnswer1,userAnswer2,userAnswer3,
                                                                                answers1,answers2,answers3,
                                                                                completed, 3,testName);

    }
}
