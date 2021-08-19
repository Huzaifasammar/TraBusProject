package com.example.trabus.Main.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.trabus.R;

public class Identity extends AppCompatActivity {
RelativeLayout Rldriver,Rlstudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.silver));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_identity);
        Rldriver=findViewById(R.id.RL_driver);
        Rlstudent=findViewById(R.id.RL_student);
        Rldriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Identity.this,Driver_Signup_1.class));
                finish();
            }
        });
        Rlstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Identity.this,Student_Signup.class));
                finish();
            }
        });

    }
}