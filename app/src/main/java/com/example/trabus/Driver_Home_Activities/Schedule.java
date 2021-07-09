package com.example.trabus.Driver_Home_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;

public class Schedule extends AppCompatActivity {
    RelativeLayout busno;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_schedule);
        busno=findViewById(R.id.sample_busno);
        back=findViewById(R.id.backschedule);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule.this, Driver_Home.class));
            }
        });
        busno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule.this,Schedule_Detail.class));
            }
        });
    }
}