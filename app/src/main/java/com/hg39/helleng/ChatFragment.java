package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatFragment extends Fragment {

    com.google.android.material.floatingactionbutton.FloatingActionButton floatingButtonAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView
                = inflater.inflate(R.layout.fragment_chat,container,false);

        floatingButtonAdd = rootView.findViewById(R.id.floatingButtonAdd);

        return rootView;
    }
}
