package com.hg39.helleng;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hg39.helleng.Models.CustomTest;
import com.hg39.helleng.Models.User;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hg39.helleng.MainActivity.futureSimple;
import static com.hg39.helleng.MainActivity.groupGrammar;
import static com.hg39.helleng.MainActivity.pastSimple;
import static com.hg39.helleng.MainActivity.presentSimple;
import static com.hg39.helleng.TestMakerActivity.ACTION_EDIT_TEST;

public class CreateTestSelectedFragment extends Fragment implements View.OnClickListener{

    //TextView tvCompletedPrSimple,tvCompletedPsSimple,tvCompletedFtSimple;
    //TextView tvPrSimple,tvPsSimple,tvRFtSimple;
    //String completedPrSimple,completedPsSimple,completedFrSimple;
    //Intent intentTenses;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersTestsProgress, userTestsIDRef, userTestsStorageRef;

    com.google.android.material.card.MaterialCardView cardCreateTest, cardOtherTest;

    RecyclerView recyclerView;

    Intent intent;

    FirebaseRecyclerOptions<CustomTest> options;
    FirebaseRecyclerAdapter<CustomTest, CustomTestViewHolder> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //intentTenses = new Intent(getContext(), GrammarActivity.class);
        intent = new Intent(getContext(), TestMakerActivity.class);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        usersTestsProgress = database.getReference("Users Tests Progress");
        userTestsIDRef = database.getReference("User Tests ID").child(mAuth.getCurrentUser().getUid());
        userTestsStorageRef = database.getReference("User Tests Storage");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.fragment_create_test_selected,container,false);

        toolbar = rootView.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity)getContext()).onBackPressed());

        rootView.findViewById(R.id.btnCreate).setOnClickListener(this);
        rootView.findViewById(R.id.btnFind).setOnClickListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadUserTests();

        return rootView;
    }

    private void loadUserTests() {

        options = new FirebaseRecyclerOptions.Builder<CustomTest>()
                        .setQuery(userTestsIDRef, CustomTest.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CustomTest, CustomTestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CustomTestViewHolder holder, int position, @NonNull CustomTest model) {

                //final String currentTestID = getRef(position).child("").getRef();

                final String list_user_ID = getRef(position).getKey();

                holder.tvTestID.setText("ID: " + list_user_ID);

                userTestsStorageRef.child(list_user_ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String date = snapshot.child("dateОfCreation").getValue(String.class);
                        final String testName = snapshot.child("testName").getValue(String.class);

                        holder.tvDate.setText(date);
                        holder.tvTestName.setText(testName);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "Not avalible in BETA" , Toast.LENGTH_SHORT).show();
                        intent.putExtra("testID", list_user_ID);
                        intent.putExtra("Action", ACTION_EDIT_TEST);
                        startActivity(intent);
                    }
                });

                holder.btnStat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Not avalible in BETA" , Toast.LENGTH_SHORT).show();
                    }
                });

                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to permanently delete this test?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        userTestsStorageRef.child(list_user_ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    userTestsIDRef.child(list_user_ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getContext(), "Test Suссsefull Deleted" , Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }).create().show();
                    }
                });

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("testID", list_user_ID);
                        intent.putExtra("Action", "TestPreview");
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CustomTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_user_test, parent, false);

                return new CustomTestViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUI();

    }

    private void updateUI(){

        /*if (completedFrSimple == null) {
            completedFrSimple = "0";
        }

        if (completedPsSimple == null) {
            completedPsSimple = "0";
        }

        if (completedPrSimple == null) {
            completedPrSimple = "0";
        }

        tvCompletedPrSimple.setText(completedPrSimple + " %");
        tvCompletedPsSimple.setText(completedPsSimple + " %");
        tvCompletedFtSimple.setText(completedFrSimple + " %");*/

    }

    public static class CustomTestViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName, tvDate, tvTestID;
        Button btnDelete, btnEdit, btnStat;
        com.google.android.material.card.MaterialCardView cardView;

        public CustomTestViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            tvTestID = itemView.findViewById(R.id.tvTestID);
            tvTestName = itemView.findViewById(R.id.tvTestName);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnStat = itemView.findViewById(R.id.btnStat);

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCreate)
        {
            //Toast.makeText(getContext(), "Create Test!", Toast.LENGTH_SHORT).show();
            intent.putExtra("Action", "CreateTest");
            startActivity(intent);
        }
        else if (v.getId() == R.id.btnFind)
        {
            intent.putExtra("Action", "ViewOtherTest");
            startActivity(intent);
            //Toast.makeText(getContext(), "Other Test!", Toast.LENGTH_SHORT).show();
        }
    }
}
