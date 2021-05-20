package com.hg39.helleng;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.MessageAdapter;
import com.hg39.helleng.Models.Messages;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hg39.helleng.SettingsActivity.CONFIG_FILE;
import static com.hg39.helleng.SettingsActivity.CONFIG_LANGUAGE;

public class DialogActivity extends AppCompatActivity {

    //com.google.android.material.appbar.MaterialToolbar toolbar;

    String messageReceiverID, messageReceiverName, messageReceiverImage, messageSenderID,webStatus;

    Toolbar chatToolBar;

    EditText messageInputText;
    ImageButton btnSendMessage;

    FirebaseAuth mAuth;
    DatabaseReference rootRef;

    TextView tvUsername,tvWebStatus;
    CircleImageView userImage;

    final List<Messages> messagesList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    MessageAdapter messageAdapter;
    RecyclerView userMessagesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_dialog);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        messageSenderID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        btnSendMessage = findViewById(R.id.btnSendMessage);
        messageInputText = findViewById(R.id.editTextNewMessage);

        messageReceiverID = getIntent().getExtras().get("visit_user_id").toString();
        //messageReceiverName = getIntent().getExtras().get("visit_user_name").toString();
        //messageReceiverImage = getIntent().getExtras().get("visit_user_image").toString();

        initializationActionBar();

        rootRef.child("Users").child(messageReceiverID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                webStatus = snapshot.child("webStatus").getValue(String.class);
                messageReceiverName = snapshot.child("fullName").getValue(String.class);

                if (snapshot.hasChild("profileImage")) {
                    messageReceiverImage = snapshot.child("profileImage").getValue(String.class);
                    Picasso.get().load(messageReceiverImage).into(userImage);
                } else {
                    messageReceiverImage = "default_image";
                }

                tvUsername.setText(messageReceiverName);
                tvWebStatus.setText(webStatus);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*tvUsername.setText(messageReceiverName);
        tvWebStatus.setText(webStatus);
        if (!messageReceiverImage.equals("default_image")) {
            Picasso.get().load(messageReceiverImage).into(userImage);
        }*/

        //chatToolBar = findViewById(R.id.topAppBar);
        chatToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSendMessage.setOnClickListener(v -> sendMessage());

        rootRef.child("Messages").child(messageSenderID).child(messageReceiverID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Messages messages = snapshot.getValue(Messages.class);
                        messagesList.add(messages);
                        messageAdapter.notifyDataSetChanged();

                        userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void sendMessage() {
        String messageText = messageInputText.getText().toString();

        if (TextUtils.isEmpty(messageText))
        {
            //
        }
        else
        {
            String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID;
            String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderID).child(messageReceiverID).push();

            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody);

            rootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        //Toast.makeText(DialogActivity.this, "Yep!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(DialogActivity.this, "Something wrong, we haven't sent a message", Toast.LENGTH_SHORT).show();
                    }
                    messageInputText.setText("");
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initializationActionBar() {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_top_chat_bar, null);

        chatToolBar = findViewById(R.id.chat_bar);
        setSupportActionBar(chatToolBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setCustomView(actionBarView);

        userImage = findViewById(R.id.custom_profileImage);
        tvUsername = findViewById(R.id.custom_username);
        tvWebStatus = findViewById(R.id.custom_web_status);

        messageAdapter = new MessageAdapter(messagesList);
        userMessagesList = findViewById(R.id.recyclerViewPrivateDialog);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

    }

    protected void setLanguage() {
        SharedPreferences sp = getSharedPreferences(CONFIG_FILE, 0);
        String language = sp.getString(CONFIG_LANGUAGE, "en");

        Locale locale = new Locale(language);

        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }

}