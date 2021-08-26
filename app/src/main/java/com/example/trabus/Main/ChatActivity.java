package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.adapter.ChatsAdapter;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ImageView leftarrow;
    ChatsAdapter adapter;
    DatabaseReference reference;
    ArrayList<DriverHelper> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_chat);

        //Initialization ------------------------------------------------------------------------

        recyclerView=findViewById(R.id.chatrecyclerview);
        adapter=new ChatsAdapter(list,ChatActivity.this);
        recyclerView.setAdapter(adapter);
        leftarrow=findViewById(R.id.backStudentHome);
        linearLayoutManager=new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        reference= FirebaseDatabase.getInstance().getReference().child("User").child("Drivers").child("Profile");

        // getting List of Drivers -------------------------------------------------------------------

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()) {
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        DriverHelper helper =ds.getValue(DriverHelper.class);
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

        //backarrow click --------------------------------------------------------------------------------

        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, Student_Home.class));
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}