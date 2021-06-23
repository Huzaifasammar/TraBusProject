package com.example.trabus.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabus.Main.RegistrationActivity;
import com.example.trabus.Main.Signup.Identity;
import com.example.trabus.MainActivity;
import com.example.trabus.R;

public class SignIn extends AppCompatActivity {
      TextView caltoidentity;
      ImageView leftarrow;
      Button btnsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_sign_in);
        caltoidentity=findViewById(R.id.caltosignup);
        leftarrow=findViewById(R.id.leftarrowsignin);
        btnsignin=findViewById(R.id.btnsignin);
        caltoidentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Identity.class));
            }
        });
       btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, MainActivity.class));
            }
        });
        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, RegistrationActivity.class));
            }
        });
    }
}