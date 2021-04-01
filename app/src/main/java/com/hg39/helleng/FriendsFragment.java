package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FriendsFragment extends Fragment implements View.OnClickListener{

    ListView friendsList;

    com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton extendedFloatingActionButtonFind;
    com.google.android.material.appbar.MaterialToolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView
                = inflater.inflate(R.layout.fragment_friends,container,false);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).setFragHome();
            }
        });

        extendedFloatingActionButtonFind = rootView.findViewById(R.id.floatingButtonFind);
        extendedFloatingActionButtonFind.setOnClickListener(this::onClick);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.floatingButtonFind) {
            ((MainActivity)getContext()).setFragFindFriends();
        }

    }
}
