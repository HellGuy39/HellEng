package com.hg39.helleng;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.User;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class FriendsFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;

    //com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton extendedFloatingActionButtonFind;
    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseRecyclerOptions<User>options;
    FirebaseRecyclerAdapter<User,UserViewHolder>adapter;

    FirebaseAuth mAuth;
    DatabaseReference friendsRef, usersRef;
    FirebaseUser mUser;

    String currentUserID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends").child(currentUserID);
        mUser = mAuth.getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView
                = inflater.inflate(R.layout.fragment_friends,container,false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).onBackPressed();
                //((MainActivity)getContext()).setFragChat();
            }
        });

        //extendedFloatingActionButtonFind = rootView.findViewById(R.id.floatingButtonFind);
        //extendedFloatingActionButtonFind.setOnClickListener(this);

        loadFriend();

        return rootView;
    }

    private void loadFriend() {
        //Query query = mRef.child(mUser.getUid()).orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<User>().setQuery(friendsRef,User.class).build();
        adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {

                String userIDs = getRef(position).getKey();

                usersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String username = snapshot.child("fullName").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);

                        if (snapshot.hasChild("profileImage")) {
                            String profileImage = snapshot.child("profileImage").getValue(String.class);
                            Picasso.get().load(profileImage).placeholder(R.drawable.no_avatar).into(holder.profileImage);
                        } else {
                            holder.profileImage.setImageResource(R.drawable.no_avatar);
                        }

                        holder.username.setText(username);
                        holder.status.setText(status);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                /*if (model.getProfileImage() != null) {
                    Picasso.get().load(model.getProfileImage()).into(holder.profileImage);
                } else {
                    holder.profileImage.setImageResource(R.drawable.no_avatar);
                }

                holder.username.setText(model.getUsername());
                holder.status.setText(model.getUserStatus());*/

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) Objects.requireNonNull(getContext())).setFragViewOtherProfile(getRef(position).getKey());
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
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        /*if (v.getId() == R.id.floatingButtonFind) {
            ((MainActivity)getContext()).setFragFindFriends();
        }*/

    }
}
