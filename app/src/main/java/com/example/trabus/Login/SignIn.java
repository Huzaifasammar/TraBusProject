package com.example.trabus.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.Main.RegistrationActivity;
import com.example.trabus.Main.Signup.Identity;
import com.example.trabus.MainActivity;
import com.example.trabus.R;

public class SignIn extends AppCompatActivity {
      TextView caltoidentity,forgetsgnin;
      ImageView leftarrow;
      Button btnsignin;
      RadioButton btndriver,btnstdnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_sign_in);
        caltoidentity=findViewById(R.id.caltosignup);
        leftarrow=findViewById(R.id.leftarrowsignin);
        btndriver=findViewById(R.id.radiobtndriver);
        btnstdnt=findViewById(R.id.radiobtnstdnt);
        forgetsgnin=findViewById(R.id.forgetsgnin);
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
                if (btndriver.isChecked()) {
                    startActivity(new Intent(SignIn.this, Driver_Home.class));
                }
                else if(btnstdnt.isChecked())
                {
                    startActivity(new Intent(SignIn.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please choose either you are driver or Student",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, RegistrationActivity.class));
            }
        });
        forgetsgnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Forget_Password_Activity.class));
            }
        });
    }
}