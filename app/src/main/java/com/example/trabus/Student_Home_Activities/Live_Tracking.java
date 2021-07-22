package com.example.trabus.Student_Home_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.trabus.R;
import com.example.trabus.Student_Home;

public class Live_Tracking extends AppCompatActivity {
ImageView back_arrow_track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_live_tracking);
        back_arrow_track=findViewById(R.id.back_bus_track);
        back_arrow_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Live_Tracking.this, Student_Home.class));
            }
        });
    }
}