package com.hg39.helleng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hg39.helleng.Models.User;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {

    EditText editTextFirstName,editTextLastName,editTextUserStatus;
    ImageView profileImage;

    com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnSave;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    StorageReference storageReference;
    StorageReference profileRef;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    User user = new User();
    String firstNStr,lastNStr,statusStr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        storageReference = FirebaseStorage.getInstance().getReference();

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        FirebaseUser userF = mAuth.getCurrentUser();

        users.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                firstNStr = dataSnapshot.child("firstName").getValue(String.class);
                lastNStr = dataSnapshot.child("lastName").getValue(String.class);
                statusStr = dataSnapshot.child("status").getValue(String.class);

                updateUI();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(),"Error" + error.getMessage(),Toast.LENGTH_SHORT).show();
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView =
                inflater.inflate(R.layout.fragment_edit_profile,container,false);

        profileImage = rootView.findViewById(R.id.profileImage);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity())
                        .outEditProfileFragment();
            }
        });

        fltBtnSave = rootView.findViewById(R.id.floatingButtonSave);

        fltBtnSave.setOnClickListener(this::onClickBtnSave);
        profileImage.setOnClickListener(this::onClickChangeProfileImage);

        editTextFirstName = rootView.findViewById(R.id.editTextFirstName);
        editTextLastName = rootView.findViewById(R.id.editTextLastName);
        editTextUserStatus = rootView.findViewById(R.id.editTextUserStatus);

        editTextFirstName.setText(firstNStr);
        editTextLastName.setText(lastNStr);
        editTextUserStatus.setText(statusStr);
        //updateUI();

        //editTextFirstName.setText(getArguments().getString("userFName"));
        //editTextLastName.setText(getArguments().getString("userLName"));
        //editTextUserStatus.setText(getArguments().getString("userStatus"));

        updateUI();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUI();
    }

    private void updateUI() {
        editTextFirstName.setText(firstNStr);
        editTextLastName.setText(lastNStr);
        editTextUserStatus.setText(statusStr);

        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() +"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

    }

    protected void onClickChangeProfileImage(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent,1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(),"Image Uploaded.", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onClickBtnSave(View view) {

        String strStatus = editTextUserStatus.getText().toString();
        String strFName = editTextFirstName.getText().toString();
        String strLName = editTextLastName.getText().toString();

        if (strFName.length() > 32) {
            Toast.makeText(getActivity(),"First name must be no more than 32 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if (strLName.length() > 32) {
            Toast.makeText(getActivity(),"Last name must be no more than 32 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if (strStatus.length() > 64) {
            Toast.makeText(getActivity(),"Status must be no more than 64 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        //((MainActivity)getActivity())
        //           .saveProfileInfo(strStatus,strFName,strLName);

        User user = new User();
        user.setFirstName(editTextFirstName.getText().toString());
        user.setLastName(editTextLastName.getText().toString());
        user.setStatus(editTextUserStatus.getText().toString());

        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("firstName").setValue(user.getFirstName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lastName").setValue(user.getLastName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue(user.getStatus());

        Toast.makeText(getActivity(),
                "What a save!",
                Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity())
                .outEditProfileFragment();


    }

}
