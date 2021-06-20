package com.example.trabus.Main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.trabus.MainActivity;
import com.example.trabus.R;

public class SplachScreenActivity extends AppCompatActivity {
  Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splach_screen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplachScreenActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}