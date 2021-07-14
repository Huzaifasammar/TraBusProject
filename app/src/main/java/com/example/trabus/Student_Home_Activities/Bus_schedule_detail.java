package com.example.trabus.Student_Home_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.trabus.R;

public class Bus_schedule_detail extends AppCompatActivity {
    ImageView back_bus_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule_detail);
        back_bus_detail=findViewById(R.id.back_bus_schedule_detail);
        back_bus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bus_schedule_detail.this,Buses_Schedule.class));
            }
        });
    }
}