package com.example.trabus.Main.Signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class Driver_Signup_1 extends AppCompatActivity {
ImageView backarrow;
RelativeLayout Rlimage;
TextView caltologindriver;
Button btnregisterdriver;
AutoCompleteTextView busno;
final int RESULT_LOAD_IMAGE=0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri ImageData = data.getData();
                CircleImageView imageView = findViewById(R.id.driverimg);
                imageView.setImageURI(ImageData);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_driver_signup1);
        backarrow=findViewById(R.id.backarrow);
        Rlimage=findViewById(R.id.RL_image);
        busno=findViewById(R.id.busno);
        btnregisterdriver=findViewById(R.id.btnregisterdriver);
        caltologindriver=findViewById(R.id.caltologindriver);
        String[]selectGender ={"Bus No 1","Bus No 2 ","Bus No 3","Bus No 4","Bus No 5 ","Bus No 6","Bus No 7","Bus No 8 ","Bus No 9"};
        ArrayAdapter genderAdapter =new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,selectGender);
        busno.setAdapter(genderAdapter);
        Rlimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


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