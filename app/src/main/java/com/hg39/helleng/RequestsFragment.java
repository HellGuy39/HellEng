package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestsFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;

    //com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton extendedFloatingActionButtonFind;
    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    //DatabaseReference mRef;
    //FirebaseUser mUser;

    DatabaseReference friendRequestsRef, usersRef, friendsRef;
    String currentUserID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        friendRequestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        //mUser = mAuth.getCurrentUser();
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView
                = inflater.inflate(R.layout.fragment_requests,container,false);

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

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(friendRequestsRef.child(currentUserID), User.class)
                .build();

        FirebaseRecyclerAdapter<User, RequestsViewHolder> adapter = new FirebaseRecyclerAdapter<User, RequestsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder, int position, @NonNull User model) {

                final String list_user_ID = getRef(position).getKey();
                DatabaseReference getTypeRef = getRef(position).child("requestType").getRef();

                getTypeRef.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if (snapshot.exists())
                        {
                            String type = snapshot.getValue(String.class);
                            if (Objects.requireNonNull(type).equalsIgnoreCase("received"))
                            {
                                usersRef.child(Objects.requireNonNull(list_user_ID)).addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot)
                                    {

                                        final String requestUserName = snapshot.child("fullName").getValue(String.class);
                                        final String requestUserStatus = "would like add you as a friend";//snapshot.child("status").getValue(String.class);

                                        if (snapshot.hasChild("profileImage"))
                                        {
                                            final String requestUserProfileImage = snapshot.child("profileImage").getValue(String.class);
                                            Picasso.get().load(requestUserProfileImage).into(holder.profileImage);
                                        }
                                        else
                                        {
                                            holder.profileImage.setImageResource(R.drawable.no_avatar);
                                        }

                                        holder.username.setText(requestUserName);
                                        holder.status.setText(requestUserStatus);

                                        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                friendsRef.child(currentUserID).child(list_user_ID).child("Friends").setValue("Saved")
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                        friendsRef.child(list_user_ID).child(currentUserID).child("Friends").setValue("Saved")
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            friendRequestsRef.child(currentUserID).child(list_user_ID).removeValue()
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful())
                                                                                        {
                                                                                        friendRequestsRef.child(list_user_ID).child(currentUserID).removeValue()
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful())
                                                                                                        {
                                                                                                            Toast.makeText(getContext(),
                                                                                                                    "New Friend Added",
                                                                                                                    Toast.LENGTH_SHORT)
                                                                                                                    .show();
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                        holder.btn_decline.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                friendRequestsRef.child(currentUserID).child(list_user_ID).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful())
                                                                {
                                                                    friendRequestsRef.child(list_user_ID).child(currentUserID).removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful())
                                                                                    {
                                                                                        Toast.makeText(getContext(),
                                                                                                "Request Declined",
                                                                                                Toast.LENGTH_SHORT)
                                                                                                .show();
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        });

                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //!!
                                                ((MainActivity) Objects.requireNonNull(getContext())).setFragViewOtherProfile(getRef(position).getKey());
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error)
                                    {

                                    }
                                });
                            }
                            else if (Objects.requireNonNull(type).equalsIgnoreCase("sent"))
                            {
                                holder.itemView.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_single_view_user, parent, false);
                return new RequestsViewHolder(view);

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        //loadFriend("");

        return rootView;
    }

    public static class RequestsViewHolder extends RecyclerView.ViewHolder {

        TextView username, status;
        CircleImageView profileImage;
        Button btn_accept, btn_decline;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            status = itemView.findViewById(R.id.status);
            profileImage = itemView.findViewById(R.id.profileImage);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_decline = itemView.findViewById(R.id.btn_decline);

        }
    }

    @Override
    public void onClick(View v) {

        /*if (v.getId() == R.id.floatingButtonFind) {
            ((MainActivity)getContext()).setFragFindFriends();
        }*/

    }
}
