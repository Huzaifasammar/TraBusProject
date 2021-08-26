package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.adapter.NotificationAdapter;
import com.example.trabus.models.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NotificationAdapter adapter;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseUser Currentuser;
    DatabaseReference referencecheck;
    ImageView imageView;
    ArrayList<NotificationModel>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_notification);
        recyclerView = findViewById(R.id.ntificationrecycler);
        adapter = new NotificationAdapter(list, NotificationActivity.this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(NotificationActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        reference = FirebaseDatabase.getInstance().getReference();
        referencecheck = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        imageView=findViewById(R.id.left_arrow_notify);
        Currentuser = FirebaseAuth.getInstance().getCurrentUser();
        reference.child("User").child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        NotificationModel Nm = ds.getValue(NotificationModel.class);
                        assert Nm != null;
                        list.add(Nm);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcurrentuser();
            }
        });
    }

        public void checkcurrentuser() {
            if (Currentuser != null) {
                referencecheck.child("User").child("Students").child("Profiles").child(Currentuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            startActivity(new Intent(NotificationActivity.this, Student_Home.class));
                            finish();
                        } else {
                            startActivity(new Intent(NotificationActivity.this, Driver_Home.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
            else
            {

            }
        }

    }