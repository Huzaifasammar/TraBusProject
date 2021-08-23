package com.example.trabus.Main.Signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.StudentHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Signup extends AppCompatActivity {
  ImageView backaroow;
  TextView caltologinstudent;
  Button btnregisterstudent;
  FloatingActionButton RLstudentimg;
  FirebaseAuth fAuth;
  Uri Imagedata;
  StorageReference reference;
  ProgressDialog progressdialog;
  FirebaseDatabase fdatabase;
  DatabaseReference dbreference;
  TextInputLayout fname, lname, username, email, passwors, cpassword;
  TextInputEditText FirstName,LastName,UserName,Email,Password;
  RadioGroup gender;
  String Sfirstname;
    String Slastname;
    String Susername;
    String Semail;
    String Spassword;
    int Sgender;
  final int RESULT_LOAD_IMAGE = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Imagedata = data.getData();
                CircleImageView imageView = findViewById(R.id.studentimg);
                imageView.setImageURI(Imagedata);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_student_signup);
        initialization();
        onclick();
        progressdialog = new ProgressDialog(Student_Signup.this);
        progressdialog.setTitle("Creating Account");
        progressdialog.setMessage("We are Creating Your Account");

    }
    //onclick Listners
    public void onclick(){
        RLstudentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        btnregisterstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        caltologinstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Signup.this, SignIn.class));
                finish();
            }
        });


    }
    //initialize all variables
    public void initialization(){
        btnregisterstudent=findViewById(R.id.btnregisterstudent);
        caltologinstudent=findViewById(R.id.caltologinstudent);
        RLstudentimg=findViewById(R.id.RL_student_image);
        fAuth = FirebaseAuth.getInstance();
        fdatabase = FirebaseDatabase.getInstance();
        fname = findViewById(R.id.Fname_std_layout);
        lname = findViewById(R.id.lname_std_layout);
        username = findViewById(R.id.username_std_layout);
        email = findViewById(R.id.Email_std_layout);
        passwors = findViewById(R.id.password_std_layout);
        cpassword = findViewById(R.id.cpassword_std_layout);
        FirstName=findViewById(R.id.fname_ET_Student);
        LastName=findViewById(R.id.lname_ET_Student);
        UserName=findViewById(R.id.username_ET_Student);
        Email=findViewById(R.id.email_ET_Student);
        Password=findViewById(R.id.password_ET_Student);
        gender=findViewById(R.id.checked_Student);
        reference= FirebaseStorage.getInstance().getReference().child("ProfileImagesStudents");
        dbreference=FirebaseDatabase.getInstance().getReference();

    }
    // validate Text Fields
    public void validation() {
        if (!validatefname() || !validatelname() || !validateusername() || !validateemail() || !validatePassword()) {
            Toast.makeText(getApplicationContext(), "please fill require fields", Toast.LENGTH_LONG).show();
        } else {
           savedata();
        }
    }
    public boolean validatefname() {
        String val = fname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fname.setError("This field cannot be empty");
            return false;
        } else {
            fname.setError(null);
            fname.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatelname() {
        String val = lname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            lname.setError("This field cannot be empty");
            return false;
        } else {
            lname.setError(null);
            lname.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateusername() {
        String val = username.getEditText().getText().toString().trim();
        String checkSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            username.setError("This field cannot be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large");
            return false;
        } else if (val.length() < 4) {
            username.setError("Username must be 4 digits");
            return false;
        } else if (!val.matches(checkSpace)) {
            username.setError("White Spaces are not allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateemail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[iiu]+.+[edu].+[pk]";
        if (val.isEmpty()) {
            email.setError("This field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Plese use email abc@iiu.edu.pk!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = passwors.getEditText().getText().toString().trim();
        String val1 = cpassword.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            passwors.setError("This field cannot be empty");
            return false;
        } else if (val1.isEmpty()) {
            cpassword.setError("This field cannot be empty");
            return false;
        } else if (!val1.matches(val)) {
            cpassword.setError("Password not match!");
            return false;
        } else {
            passwors.setError(null);
            passwors.setErrorEnabled(false);
            cpassword.setError(null);
            cpassword.setErrorEnabled(false);
            return true;
        }
    }
    public void getEdittextdata()
    {
        Sfirstname=FirstName.getText().toString().trim();
        Slastname=LastName.getText().toString().trim();
        Susername=UserName.getText().toString().trim();
        Semail=Email.getText().toString().trim();
        Spassword=Password.getText().toString().trim();
        Sgender=gender.getCheckedRadioButtonId();
    }

    public void savedata() {
        getEdittextdata();
        fAuth.createUserWithEmailAndPassword(Semail,Spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressdialog.show();
                    String id = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                    String uniqueString = UUID.randomUUID().toString();
                    StorageReference Imagename = reference.child(uniqueString);
                    Imagename.putFile(Imagedata).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Select profile picture", Toast.LENGTH_SHORT).show();
                            StudentHelper helper = new StudentHelper(Sfirstname,Slastname,Susername,Semail,Spassword,Sgender, "",id);
                            dbreference.child("User").child("Students").child("Profiles").child(id).setValue(helper);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StudentHelper helper = new StudentHelper(Sfirstname,Slastname,Susername,Semail,Spassword,Sgender,uri.toString(),id);
                                    dbreference.child("User").child("Students").child("Profiles").child(id).setValue(helper);
                                    progressdialog.dismiss();
                                    startActivity(new Intent(Student_Signup.this, SignIn.class));
                                    finish();
                                }
                            });

                        }
                    });


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error Signing up", Toast.LENGTH_SHORT).show();
            }
        });
    }
}