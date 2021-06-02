package com.hg39.helleng.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    List<Post> userPostList;
    FirebaseAuth mAuth;
    DatabaseReference usersRef;

    public PostAdapter (List<Post> userPostList) {
        this.userPostList = userPostList;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public TextView authorName,date, postText;
        public CircleImageView authorProfileImage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.postDate);
            postText = itemView.findViewById(R.id.tvPostText);
            authorProfileImage = itemView.findViewById(R.id.profileImage);

        }
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_post_view, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

        //String authorID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        Post post = userPostList.get(position);

        //String fromUserID = messages.getFrom();
        String postType = post.getType();
        String authorID  = post.getAuthor();

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(authorID);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild("profileImage")) {
                    String profileImage = snapshot.child("profileImage").getValue(String.class);
                    Picasso.get().load(profileImage).placeholder(R.drawable.no_avatar).into(holder.authorProfileImage);
                }

                String username = snapshot.child("fullName").getValue(String.class);
                holder.authorName.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (postType.equals("text"))
        {

            holder.postText.setText(post.getText());
            holder.date.setText(post.getDate());

        }

    }

    @Override
    public int getItemCount() {
        return userPostList.size();
    }

}
