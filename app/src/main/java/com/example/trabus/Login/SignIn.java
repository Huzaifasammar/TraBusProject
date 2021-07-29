package com.example.trabus.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.Main.Signup.Identity;
import com.example.trabus.Student_Home;
import com.example.trabus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SignIn extends AppCompatActivity {
    TextView caltoidentity, forgetsgnin;
    ImageView leftarrow;
    Button btnsignin;
    String id;
    TextInputLayout Lemail, Lpassword;
    TextInputEditText Temail, Tpassword;
    String Email, Password;
    FirebaseDatabase database;
    FirebaseAuth fAuth;
    FirebaseUser Currentuser;
    ProgressDialog progressdialog;
    DatabaseReference dbreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_sign_in);
        initialize();
        onclick();
        progressdialog = new ProgressDialog(SignIn.this);
        progressdialog.setTitle("Logging In");
        progressdialog.setMessage("We are Logging you in");

    }

    public void initialize() {
        caltoidentity = findViewById(R.id.caltosignup);
        leftarrow = findViewById(R.id.leftarrowsignin);
        forgetsgnin = findViewById(R.id.forgetsgnin);
        btnsignin = findViewById(R.id.btnsignin);
        Lemail = findViewById(R.id.email_layout);
        Lpassword = findViewById(R.id.password_layout);
        Temail = findViewById(R.id.email_ET);
        Tpassword = findViewById(R.id.password_ET);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbreference = FirebaseDatabase.getInstance().getReference();
        Currentuser = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void onclick() {
        caltoidentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Identity.class));
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }

        });
        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Identity.class));
            }
        });
        forgetsgnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Forget_Password_Activity.class));
            }
        });
    }

    public boolean validateemail() {
        String val = Lemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[iiu]+.+[edu].+[pk]";
        if (val.isEmpty()) {
            Lemail.setError("This field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            Lemail.setError("Plese use email abc@iiu.edu.pk!");
            return false;
        } else {
            Lemail.setError(null);
            Lemail.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatepassword() {
        String val = Lpassword.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Lpassword.setError("This field cannot be empty");
            return false;
        } else if (val.length() < 6) {
            Lpassword.setError("Password Must be greter or equal to 6 character");
            return false;
        } else {
            Lpassword.setError(null);
            Lpassword.setErrorEnabled(false);
            return true;
        }
    }

    public void validation() {
        if (!validateemail() || !validatepassword()) {
            Toast.makeText(getApplicationContext(), "please fill require fields", Toast.LENGTH_LONG).show();
        } else {
            Authentication();
        }
    }

    public void Authentication() {

        Email = Temail.getText().toString().trim();
        Password = Tpassword.getText().toString().trim();
        progressdialog.show();
        fAuth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                String id = authResult.getUser().getUid();
                dbreference.child("User").child("Students").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (fAuth.getCurrentUser() != null) {
                                progressdialog.dismiss();
                                startActivity(new Intent(SignIn.this, Student_Home.class));
                                finish();
                            }
                        } else {
                            if (fAuth.getCurrentUser() != null) {
                                progressdialog.dismiss();
                                startActivity(new Intent(SignIn.this, Driver_Home.class));
                                finish();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressdialog.dismiss();
                Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

