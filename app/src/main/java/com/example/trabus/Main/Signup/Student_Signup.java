package com.example.trabus.Main.Signup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;

public class Student_Signup extends AppCompatActivity {
  ImageView backaroow;
  TextView caltologinstudent;
  Button btnregisterstudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        AlertDialog alertDialog = new AlertDialog.Builder(Student_Signup.this).create();
        alertDialog.setMessage("Successfully created your account");
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.white_backgroun_with_border));
        setContentView(R.layout.activity_student_signup);
        backaroow=findViewById(R.id.backarrowstudent);
        btnregisterstudent=findViewById(R.id.btnregisterstudent);
        caltologinstudent=findViewById(R.id.caltologinstudent);
        backaroow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Signup.this,Identity.class));
            }
        });
        btnregisterstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Signup.this,SignIn.class));
            }
        });
        caltologinstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Signup.this, SignIn.class));
            }
        });
    }
}