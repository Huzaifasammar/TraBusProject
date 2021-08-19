package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SplachScreenActivity extends AppCompatActivity {
  Handler handler;
  FirebaseUser Currentuser;
  FirebaseDatabase database;
  FirebaseAuth firebaseAuth;
  DatabaseReference dbreference;
  String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.splach_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbreference = FirebaseDatabase.getInstance().getReference();
        Currentuser = FirebaseAuth.getInstance().getCurrentUser();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkcurrentuser();
            }
        }, 3000);
    }
        public void checkcurrentuser() {
            if (Currentuser != null) {
                id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                dbreference.child("User").child("Students").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            startActivity(new Intent(SplachScreenActivity.this, Student_Home.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplachScreenActivity.this, Driver_Home.class));
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
              startActivity(new Intent(SplachScreenActivity.this,SignIn.class));
              finish();
            }
        }

    }