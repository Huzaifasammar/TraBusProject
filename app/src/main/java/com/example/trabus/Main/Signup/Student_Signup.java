package com.example.trabus.Main.Signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Student_Signup extends AppCompatActivity {
    ImageView backaroow;
    TextView caltologinstudent;
    Button btnregisterstudent;
    private FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage Storage;
    TextInputEditText email, password;
    String Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        AlertDialog alertDialog = new AlertDialog.Builder(Student_Signup.this).create();
        alertDialog.setMessage("Successfully created your account");
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.white_backgroun_with_border));
        setContentView(R.layout.activity_student_signup);
        backaroow = findViewById(R.id.backarrowstudent);
        btnregisterstudent = findViewById(R.id.btnregisterstudent);
        caltologinstudent = findViewById(R.id.caltologinstudent);
        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.password_ET);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference=FirebaseDatabase.getInstance().getReference();
        backaroow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Signup.this, Identity.class));
            }
        });
        btnregisterstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });
        caltologinstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Signup.this, SignIn.class));
            }
        });

    }

    public void savedata(){
        Email = Objects.requireNonNull(email.getText()).toString().trim();
        Password = Objects.requireNonNull(password.getText()).toString().trim();
        fAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Signup Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Student_Signup.this, SignIn.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error Signup", Toast.LENGTH_SHORT).show();

            }
        });
    }
}