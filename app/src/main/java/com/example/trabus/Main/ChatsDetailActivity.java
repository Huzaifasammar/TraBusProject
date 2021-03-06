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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsDetailActivity extends AppCompatActivity {
    ImageView leftarrow;
    EditText sendmessage;
    ImageView btnsend;
    TextView timesender;
    FirebaseUser Currentuser;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference dbreference,reference,reference1;
    MessageAdapter messageAdapter;
    List<ChatModel>mChat=new ArrayList<>();
    List<ChatModel>mChatReceiver=new ArrayList<>();
    CircleImageView imageView;
    TextView Name;
    String userid,senderid,senderRoom,receiverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_chats);
        imageView=findViewById(R.id.profilepic);
        Name=findViewById(R.id.usernamechatdetail);
        Currentuser=FirebaseAuth.getInstance().getCurrentUser();
        senderid =getIntent().getStringExtra("id");
        String name=getIntent().getStringExtra("username");
        Name.setText(name);
        senderRoom=Currentuser.getUid()+senderid;
        receiverRoom=senderid+Currentuser.getUid();
        String pic=getIntent().getStringExtra("profilepic");
        Picasso.get().load(pic).placeholder(R.drawable.ic_profile).into(imageView);
         Initialize();
         onClick();
         showsendmessage();
         //showReceivemessage();



    }

// Send Message

  private void sendmessage()
  {
      String Message=sendmessage.getText().toString();
      Date date=new Date();
      ChatModel chatModel=new ChatModel(Message,Currentuser.getUid(),senderid,date.getTime());
      reference.child("User").child("Chat").child(senderRoom).child("Message").push().setValue(chatModel);
      reference1.child("User").child("Chat").child(receiverRoom).child("Message").push().setValue(chatModel);
      sendmessage.setText(null);
      messageAdapter.notifyDataSetChanged();
  }

  //  Show Send Messages

  private void showsendmessage()
  {
      reference.child("User").child("Chat").child(senderRoom).child("Message").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
             mChat.clear();
              if(snapshot.exists()) {
                  for (DataSnapshot d : snapshot.getChildren()) {
                      ChatModel chatModel = d.getValue(ChatModel.class);
                      assert chatModel != null;

                      {
                          mChat.add(chatModel);

                      }
                      messageAdapter.notifyDataSetChanged();
                  }

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
           startActivity(new Intent(ChatsDetailActivity.this,ChatActivity.class));
           finish();
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
    reference1=FirebaseDatabase.getInstance().getReference();
    leftarrow=findViewById(R.id.back_arrow_Chat);
    recyclerView=findViewById(R.id.chatrecyclerview);
    userid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

    sendmessage=findViewById(R.id.sms_editText);
    btnsend=findViewById(R.id.send_sms_btn);
    reference=FirebaseDatabase.getInstance().getReference();
    linearLayoutManager=new LinearLayoutManager(getApplicationContext());
    linearLayoutManager.setStackFromEnd(true);
    recyclerView.setLayoutManager(linearLayoutManager);
    timesender=findViewById(R.id.timesender);
    messageAdapter=new MessageAdapter(mChat, ChatsDetailActivity.this);
    recyclerView.setAdapter(messageAdapter);
}
    }