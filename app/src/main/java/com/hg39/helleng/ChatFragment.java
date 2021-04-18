package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.SplittableRandom;

public class ChatFragment extends Fragment {

    com.google.android.material.floatingactionbutton.FloatingActionButton floatingButtonAdd;
    RecyclerView chatList;

    FirebaseDatabase database;

    DatabaseReference chatsRef, usersRef;
    FirebaseAuth mAuth;
    String currentUserID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        chatsRef = database.getReference().child("Friends").child(currentUserID);
        usersRef = database.getReference().child("Users");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView
                = inflater.inflate(R.layout.fragment_chat,container,false);

        chatList = rootView.findViewById(R.id.chatsList);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingButtonAdd = rootView.findViewById(R.id.floatingButtonAdd);
        floatingButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).setFragFriends();
            }
        });

        loadUsers();

        return rootView;
    }

    private void updateUI() {

    }

    private void loadUsers() {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(chatsRef,User.class)
                        .build();

        FirebaseRecyclerAdapter<User,UserViewHolder> adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {

                final String usersIDs = getRef(position).getKey();
                final String[] retImage = {"default_image"};

                usersRef.child(usersIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.hasChild("profileImage")) {
                                retImage[0] = snapshot.child("profileImage").getValue(String.class);
                                Picasso.get().load(retImage[0]).into(holder.profileImage);
                            } else {
                                holder.profileImage.setImageResource(R.drawable.no_avatar);
                            }

                            final String retFullName = snapshot.child("firstName").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class);
                            final String retUserStatus = snapshot.child("status").getValue(String.class);

                            holder.username.setText(retFullName);
                            holder.status.setText(retUserStatus);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(),DialogActivity.class);

                                    intent.putExtra("visit_user_id", usersIDs);
                                    intent.putExtra("visit_user_name", retFullName);
                                    intent.putExtra("visit_user_image", retImage[0]);

                                    startActivity(intent);
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_user,parent,false);
                return new UserViewHolder(view);
            }
        };
        chatList.setAdapter(adapter);
        adapter.startListening();
    }


}
