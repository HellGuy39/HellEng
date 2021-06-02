package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hg39.helleng.Models.Post;
import com.hg39.helleng.Models.PostAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewOtherProfileFragment extends Fragment {

    TextView textViewUserStatus,textViewReg,textViewUserId;
    TextView textViewFullName,textViewWebStatus;
    CircleImageView profileImageView;
    com.google.android.material.appbar.MaterialToolbar topAppBar;

    Button btnPerform,btnDecline;

    //FirebaseUser mUser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    //DatabaseReference mUserRef;
    //DatabaseReference mThisUserRef;
    //DatabaseReference notificationRef;
    StorageReference storageReference;
    StorageReference profileRef;
    //DatabaseReference requestRef,friendRef;

    DatabaseReference userRef, requestRef, friendRef, notificationRef,  postsRef;

    //String firstNStr,lastNStr,statusStr,regDate;
    //String profileImageUri;
    String currentState;

    //String thisFirstNStr,thisLastNStr,thisStatusStr,thisRegDate;
    //String thisProfileImageUri;
    //String webStatus;

    String userID;
    String receiverUserID, senderUserID;

    String receiverUsername, receiverStatus, receiverRegDate,
            receiverProfileImage, receiverWebStatus;


    TextView textViewBirthday;
    TextView textViewCity;
    TextView textViewAboutMe;

    String selectedBackground;
    String postAuthorID;

    String birthday, city, aboutMe;

    ImageView imageBackground;

    Button btnCreatePost,btnAttachments;
    EditText editTextNewPost;
    RecyclerView recyclerViewMicroBlog;
    LinearLayoutManager linearLayoutManager;
    PostAdapter postAdapter;

    ValueEventListener dataListener;

    final List<Post> postsList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentState = "new";

        userID = getArguments().getString("userKey","Nope");

        //Muhamed Ali power
        receiverUserID = getArguments().getString("userKey","Nope");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        requestRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        friendRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();

        senderUserID = mAuth.getCurrentUser().getUid();

        storageReference = FirebaseStorage.getInstance().getReference();

        profileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() +"/profile.jpg");

        database = FirebaseDatabase.getInstance();

        notificationRef = database.getReference().child("Notifications");

        postAuthorID = userID;//Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        postsRef = FirebaseDatabase.getInstance().getReference();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_view_other_profile,container,false);

        topAppBar = rootView.findViewById(R.id.topAppBar);
        textViewUserId = rootView.findViewById(R.id.textViewUserId);
        textViewReg = rootView.findViewById(R.id.textViewReg);
        textViewFullName = rootView.findViewById(R.id.textViewFullName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);
        profileImageView = rootView.findViewById(R.id.profileImage);
        textViewWebStatus = rootView.findViewById(R.id.textViewWebStatus);

        imageBackground = rootView.findViewById(R.id.imageBackground);

        textViewBirthday = rootView.findViewById(R.id.textViewBirthday);
        textViewCity = rootView.findViewById(R.id.textViewCity);
        textViewAboutMe = rootView.findViewById(R.id.textViewAboutMe);

        recyclerViewMicroBlog = rootView.findViewById(R.id.recyclerViewMicroBlog);
        postAdapter = new PostAdapter(postsList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMicroBlog.setLayoutManager(linearLayoutManager);
        recyclerViewMicroBlog.setAdapter(postAdapter);

        btnPerform = rootView.findViewById(R.id.btnPerform);
        btnDecline = rootView.findViewById(R.id.btnDecline);

        btnDecline.setOnClickListener(this::onClickDeclineBtn);
        btnPerform.setOnClickListener(this::onClickPerformBtn);
        //checkUserExistence(userID);

        topAppBar.setOverflowIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_settings_24, null));
        topAppBar.setNavigationOnClickListener(v -> ((MainActivity)getContext()).onBackPressed());

        btnPerform.setText("Send Friend Request");
        btnDecline.setVisibility(View.GONE);

        //retrieveUserInfo();

        //updateUI();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveUserInfo();
        //updateUI();
    }

    private void retrieveUserInfo() {
        userRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                receiverUsername = snapshot.child("fullName").getValue(String.class);
                receiverStatus = snapshot.child("status").getValue(String.class);
                receiverProfileImage = snapshot.child("profileImage").getValue(String.class);
                receiverRegDate = snapshot.child("registerDate").getValue(String.class);
                receiverWebStatus = snapshot.child("webStatus").getValue(String.class);

                birthday = snapshot.child("birthday").getValue(String.class);
                city = snapshot.child("city").getValue(String.class);
                aboutMe = snapshot.child("aboutMe").getValue(String.class);
                selectedBackground = snapshot.child("background").getValue(String.class);

                updateUI();
                manageChatRequests();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void manageChatRequests() {
        requestRef.child(senderUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(receiverUserID))
                {
                    String requestType = snapshot.child(receiverUserID).child("requestType").getValue(String.class);

                    if (requestType.equals("sent"))
                    {
                        btnPerform.setText("Cancel Friend Request");
                        btnDecline.setVisibility(View.GONE);
                        currentState = "request_sent";
                    }
                    else if (requestType.equals("received"))
                    {
                        currentState = "request_received";
                        btnPerform.setText("Accept Friend Request");
                        btnDecline.setVisibility(View.VISIBLE);
                        btnDecline.setText("Cancel Friend Request");

                    }

                    /*else
                    {
                        currentState = "new";
                        btnPerform.setText("Send Friend Request");
                        btnDecline.setVisibility(View.GONE);
                    }*/

                }
                else
                {
                    friendRef.child(senderUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(receiverUserID)) {
                                currentState = "friends";
                                btnPerform.setText("UnFriend");

                                btnDecline.setVisibility(View.VISIBLE);
                                btnDecline.setText("Message");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        postsList.clear();
    }

    protected void onClickDeclineBtn(View view) {

        if (currentState.equals("new")) {
            //sendChatRequest();
        }

        if (currentState.equals("request_sent")) {
            //cancelChatRequest();
        }

        if (currentState.equals("request_received")) {
            cancelChatRequest();
        }

        if (currentState.equals("friends")) {
            //removeSpecificFriend();
            message();
        }

    }

    protected void message() {
        Intent intent = new Intent(getContext(),DialogActivity.class);

        intent.putExtra("visit_user_id", receiverUserID);

        startActivity(intent);
    }

    protected void onClickPerformBtn(View view) {

        if (currentState.equals("new")) {
            sendChatRequest();
        }

        if (currentState.equals("request_sent")) {
            cancelChatRequest();
        }

        if (currentState.equals("request_received")) {
            acceptChatRequest();
        }

        if (currentState.equals("friends")) {
            removeSpecificFriend();
        }

    }

    private void removeSpecificFriend() {
        friendRef.child(senderUserID).child(receiverUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            friendRef.child(receiverUserID).child(senderUserID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                btnPerform.setText("Send Friend Request");
                                                currentState = "new";

                                                btnDecline.setVisibility(View.GONE);

                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void acceptChatRequest() {
        friendRef.child(senderUserID).child(receiverUserID)
                .child("Friends").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override  //status //friend
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    friendRef.child(receiverUserID).child(senderUserID)
                            .child("Friends").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                requestRef.child(senderUserID).child(receiverUserID)
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            requestRef.child(receiverUserID).child(senderUserID)
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        currentState = "friends";
                                                        btnPerform.setText("UnFriend");

                                                        btnDecline.setVisibility(View.VISIBLE);
                                                        btnDecline.setText("Message");
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

    private void cancelChatRequest() {
        requestRef.child(senderUserID).child(receiverUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                        requestRef.child(receiverUserID).child(senderUserID)
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            btnPerform.setText("Send Friend Request");
                                            currentState = "new";

                                            btnDecline.setVisibility(View.GONE);

                                        }
                                    }
                                });
                        }
                    }
                });
    }

    private void sendChatRequest() {
        requestRef.child(senderUserID).child(receiverUserID)
                .child("requestType").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    requestRef.child(receiverUserID).child(senderUserID)
                            .child("requestType").setValue("received")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                HashMap<String, String> chatNotificationMap = new HashMap<>();
                                chatNotificationMap.put("from", senderUserID);
                                chatNotificationMap.put("type", "request");

                                notificationRef.child(receiverUserID).push()
                                        .setValue(chatNotificationMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                     if (task.isSuccessful())
                                                     {
                                                         btnPerform.setText("Cancel Friend Request");
                                                         btnDecline.setVisibility(View.GONE);
                                                         currentState = "request_sent";
                                                     }
                                            }
                                        });

                                //!!!
                                //btnPerform.setEnabled(true);
                                btnPerform.setText("Cancel Friend Request");
                                btnDecline.setVisibility(View.GONE);
                                currentState = "request_sent";
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateUI() {

        textViewWebStatus.setText(receiverWebStatus);
        textViewReg.setText("Registered since " + receiverRegDate);
        textViewFullName.setText(receiverUsername);//firstNStr + " " + lastNStr);
        textViewUserStatus.setText(receiverStatus);
        textViewUserId.setText("Id: " + receiverUserID);//Warning!

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

        if (receiverProfileImage != null) {
            Picasso.get().load(receiverProfileImage).placeholder(R.drawable.no_avatar).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.no_avatar);
        }

        postsRef.child("Users").child(postAuthorID).child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                postsList.add(post);
                postAdapter.notifyDataSetChanged();

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
    public void onResume() {
        super.onResume();
    }

}
