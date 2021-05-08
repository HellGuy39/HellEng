package com.hg39.helleng;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class CoursesFragment extends Fragment implements View.OnClickListener {

    com.google.android.material.card.MaterialCardView cardViewVocabulary, cardViewGrammar,cardViewAudition;

    /*1 - furniture
     *2 - school supplies
     *3 - food
     *4 - present
     *5 - past
     *6 - future
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Context context = MyContextWrapper.wrap(Objects.requireNonNull(getContext())/*in fragment use getContext() instead of this*/, "en");
        //getResources().updateConfiguration(context.getResources().getConfiguration(), context.getResources().getDisplayMetrics());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_courses, container, false);

        cardViewVocabulary = rootView.findViewById(R.id.cardViewVocabulary);
        cardViewGrammar = rootView.findViewById(R.id.cardViewGrammar);
        cardViewAudition = rootView.findViewById(R.id.cardViewAudition);

        cardViewGrammar.setOnClickListener(this);
        cardViewVocabulary.setOnClickListener(this);
        cardViewAudition.setOnClickListener(this);

        return rootView;
    }

    //private void updateUI(){ }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cardViewGrammar) {
            ((MainActivity) Objects.requireNonNull(getContext())).setFragSelectedGrammar();
        } else if (v.getId() == R.id.cardViewVocabulary) {
            ((MainActivity) Objects.requireNonNull(getContext())).setFragSelectedVocabulary();
        } else if (v.getId() == R.id.cardViewAudition) {
            ((MainActivity) Objects.requireNonNull(getContext())).setFragSelectedAudition();
        }
    }
}
