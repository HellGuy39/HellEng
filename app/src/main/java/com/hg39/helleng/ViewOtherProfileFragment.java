package com.hg39.helleng;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import com.hg39.helleng.Models.User;
import com.squareup.picasso.Picasso;

import java.util.Currency;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewOtherProfileFragment extends Fragment {

    Button btnEdit,btnSignOut;
    TextView textViewUserStatus,textViewReg,textViewTestsStarted,textViewTestsFullCompleted,textViewUserId;
    TextView textViewFullName;
    CircleImageView profileImage;
    com.google.android.material.appbar.MaterialToolbar topAppBar;

    Button btnPerform,btnDecline;

    FirebaseUser mUser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mUserRef;
    DatabaseReference mThisUserRef;
    StorageReference storageReference;
    StorageReference profileRef;
    DatabaseReference requestRef,friendRef;

    int testsStarted,testsFullCompleted;
    String firstNStr,lastNStr,statusStr,regDate;//userId;
    String profileImageUri;
    String currentState = "nothing_happen";

    String thisFirstNStr,thisLastNStr,thisStatusStr,thisRegDate;
    String thisProfileImageUri;

    User user = new User();

    String userID;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = getArguments().getString("userKey","Nope");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");

        database = FirebaseDatabase.getInstance();

        mUserRef = database.getReference("Users").child(userID);
        mThisUserRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid().toString());

        requestRef = database.getReference().child("Requests");
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
        });

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
        textViewTestsFullCompleted = rootView.findViewById(R.id.textViewTestFullCompleted);
        textViewTestsStarted = rootView.findViewById(R.id.textViewTestStarted);
        textViewFullName = rootView.findViewById(R.id.textViewFullName);
        textViewUserStatus = rootView.findViewById(R.id.textViewUserStatus);
        profileImage = rootView.findViewById(R.id.profileImage);

        btnPerform = rootView.findViewById(R.id.btnPerform);
        btnDecline = rootView.findViewById(R.id.btnDecline);

        btnDecline.setOnClickListener(v -> UnFriend(userID));
        btnPerform.setOnClickListener(v -> PerformAction(userID));
        checkUserExistence(userID);

        topAppBar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_settings_24));
        topAppBar.setNavigationOnClickListener(v -> ((MainActivity)getContext()).setFragFindFriends());

        updateUI();

        return rootView;
    }

    private void UnFriend(String userID) {

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
    }

    private void checkUserExistence(String userID) {
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
    }

    private void PerformAction(String userID) {

        if (currentState.equals("nothing_happen")) {

            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");

            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "Friend Request sent.", Toast.LENGTH_SHORT).show();

                        currentState = "I_sent_pending";

                        btnDecline.setVisibility(View.GONE);
                        btnPerform.setText("Cancel Friend Request");
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
            //
        }

    }

    private void updateUI() {

        textViewReg.setText("Registered since " + regDate);
        textViewTestsStarted.setText("Tests started: " + testsStarted);
        textViewTestsFullCompleted.setText("Tests completed 100%: " + testsFullCompleted);
        textViewFullName.setText(firstNStr + " " + lastNStr);
        textViewUserStatus.setText(statusStr);
        textViewUserId.setText("Id: " + userID);//Warning!

        Picasso.get().load(profileImageUri).into(profileImage);

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

    }