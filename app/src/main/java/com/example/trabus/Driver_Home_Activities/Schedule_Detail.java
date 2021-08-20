package com.example.trabus.Driver_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabus.R;
import com.example.trabus.models.ScheduleBusNo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Schedule_Detail extends AppCompatActivity {
    ImageView back;
    String Busno;
    TextView firsttime,seconddtime,thirdtime,fourthtime,fifththtime,tvtoolbarschedule;
    DatabaseReference reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_schedule_detail);
        Busno=getIntent().getStringExtra("busno");
        firsttime=findViewById(R.id.tvfirsttime);
        seconddtime=findViewById(R.id.tv2ndtime);
        thirdtime=findViewById(R.id.tv3rdtime);
        fourthtime=findViewById(R.id.tv4thtime);
        fifththtime=findViewById(R.id.tv5thtime);
        tvtoolbarschedule=findViewById(R.id.tvtoolbarschedule);
        tvtoolbarschedule.setText(Busno+" Schedule");
        reference= FirebaseDatabase.getInstance().getReference().child("User").child("Schedule").child(Busno);
        back=findViewById(R.id.backscheduledetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule_Detail.this,Schedule.class));
                finish();
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ScheduleBusNo busno=snapshot.getValue(ScheduleBusNo.class);
                assert busno != null;
                String one=busno.getFirst_time();
                String two=busno.getSecond_time();
                String three=busno.getThird_time();
                String four=busno.getFourt_time();
                String five=busno.getFifth_time();
                firsttime.setText(one);
                seconddtime.setText(two);
                thirdtime.setText(three);
                fourthtime.setText(four);
                fifththtime.setText(five);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}