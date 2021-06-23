package com.example.trabus.Main.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trabus.R;

public class Student_Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_student_signup);
    }
}