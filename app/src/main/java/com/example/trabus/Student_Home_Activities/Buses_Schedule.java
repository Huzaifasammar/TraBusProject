package com.example.trabus.Student_Home_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.Student_Navigation_fragments.Home;

public class Buses_Schedule extends AppCompatActivity {
    RelativeLayout bus_schedule;
    ImageView back_arrow_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_buses_schedule);

        bus_schedule=findViewById(R.id.bus_schedule_sample);
        back_arrow_schedule=findViewById(R.id.back_bus_schedule);
        back_arrow_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Buses_Schedule.this, Student_Home.class));
            }
        });


        bus_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Buses_Schedule.this,Bus_schedule_detail.class));
            }
        });

    }
}