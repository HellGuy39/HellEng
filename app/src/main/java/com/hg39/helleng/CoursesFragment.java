package com.hg39.helleng;


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

    Button btnVocabulary,btnGrammar,btnAudition;
    Button btnVocabularyMore,btnGrammarMore,btnAuditionMore;

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_courses, container, false);

        /*btnVocabulary = rootView.findViewById(R.id.btnVocabulary);
        btnGrammar = rootView.findViewById(R.id.btnGrammar);
        btnAudition = rootView.findViewById(R.id.btnAudition);
        btnVocabularyMore = rootView.findViewById(R.id.btnVocabularyMore);
        btnGrammarMore = rootView.findViewById(R.id.btnGrammarMore);
        btnAuditionMore = rootView.findViewById(R.id.btnAuditionMore);*/

        cardViewVocabulary = rootView.findViewById(R.id.cardViewVocabulary);
        cardViewGrammar = rootView.findViewById(R.id.cardViewGrammar);
        cardViewAudition = rootView.findViewById(R.id.cardViewAudition);

        cardViewGrammar.setOnClickListener(this);
        cardViewVocabulary.setOnClickListener(this);
        cardViewAudition.setOnClickListener(this);

        /*btnVocabulary.setOnClickListener(this);
        btnGrammar.setOnClickListener(this);
        btnAudition.setOnClickListener(this);

        btnVocabularyMore.setOnClickListener(this);
        btnGrammarMore.setOnClickListener(this);
        btnAuditionMore.setOnClickListener(this);*/

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
            Toast.makeText(getContext(), "Nope", Toast.LENGTH_SHORT).show();
        }

        /*if (v.getId() == R.id.btnVocabulary) {
            ((MainActivity) Objects.requireNonNull(getContext())).setFragSelectedVocabulary();
        } else if (v.getId() == R.id.btnVocabularyMore) {
            Toast.makeText(getContext(), "Nope", Toast.LENGTH_SHORT).show();
        }

        if (v.getId() == R.id.btnGrammar) {
            ((MainActivity) Objects.requireNonNull(getContext())).setFragSelectedGrammar();
        } else if (v.getId() == R.id.btnGrammarMore) {
            Toast.makeText(getContext(), "Nope", Toast.LENGTH_SHORT).show();
        }


        if (v.getId() == R.id.btnAudition) {
            Toast.makeText(getContext(), "Nope", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btnAuditionMore) {
            Toast.makeText(getContext(), "Nope", Toast.LENGTH_SHORT).show();
        }*/
    }
}
