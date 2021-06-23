package com.example.trabus.Main.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;

public class Driver_Signup_1 extends AppCompatActivity {
ImageView backarrow;
TextView caltologindriver;
Button btnregisterdriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_driver_signup1);
        backarrow=findViewById(R.id.backarrow);
        btnregisterdriver=findViewById(R.id.btnregisterdriver);
        caltologindriver=findViewById(R.id.caltologindriver);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Driver_Signup_1.this,Identity.class));
            }
        });
        btnregisterdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Driver_Signup_1.this,SignIn.class));
            }
        });
        caltologindriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Driver_Signup_1.this, SignIn.class));
            }
        });
    }
}