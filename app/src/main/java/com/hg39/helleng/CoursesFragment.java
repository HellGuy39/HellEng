package com.hg39.helleng;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class CoursesFragment extends Fragment implements View.OnClickListener {

    com.google.android.material.card.MaterialCardView cardViewVocabulary, cardViewGrammar,
            cardViewAudition, cardViewCreateTest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_courses, container, false);

        cardViewCreateTest = rootView.findViewById(R.id.cardViewCreateTest);
        cardViewVocabulary = rootView.findViewById(R.id.cardViewVocabulary);
        cardViewGrammar = rootView.findViewById(R.id.cardViewGrammar);
        cardViewAudition = rootView.findViewById(R.id.cardViewAudition);

        cardViewGrammar.setOnClickListener(this);
        cardViewVocabulary.setOnClickListener(this);
        cardViewAudition.setOnClickListener(this);
        cardViewCreateTest.setOnClickListener(this);

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
        } else if (v.getId() == R.id.cardViewCreateTest) {
            ((MainActivity) Objects.requireNonNull(getContext())).setFragSelectedCreateTest();
        }
    }
}
