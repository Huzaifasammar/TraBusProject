package com.example.trabus.Login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.trabus.R;

public class Forget_Password_Activity extends AppCompatActivity {
    ImageView backaroowforgett;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_forget_password);
        backaroowforgett=findViewById(R.id.backaroowforgett);
        backaroowforgett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forget_Password_Activity.this,SignIn.class));
            }
        });
    }
}