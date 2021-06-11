package com.hg39.helleng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hg39.helleng.Models.AuthorPostAdapter;
import com.hg39.helleng.Models.Post;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hg39.helleng.MainActivity.LOG_TAG;

public class ProfileFragment extends Fragment {

    TextView textViewUserStatus,textViewReg,textViewUserId;
    TextView textViewFullName;
    TextView textViewWebStatus;
    TextView textViewBirthday;
    TextView textViewCity;
    TextView textViewAboutMe;
    CircleImageView profileImage;
    com.google.android.material.appbar.MaterialToolbar topAppBar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users, postsRef;
    StorageReference storageReference;
    StorageReference profileRef;

    String firstNStr,lastNStr,statusStr,regDate,userId;
    String profileImageUri;
    String webStatus;
    String birthday, city, aboutMe;
    String selectedBackground;
    String postAuthorID;

    ImageView imageBackground;

    Button btnCreatePost,btnAttachments;
    EditText editTextNewPost;
    RecyclerView recyclerViewMicroBlog;
    LinearLayoutManager linearLayoutManager;
    //PostAdapter postAdapter;
    AuthorPostAdapter authorPostAdapter;

    ValueEventListener dataListener;

    final List<Post> postsList = new ArrayList<>();

    boolean isLeaving = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        postAuthorID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        postsRef = FirebaseDatabase.getInstance().getReference();

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        if (mAuth != null) {
            userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        }

        dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                firstNStr = dataSnapshot.child("firstName").getValue(String.class);
                lastNStr = dataSnapshot.child("lastName").getValue(String.class);
                statusStr = dataSnapshot.child("status").getValue(String.class);
                regDate = dataSnapshot.child("registerDate").getValue(String.class);
                webStatus = dataSnapshot.child("webStatus").getValue(String.class);
                profileImageUri = dataSnapshot.child("profileImage").getValue(String.class);
                birthday = dataSnapshot.child("birthday").getValue(String.class);
                city = dataSnapshot.child("city").getValue(String.class);
                aboutMe = dataSnapshot.child("aboutMe").getValue(String.class);
                selectedBackground = dataSnapshot.child("background").getValue(String.class);

                updateUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };


    }

    @SuppressLint("NonConstantResourceId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_profile,container,false);

        imageBackground = rootView.findViewById(R.id.imageBackground);

        btnCreatePost = rootView.findViewById(R.id.btnCreatePost);
        btnAttachments = rootView.findViewById(R.id.btnAttachments);
        editTextNewPost = rootView.findViewById(R.id.editTextNewPost);
        recyclerViewMicroBlog = rootView.findViewById(R.id.recyclerViewMicroBlog);

        topAppBar = rootView.findViewById(R.id.topAppBar);
        textViewUserId = rootView.findViewById(R.id.textViewUserId);
        textViewReg = rootView.findViewById(R.id.textViewReg);
        textViewAboutMe = rootView.findViewById(R.id.textViewAboutMe);
        textViewCity = rootView.findViewById(R.id.textViewCity);
        textViewBirthday = rootView.findViewById(R.id.textViewBirthday);

        textViewFullName = rootView.findViewById(R.id.textViewFullName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);
        profileImage = rootView.findViewById(R.id.profileImage);
        textViewWebStatus = rootView.findViewById(R.id.textViewWebStatus);

        recyclerViewMicroBlog = rootView.findViewById(R.id.recyclerViewMicroBlog);
        //postAdapter = new PostAdapter(postsList);
        authorPostAdapter = new AuthorPostAdapter(postsList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMicroBlog.setLayoutManager(linearLayoutManager);
        recyclerViewMicroBlog.setAdapter(authorPostAdapter);//postAdapter);

        btnCreatePost.setOnClickListener(this::onClickCreatePost);

        topAppBar.setOverflowIcon(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_menu_24,null));
        topAppBar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {

                case R.id.editProfile:
                    users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).removeEventListener(dataListener);
                    ((MainActivity) requireActivity())
                            .setEditProfileFragment();
                    break;

                case R.id.settings:
                    users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).removeEventListener(dataListener);
                    startActivity(new Intent(getContext(),SettingsActivity.class));
                    break;

                case R.id.aboutTheApp:
                    users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).removeEventListener(dataListener);
                    startActivity(new Intent(getContext(),AboutTheAppActivity.class));
                    break;

                case R.id.signOut:

                    //<Костыль Technologies>
                    //postAdapter = null;
                    authorPostAdapter = null;
                    //</>
                    isLeaving = true;

                    users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).removeEventListener(dataListener);

                    ((MainActivity)
                            requireContext())
                            .signOut();
                    break;
            }

            return false;
        });

        loadPosts();
        //updateUI();
        users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).addValueEventListener(dataListener);

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {

        if (webStatus == null)
            webStatus = "";

        if (regDate == null)
            regDate = "";

        if (firstNStr == null)
            firstNStr = "";

        if (lastNStr == null)
            lastNStr = "";

        if (statusStr == null)
            statusStr = "";

        if (userId == null)
            userId = "";

        if (city != null)
            textViewCity.setText("City: " + city);
        else
            textViewCity.setVisibility(View.GONE);

        if (birthday != null)
            textViewBirthday.setText("Birthday: " + birthday);
        else
            textViewBirthday.setVisibility(View.GONE);

        if (aboutMe != null)
            textViewAboutMe.setText("About me: " + aboutMe);
        else
            textViewAboutMe.setVisibility(View.GONE);

        if (selectedBackground != null)
            setBackground(selectedBackground);

        textViewWebStatus.setText(webStatus);
        textViewReg.setText("Registered since " + regDate);
        textViewFullName.setText(firstNStr + " " + lastNStr);
        textViewUserStatus.setText(statusStr);
        textViewUserId.setText("Id: " + userId);

        if (profileImageUri != null) {
            Picasso.get().load(profileImageUri).placeholder(R.drawable.no_avatar).into(profileImage);
        } else {
            //profileImage.setImageDrawable(getResources().getDrawable(R.drawable.no_avatar));
            profileImage.setImageResource(R.drawable.no_avatar);
        }

    }

    private void loadPosts() {
        postsRef.child("Users").child(postAuthorID).child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                postsList.add(post);
                //postAdapter.notifyDataSetChanged();
                authorPostAdapter.notifyDataSetChanged();

                Log.d(LOG_TAG, "Post added, key: " + Objects.requireNonNull(post).getKey());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClickCreatePost(View view) {

        String postText = editTextNewPost.getText().toString().trim();

        if (TextUtils.isEmpty(postText))
        {
            //Nothing do
            Log.d(LOG_TAG,"Empty text input view");
        }
        else
        {

            String postAuthorRef = "Users/" + postAuthorID + "/Posts";

            DatabaseReference userPostKeyRef = postsRef.child("Users")
                    .child(postAuthorID).child("Posts").push();

            String postsPushID = userPostKeyRef.getKey();

            Date currentDate = new Date();
            // Форматирование времени как "день.месяц.год"
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);

            // Форматирование времени как "часы:минуты:секунды"
            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String timeText = timeFormat.format(currentDate);

            String date = dateText + " in " + timeText;

            Map<String, String> postBody = new HashMap<>();
            postBody.put("text", postText);
            postBody.put("type", "text");
            postBody.put("author", postAuthorID);
            postBody.put("date", date);
            postBody.put("key", postsPushID);

            Map<String, Object> postBodyDetails = new HashMap<>();
            postBodyDetails.put(postAuthorRef + "/" + postsPushID, postBody);
            //postBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody);

            postsRef.updateChildren(postBodyDetails).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "New post created!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Something wrong, we can't create new post", Toast.LENGTH_SHORT).show();
                }
                editTextNewPost.setText("");
            });

        }
    }

    private void setBackground(String selectedBackground) {
        switch (selectedBackground) {

            case "Default":
                imageBackground.setImageResource(R.color.fui_transparent);
                break;

            case "Red Glitch":
                imageBackground.setImageResource(R.drawable.red_glitch);
                topAppBar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.red_glitch, null));

                break;

            case "Black Glaze":
                imageBackground.setImageResource(R.drawable.black_glaze);
                topAppBar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.black_glaze, null));
                break;

            case "Incredible Blue":
                imageBackground.setImageResource(R.drawable.incredible_blue);
                topAppBar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.incredible_blue, null));

                break;

            case "Purple Galaxy":
                imageBackground.setImageResource(R.drawable.purple_galaxy);
                topAppBar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_galaxy, null));

                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (!isLeaving)
        {
            users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).removeEventListener(dataListener);
        }
        postsList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        users.child(Objects.requireNonNull(Objects.requireNonNull(mAuth).getCurrentUser()).getUid()).removeEventListener(dataListener);
    }

}
