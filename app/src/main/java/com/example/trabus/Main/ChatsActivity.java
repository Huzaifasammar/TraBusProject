package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

public class ChatsActivity extends AppCompatActivity {
    ImageView leftarrow;
    FirebaseUser Currentuser;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbreference;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_chats);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbreference = FirebaseDatabase.getInstance().getReference();
        Currentuser = FirebaseAuth.getInstance().getCurrentUser();
        leftarrow=findViewById(R.id.back_arrow_Chat);
        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcurrentuser();
            }
        });
    }


    public void checkcurrentuser() {
        if (Currentuser != null) {
            id = firebaseAuth.getCurrentUser().getUid();
            dbreference.child("User").child("Students").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        startActivity(new Intent(ChatsActivity.this, Student_Home.class));
                    } else {
                        startActivity(new Intent(ChatsActivity.this, Driver_Home.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        else
        {
            startActivity(new Intent(ChatsActivity.this, SignIn.class));
            finish();
        }
    }
}