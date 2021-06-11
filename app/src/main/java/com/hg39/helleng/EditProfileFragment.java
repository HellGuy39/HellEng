package com.hg39.helleng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import id.zelory.compressor.Compressor;

public class EditProfileFragment extends Fragment {

    EditText editTextFirstName,editTextLastName,editTextUserStatus,editTextCity,editTextAboutMe;
    TextView tvBirthday;
    ImageView profileImage;

    AutoCompleteTextView backgroundDropdown;

    String profileImageUri;

    Button btnDeleteProfileImage, btnChangeBirthday;

    com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnSave;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    StorageReference storageReference;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    String firstNStr,lastNStr,statusStr;
    String birthday;
    String city, aboutMe;
    String selectedBackground;

    ImageView imageBackgroundExample;

    ArrayAdapter<String> adapterBackgrounds;

    ProgressDialog loadingBar;

    androidx.coordinatorlayout.widget.CoordinatorLayout root;

    /*String[] backgrounds =
            {"Default",
                    "Perfect Blue", "Natural Green", "Holo Red", "Orange Lollipop", "Mysterious Violet",
                    "Jet Black", "Space Gray", "Abstract Gradient",
                    "Japanese Sunset", "Japanese Sunrise"};*/
    String[] backgrounds = {"Default", "Red Glitch", "Black Glaze", "Incredible Blue", "Purple Galaxy"};

    ValueEventListener loadData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        storageReference = FirebaseStorage.getInstance().getReference();

        loadData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                firstNStr = dataSnapshot.child("firstName").getValue(String.class);
                lastNStr = dataSnapshot.child("lastName").getValue(String.class);
                statusStr = dataSnapshot.child("status").getValue(String.class);

                profileImageUri = dataSnapshot.child("profileImage").getValue(String.class);

                if (dataSnapshot.hasChild("birthday")) {
                    birthday = dataSnapshot.child("birthday").getValue(String.class);
                }

                if (dataSnapshot.hasChild("city")) {
                    city = dataSnapshot.child("city").getValue(String.class);
                }

                if (dataSnapshot.hasChild("aboutMe")) {
                    aboutMe = dataSnapshot.child("aboutMe").getValue(String.class);
                }

                if (dataSnapshot.hasChild("background")) {
                    selectedBackground = dataSnapshot.child("background").getValue(String.class);
                    setExampleImage(Objects.requireNonNull(selectedBackground));
                }

                updateUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_edit_profile,container,false);

        root = rootView.findViewById(R.id.root);

        profileImage = rootView.findViewById(R.id.profileImage);
        imageBackgroundExample = rootView.findViewById(R.id.imageBackgroundExample);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) requireContext()).onBackPressed());

        backgroundDropdown = rootView.findViewById(R.id.backgroundDropdown);
        adapterBackgrounds = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, backgrounds);

        backgroundDropdown.setOnItemClickListener((parent, view, position, id) -> {
            selectedBackground = (String)parent.getItemAtPosition(position);
            setExampleImage(selectedBackground);
        });

        loadingBar = new ProgressDialog(getContext());

        btnDeleteProfileImage = rootView.findViewById(R.id.btnDeleteProfileImage);

        fltBtnSave = rootView.findViewById(R.id.floatingButtonSave);
        btnChangeBirthday = rootView.findViewById(R.id.btnChangeBirthday);

        btnChangeBirthday.setOnClickListener(this::onClickChangeBirthday);
        btnDeleteProfileImage.setOnClickListener(this::onClickDeleteProfileImage);
        fltBtnSave.setOnClickListener(this::onClickBtnSave);
        profileImage.setOnClickListener(this::onClickChangeProfileImage);

        editTextFirstName = rootView.findViewById(R.id.editTextFirstName);
        editTextLastName = rootView.findViewById(R.id.editTextLastName);
        editTextUserStatus = rootView.findViewById(R.id.editTextUserStatus);

        editTextAboutMe = rootView.findViewById(R.id.etAboutMe);
        editTextCity = rootView.findViewById(R.id.etCity);

        tvBirthday = rootView.findViewById(R.id.textViewDate);

        editTextFirstName.setText(firstNStr);
        editTextLastName.setText(lastNStr);
        editTextUserStatus.setText(statusStr);

        //updateUI();

        users.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(loadData);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        users.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).removeEventListener(loadData);
    }

    private void setExampleImage(String selectedBackground) {
        switch (selectedBackground) {

            case "Default":
                imageBackgroundExample.setImageResource(R.color.fui_transparent);
                break;

            case "Red Glitch":
                imageBackgroundExample.setImageResource(R.drawable.red_glitch);
                break;

            case "Black Glaze":
                imageBackgroundExample.setImageResource(R.drawable.black_glaze);
                break;

            case "Incredible Blue":
                imageBackgroundExample.setImageResource(R.drawable.incredible_blue);
                break;

            case "Purple Galaxy":
                imageBackgroundExample.setImageResource(R.drawable.purple_galaxy);
                break;

        }
    }

    private void onClickDeleteProfileImage(View view) {
        if (!((MainActivity) requireContext()).isOnline())
        {
            dialogBuilder();
            return;
        }

        loadingBar.setTitle("Updating data...");
        loadingBar.setMessage("Please wait, while we update your account information...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        final StorageReference fileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
        fileRef.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                users.child(Objects.requireNonNull(mAuth.getCurrentUser().getUid())).child("profileImage").removeValue().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful())
                    {
                        Toast.makeText(getContext(), "Image successful deleted.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else
                    {

                        Toast.makeText(getContext(), "Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        dialogBuilder();
                    }
                }).addOnCanceledListener(() -> {

                    Toast.makeText(getContext(), "Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    dialogBuilder();
                }).addOnFailureListener(e -> {

                    Toast.makeText(getContext(), "Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    dialogBuilder();
                });
            }
            else
            {
                Toast.makeText(getContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnCanceledListener(() -> {
            Toast.makeText(getContext(),"Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
            dialogBuilder();
        }).addOnFailureListener(e -> {
            //Toast.makeText(getContext(),"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        adapterBackgrounds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backgroundDropdown.setAdapter(adapterBackgrounds);

        loadData();
        //updateUI();
    }

    private void loadData() {

    }

    private void updateUI() {
        editTextFirstName.setText(firstNStr);
        editTextLastName.setText(lastNStr);
        editTextUserStatus.setText(statusStr);

        if (aboutMe != null)
            editTextAboutMe.setText(aboutMe);

        if (city != null)
            editTextCity.setText(city);

        if (birthday != null)
            tvBirthday.setText("Birthday: " + birthday);
        else
            tvBirthday.setText("Birthday: " + "not selected");

        if (profileImageUri != null) {
            Picasso.get().load(profileImageUri).placeholder(R.drawable.no_avatar).into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.no_avatar);
        }

        if (selectedBackground != null) {
            switch (selectedBackground)
            {
                case "Red Glitch":
                    backgroundDropdown.setText(adapterBackgrounds.getItem(1), false);
                    break;

                case "Black Glaze":
                    backgroundDropdown.setText(adapterBackgrounds.getItem(2), false);
                    break;

                case "Incredible Blue":
                    backgroundDropdown.setText(adapterBackgrounds.getItem(3), false);
                    break;

                case "Purple Galaxy":
                    backgroundDropdown.setText(adapterBackgrounds.getItem(4), false);
                    break;

                default:
                    break;
            }

        } else {
            backgroundDropdown.setText(adapterBackgrounds.getItem(0), false);
        }

    }

    protected void onClickChangeBirthday(View view) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();

        datePicker.show(requireActivity().getSupportFragmentManager(), "tag");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Date date = new Date(selection);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            birthday = dateFormat.format(date);
            updateUI();
        });

        datePicker.addOnNegativeButtonClickListener(v -> {

        });

    }

    protected void onClickChangeProfileImage(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent,1000);
        profileImage.setClickable(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        profileImage.setClickable(true);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(requireContext(), this);

            //uploadImageToFirebase(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK)
            {
                assert result != null;
                Uri resultUri = result.getUri();
                uploadImageToFirebase(resultUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //upload image to firebase storage
        if (!((MainActivity) requireContext()).isOnline()) {
            dialogBuilder();
            return;
        }

        loadingBar.setTitle("Updating data...");
        loadingBar.setMessage("Updating account data, please wait...");
        loadingBar.setCanceledOnTouchOutside(true);//!
        loadingBar.show();

        final StorageReference fileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {

                Picasso.get().load(uri).into(profileImage);
                HashMap hashMap = new HashMap();
                hashMap.put("profileImage",uri.toString());

                users.child(mAuth.getUid()).updateChildren(hashMap).addOnSuccessListener(o -> {
                    Snackbar.make(root,"Image Uploaded\nYou don't need to press save button repeatedly", Snackbar.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    dialogBuilder();

                }).addOnCanceledListener(() -> {
                    Toast.makeText(getContext(),"Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    dialogBuilder();
                });
            });
        }).addOnFailureListener(e -> {
            loadingBar.dismiss();
            Toast.makeText(getContext(),"Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
            dialogBuilder();
        }).addOnCanceledListener(() -> {
            loadingBar.dismiss();
            Toast.makeText(getContext(),"Failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
            dialogBuilder();
        });
    }

    private void dialogBuilder() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Failed")
                .setMessage("Please check your internet connection")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                }).create().show();
    }

    protected void onClickBtnSave(View view) {

        String strStatus = editTextUserStatus.getText().toString().trim();
        String strFName = editTextFirstName.getText().toString().trim();
        String strLName = editTextLastName.getText().toString().trim();

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

        if (!((MainActivity) requireContext()).isOnline()) {
            dialogBuilder();
            return;
        }

        if (!((MainActivity) requireContext()).isOnline()) {
            dialogBuilder();
            return;
        }

        User user = new User();
        user.setFirstName(editTextFirstName.getText().toString());
        user.setLastName(editTextLastName.getText().toString());
        user.setStatus(editTextUserStatus.getText().toString());
        user.setAboutMe(editTextAboutMe.getText().toString());
        user.setBirthday(birthday);
        user.setCity(editTextCity.getText().toString());
        user.setBackground(selectedBackground);

        users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("fullName").setValue(user.getFirstName() + " " +user.getLastName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("firstName").setValue(user.getFirstName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lastName").setValue(user.getLastName());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue(user.getStatus());
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("birthday").setValue(user.getBirthday());

        if (!user.getCity().equals("")) {
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("city").setValue(user.getCity());
        } else {
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("city").removeValue();
        }

        if (!user.getAboutMe().equals("")) {
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("aboutMe").setValue(user.getAboutMe());
        } else {
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("aboutMe").removeValue();
        }

        if (!user.getBackground().equals("Default") || !user.getBackground().equals("")) {
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("background").setValue(user.getBackground());
        } else {
            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("background").removeValue();
        }

        Toast.makeText(getContext(),
                "What a save!",
                Toast.LENGTH_SHORT).show();

        ((MainActivity) requireContext())
                .onBackPressed();
                //.outEditProfileFragment();
    }
}
