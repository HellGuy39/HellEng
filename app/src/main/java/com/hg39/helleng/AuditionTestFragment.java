package com.hg39.helleng;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
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
import com.google.type.TimeOfDayOrBuilder;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.hg39.helleng.MainActivity.futureSimple;
import static com.hg39.helleng.MainActivity.groupGrammar;
import static com.hg39.helleng.MainActivity.humor;
import static com.hg39.helleng.MainActivity.pastSimple;
import static com.hg39.helleng.MainActivity.presentSimple;
import static com.hg39.helleng.MainActivity.superman;

public class AuditionTestFragment extends Fragment {

    TextView ruleTextView,textViewHint1,textViewHint2;
    TextView mediaProgress;
    Button btnEnd;
    TextView textViewResult;
    TextView questionTextView1,questionTextView2,questionTextView3,
            questionTextViewAfter1,questionTextViewAfter2,questionTextViewAfter3;
    TextView questionTextView4,questionTextView5;

    SeekBar seekBar;

    EditText etAnswer4,etAnswer5;

    MediaPlayer humourSound, supermanSound, actualSound;
    com.google.android.material.button.MaterialButton btnPlayOrPause, btnReplay, btnVolumeOnOrOff;

    //https://coderlessons.com/tutorials/mobilnaia-razrabotka/uchitsia-android/android-mediaplayer

    double startTime = 0;
    double finalTime = 0;


    String questions1,questions2,questions3,questions4,questions5,questions6,questions7,questions8,questions9,questions10;
    String question1After,question2After,question3After;
    String answers1,answers2,answers3,answers4,answers5,answers6,answers7,answers8,answers9,answers10;
    String hint1,hint2;
    String rule;

    //private int currentAnswer = 1;
    int completed;
    String testName;
    //private int testsStarted;
    String completedString;

    boolean isPlay, isVolumeOn;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference tests;

    TestProgressControl testProgressControl = new TestProgressControl();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isPlay = false;
        isVolumeOn = true;

        database = FirebaseDatabase.getInstance();

        tests = database.getReference("Tests");
        users = database.getReference("Users");

        humourSound = MediaPlayer.create(getContext(), R.raw.humour);
        supermanSound = MediaPlayer.create(getContext(), R.raw.superman);

        testName = getArguments().getString("testName", "null");

        if (testName.equals(humor)) {
            actualSound = humourSound;
        } else if (testName.equals(superman)) {
            actualSound = supermanSound;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_audition_test,container,false);

        btnPlayOrPause = rootView.findViewById(R.id.btnPlayOrPause);
        btnVolumeOnOrOff = rootView.findViewById(R.id.btnVolumeOnOrOff);
        btnReplay = rootView.findViewById(R.id.btnReplay);

        mediaProgress = rootView.findViewById(R.id.textViewMediaProgress);

        seekBar = rootView.findViewById(R.id.seekBar);

        seekBar.setMax(actualSound.getDuration());
        seekBar.setProgress(actualSound.getCurrentPosition());

        //String maxCount = String.format( "%02d:%02d" , actualSound.getDuration());
        //String actualCount = "00:00";

        //mediaProgress.setText(actualCount + " / " + maxCount );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                soundPlay(actualSound, "change track pos");
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

        textViewResult = rootView.findViewById(R.id.textViewCompleted);

        etAnswer4 = rootView.findViewById(R.id.etAnswer4);
        etAnswer5 = rootView.findViewById(R.id.etAnswer5);

        questionTextView1 = rootView.findViewById(R.id.questionTextView1);
        questionTextView2 = rootView.findViewById(R.id.questionTextView2);
        questionTextView3 = rootView.findViewById(R.id.questionTextView3);
        questionTextView4 = rootView.findViewById(R.id.questionTextView4);
        questionTextView5 = rootView.findViewById(R.id.questionTextView5);

        questionTextViewAfter1 = rootView.findViewById(R.id.questionTextViewAfter1);
        questionTextViewAfter2 = rootView.findViewById(R.id.questionTextViewAfter2);
        questionTextViewAfter3 = rootView.findViewById(R.id.questionTextViewAfter3);

        ruleTextView = rootView.findViewById(R.id.ruleTextView);
        textViewHint1 = rootView.findViewById(R.id.textViewHint1);
        textViewHint2 = rootView.findViewById(R.id.textViewHint2);

        updateUI();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    public void onStop() {
        super.onStop();
        soundPlay(actualSound, "pause");
        isPlay = false;
        btnPlayOrPause.setIcon(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundPlay(actualSound, "pause");
        isPlay = false;
        btnPlayOrPause.setIcon(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPlay(actualSound, "pause");
        isPlay = false;
        btnPlayOrPause.setIcon(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
    }

    private void soundPlay(MediaPlayer sound, String ToDo) {
        if (ToDo.equals("play")) {
            sound.start();

        } else if (ToDo.equals("pause")) {
            sound.pause();
        } else if (ToDo.equals("replay")) {
            //sound.reset();
            sound.seekTo(0);
        } else if (ToDo.equals("volumeOff")) {
            sound.setVolume(0f,0f);
        } else if (ToDo.equals("volumeOn")) {
            sound.setVolume(1f,1f);
        } else if (ToDo.equals("change track pos")) {
            sound.seekTo(seekBar.getProgress());
        }
    }

    private void onClickControls(View view) {

        if (view.getId() == R.id.btnPlayOrPause) {
            if (isPlay == true) {
                soundPlay(actualSound, "pause");
                isPlay = false;
                btnPlayOrPause.setIcon(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
            } else if (isPlay == false) {
                soundPlay(actualSound, "play");
                isPlay = true;
                btnPlayOrPause.setIcon(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
            }
        } else if (view.getId() == R.id.btnReplay) {
                soundPlay(actualSound, "replay");
        } else if (view.getId() == R.id.btnVolumeOnOrOff) {
            if (isVolumeOn == true) {
                soundPlay(actualSound, "volumeOff");
                isVolumeOn = false;
                btnVolumeOnOrOff.setIcon(getResources().getDrawable(R.drawable.ic_baseline_volume_off_24));
            } else if (isVolumeOn == false) {
                soundPlay(actualSound, "volumeOn");
                isVolumeOn = true;
                btnVolumeOnOrOff.setIcon(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24));
            }
        }

    }

    private void updateUI() {

        /*questionTextView1.setText(questions1);questionTextViewAfter1.setText(question1After);
        questionTextView2.setText(questions2);questionTextViewAfter2.setText(question2After);
        questionTextView3.setText(questions3);questionTextViewAfter3.setText(question3After);
        questionTextView4.setText(questions4);
        questionTextView5.setText(questions5);

        textViewHint1.setText(hint1);
        textViewHint2.setText(hint2);*/

    }

    private void onClickEnd(View view) {

        /*slot4Res = etAnswer4.getText().toString();
        slot5Res = etAnswer5.getText().toString();

        //КостыльTechnologies
        if (slot1Res == null) {
            slot1Res = " ";
        }
        if (slot2Res == null) {
            slot2Res = " ";
        }
        if (slot3Res == null) {
            slot3Res = " ";
        }

        if (slot1Res.equalsIgnoreCase(answers1)) {
            completed+=20;
        }
        if (slot2Res.equalsIgnoreCase(answers2)) {
            completed+=20;
        }
        if (slot3Res.equalsIgnoreCase(answers3)) {
            completed+=20;
        }

        testProgressControl.SaveTestProgress(groupGrammar,testName,completedString);

        ((AuditionActivity) Objects.requireNonNull(getContext())).setFragResult();*/

    }
}
