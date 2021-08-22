package com.example.trabus.Driver_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trabus.R;
import com.example.trabus.adapter.ChatDetailAdapter;
import com.example.trabus.models.StudentHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatActivityDriver extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ChatDetailAdapter adapter;
    DatabaseReference reference;
    ArrayList<StudentHelper> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_chat_driver);
        recyclerView=findViewById(R.id.chatrecyclerviewdriver);
        adapter=new ChatDetailAdapter(list, ChatActivityDriver.this);
        recyclerView.setAdapter(adapter);
        linearLayoutManager=new LinearLayoutManager(ChatActivityDriver.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        reference= FirebaseDatabase.getInstance().getReference().child("User").child("Students").child("Profiles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()) {
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        StudentHelper helper =ds.getValue(StudentHelper.class);
                        assert helper!=null;
                        list.add(helper);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    }