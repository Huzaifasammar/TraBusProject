package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.adapter.MessageAdapter;
import com.example.trabus.models.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatsActivity extends AppCompatActivity {
    ImageView leftarrow;
    EditText sendmessage;
    ImageView btnsend;
    Query query;
    FirebaseUser Currentuser;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference dbreference,reference;
    MessageAdapter messageAdapter;
    List<ChatModel>mChat=new ArrayList<>();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_chats);
         Initialize();
         showsendmessage();
         onClick();


    }

// Send Message

  private void sendmessage()
  {
      String Message=sendmessage.getText().toString();
      ChatModel chatModel=new ChatModel(Message,Currentuser.getUid());
      reference.child("User").child("Chat").child(Currentuser.getUid()).push().setValue(chatModel);
      sendmessage.setText(null);
      messageAdapter.notifyDataSetChanged();
  }

  //  Show Send Messages

  private void showsendmessage()
  {
      query=reference.child("User").child("Chat").child(Currentuser.getUid());
      query.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              mChat.clear();
              if(snapshot.exists()) {
                  for (DataSnapshot d : snapshot.getChildren()) {
                      ChatModel chatModel = d.getValue(ChatModel.class);
                      assert chatModel != null;
                      mChat.add(chatModel);
                  }
                  messageAdapter.notifyDataSetChanged();
              }

          }

          @Override
          public void onCancelled(@NonNull @NotNull DatabaseError error) {

          }
      });
  }

// On Click Listeners

public void onClick()
{
    btnsend.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            sendmessage();
        }
    });
    leftarrow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkcurrentuser();
        }
    });
}

// Initialize all fields

public void Initialize()
{
    firebaseAuth = FirebaseAuth.getInstance();
    database = FirebaseDatabase.getInstance();
    dbreference = FirebaseDatabase.getInstance().getReference();
    Currentuser = FirebaseAuth.getInstance().getCurrentUser();
    leftarrow=findViewById(R.id.back_arrow_Chat);
    recyclerView=findViewById(R.id.chatrecyclerview);
    id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    messageAdapter=new MessageAdapter(mChat,id,ChatsActivity.this);
    sendmessage=findViewById(R.id.sms_editText);
    btnsend=findViewById(R.id.send_sms_btn);
    reference=FirebaseDatabase.getInstance().getReference();
    linearLayoutManager=new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(messageAdapter);
}

// On back pressed Check Current User Driver or Student

    public void checkcurrentuser() {
        if (Currentuser != null) {

            dbreference.child("User").child("Students").child(Currentuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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