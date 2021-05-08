package com.hg39.helleng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
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

    DatabaseReference userRef, requestRef, friendRef, notificationRef;

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

        /*mUserRef = database.getReference("Users").child(userID);
        mThisUserRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid().toString());*/
        notificationRef = database.getReference().child("Notifications");

        /*requestRef = database.getReference().child("Requests");
        friendRef = database.getReference().child("Friends");


        mThisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thisProfileImageUri = snapshot.child("profileImage").getValue(String.class);
                thisFirstNStr = snapshot.child("firstName").getValue(String.class);
                thisLastNStr = snapshot.child("lastName").getValue(String.class);
                thisStatusStr = snapshot.child("status").getValue(String.class);
                thisRegDate = snapshot.child("registerDate").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    profileImageUri = snapshot.child("profileImage").getValue(String.class);
                    firstNStr = snapshot.child("firstName").getValue(String.class);
                    lastNStr = snapshot.child("lastName").getValue(String.class);
                    statusStr = snapshot.child("status").getValue(String.class);
                    regDate = snapshot.child("registerDate").getValue(String.class);
                    webStatus = snapshot.child("webStatus").getValue(String.class);

                    updateUI();

                }
                else
                {
                    Toast.makeText(getContext(),"Data not found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

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

        btnPerform = rootView.findViewById(R.id.btnPerform);
        btnDecline = rootView.findViewById(R.id.btnDecline);

        btnDecline.setOnClickListener(this::onClickDeclineBtn);
        btnPerform.setOnClickListener(this::onClickPerformBtn);
        //checkUserExistence(userID);

        topAppBar.setOverflowIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_settings_24, null));
        topAppBar.setNavigationOnClickListener(v -> ((MainActivity)getContext()).onBackPressed());

        btnPerform.setText("Send Friend Request");
        btnDecline.setVisibility(View.GONE);

        retrieveUserInfo();

        updateUI();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveUserInfo();
        updateUI();
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
        /*final String[] retImage = {"default_image"};

        if (receiverProfileImage != null) {
            retImage[0] = receiverProfileImage;
        }*/

        Intent intent = new Intent(getContext(),DialogActivity.class);

        intent.putExtra("visit_user_id", receiverUserID);
        //intent.putExtra("visit_user_name", receiverUsername);
        //intent.putExtra("visit_user_image", retImage[0]);

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

    //protected void onClickDeclineBtn(View view) {}


    /*private void UnFriend(String userID) {

        if (currentState.equals("friend"))
        {
            friendRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        friendRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "UnFriend", Toast.LENGTH_SHORT).show();
                                currentState = "nothing_happen";
                                btnPerform.setText("Sent Friend Request");
                                btnDecline.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            });
        }

        if (currentState.equals("he_sent_pending"))
        {
            HashMap hashMap = new HashMap();
            hashMap.put("status","decline");
            requestRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    Toast.makeText(getContext(), "Declined", Toast.LENGTH_SHORT).show();
                    currentState = "he_sent_decline";
                    //Сомнительное решение
                    btnPerform.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                }
            });
        }
    }*/

    /*private void checkUserExistence(String userID) {
        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    currentState = "friend";
                    btnPerform.setText("Message");
                    btnDecline.setText("UnFriend");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        friendRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    currentState = "friend";
                    btnPerform.setText("Message");
                    btnDecline.setText("UnFriend");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        requestRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    if (snapshot.child("status").getValue().toString().equals("pending"))
                    {
                        currentState = "I_sent_pending";
                        btnPerform.setText("Cancel Friend Request");
                        btnDecline.setVisibility(View.GONE);
                    }

                    if (snapshot.child("status").getValue().toString().equals("decline"))
                    {
                        currentState = "I_sent_decline";
                        btnPerform.setText("Cancel Friend Request");
                        btnDecline.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    if (snapshot.child("status").getValue().toString().equals("pending"))
                    {
                        currentState = "he_sent_pending";
                        btnPerform.setText("Accept Friend Request");
                        btnDecline.setText("Decline Friend Request");
                        btnDecline.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (currentState.equals("nothing_happen")) {
            currentState = "nothing_happen";
            btnPerform.setText("Sent Friend Request");
            btnDecline.setVisibility(View.GONE);
        }
    }*/

    /*private void PerformAction(String userID) {

        if (currentState.equals("nothing_happen")) {

            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");

            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        /*Toast.makeText(getContext(), "Friend Request sent.", Toast.LENGTH_SHORT).show();

                        currentState = "I_sent_pending";

                        btnDecline.setVisibility(View.GONE);
                        btnPerform.setText("Cancel Friend Request");*/

                        //Notification
                        /*HashMap<String, String> chatNotificationMap = new HashMap<>();
                        chatNotificationMap.put("from", mAuth.getUid());
                        chatNotificationMap.put("type", "request");

                        notificationRef.child(userID).push()
                                .setValue(chatNotificationMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getContext(), "Friend Request sent.", Toast.LENGTH_SHORT).show();

                                            currentState = "I_sent_pending";

                                            btnDecline.setVisibility(View.GONE);
                                            btnPerform.setText("Cancel Friend Request");
                                        }
                                    }
                                });


                    } 
                    else 
                    {
                        Toast.makeText(getContext(), "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        if (currentState.equals("I_sent_pending") || currentState.equals("I_sent_decline"))
        {
            requestRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()) 
                         {
                             Toast.makeText(getContext(), "Friend Request Cancelled", Toast.LENGTH_SHORT).show();
                             currentState = "nothing_happen";

                             btnPerform.setText("Sent Friend Request");
                             btnDecline.setVisibility(View.GONE);

                         }
                         else
                         {
                             Toast.makeText(getContext(), "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                         }
                }
            });
        }
        //! //! //!
        if (currentState.equals("he_sent_pending")) {
            requestRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Хэш мап просматриваемого юзера
                    final HashMap hashMap = new HashMap();
                    hashMap.put("status","friend");
                    hashMap.put("userStatus", statusStr);
                    hashMap.put("username", firstNStr + " " + lastNStr);
                    hashMap.put("profileImageUri", profileImageUri);

                    friendRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            //Хэш мап юзера текущего аккаунта
                            final HashMap thisHashMap = new HashMap();
                            thisHashMap.put("status","friend");
                            thisHashMap.put("userStatus", thisStatusStr);
                            thisHashMap.put("username", thisFirstNStr + " " + thisLastNStr);
                            thisHashMap.put("profileImageUri", thisProfileImageUri);

                            if (task.isSuccessful())
                            {
                                friendRef.child(userID).child(mUser.getUid()).updateChildren(thisHashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        Toast.makeText(getContext(), "Friend Added", Toast.LENGTH_SHORT).show();
                                        currentState = "friend";
                                        btnPerform.setText("Message");
                                        btnDecline.setText("UnFriend");
                                        btnDecline.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        if (currentState.equals("friend")) {

            final String[] retImage = {"default_image"};

            if (profileImageUri != null) {
                retImage[0] = profileImageUri;
            }

            Intent intent = new Intent(getContext(),DialogActivity.class);

            intent.putExtra("visit_user_id", userID);
            intent.putExtra("visit_user_name", firstNStr + " " + lastNStr);
            intent.putExtra("visit_user_image", retImage[0]);

            startActivity(intent);

        }

    }*/

    private void updateUI() {

        textViewWebStatus.setText(receiverWebStatus);
        textViewReg.setText("Registered since " + receiverRegDate);
        textViewFullName.setText(receiverUsername);//firstNStr + " " + lastNStr);
        textViewUserStatus.setText(receiverStatus);
        textViewUserId.setText("Id: " + receiverUserID);//Warning!

        if (receiverProfileImage != null) {
            Picasso.get().load(receiverProfileImage).placeholder(R.drawable.no_avatar).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.no_avatar);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
