package com.example.trabus.Main.Signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Driver_Signup_1 extends AppCompatActivity {
    ImageView backarrow;
    Uri image;
    RelativeLayout Rlimage;
    TextView caltologindriver;
    Button btnregisterdriver;
    AutoCompleteTextView busno;
    TextInputEditText pnumber,FirstName,LastName,UserName,Email_et,Password_et;
    private FirebaseAuth fAuth;
    StorageReference reference;
    DatabaseReference dbreference;
    ProgressDialog progressdialog;
    FirebaseDatabase fdatabase;
    String Fname,Lname,Username,Email,Password,BusNo,PhoneNumber;
    TextInputLayout fname, lname, username, email, passwors, cpassword;
    final int RESULT_LOAD_IMAGE = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                image = data.getData();
                CircleImageView imageView = findViewById(R.id.driverimg);
                imageView.setImageURI(image);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_driver_signup1);
        initialization();
        onclick();
        progressdialog=new ProgressDialog(Driver_Signup_1.this);
        progressdialog.setTitle("Creating Account");
        progressdialog.setMessage("We are Creating Your Account");

        String[] selectGender = {"Bus No 1", "Bus No 2 ", "Bus No 3", "Bus No 4", "Bus No 5 ", "Bus No 6", "Bus No 7", "Bus No 8 ", "Bus No 9"};
        ArrayAdapter genderAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, selectGender);
        busno.setAdapter(genderAdapter);

    }
    // onclicklisteners
    public void onclick() {
        Rlimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Driver_Signup_1.this, Identity.class));
            }
        });
        btnregisterdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });
        caltologindriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Driver_Signup_1.this, SignIn.class));
            }
        });
    }
    //initialize all variables
    public void initialization() {
        fAuth=FirebaseAuth.getInstance();
        fdatabase=FirebaseDatabase.getInstance();
        backarrow = findViewById(R.id.backarrow);
        Rlimage = findViewById(R.id.RL_image);
        busno = findViewById(R.id.busno);
        FirstName=findViewById(R.id.fname_ET);
        LastName=findViewById(R.id.lname_ET);
        UserName=findViewById(R.id.username_ET);
        Email_et=findViewById(R.id.email_ET);
        Password_et=findViewById(R.id.password_ET);
        reference=FirebaseStorage.getInstance().getReference().child("ProfileImagesDrivers");
        btnregisterdriver = findViewById(R.id.btnregisterdriver);
        caltologindriver = findViewById(R.id.caltologindriver);
        fname = findViewById(R.id.Fname_layout);
        lname = findViewById(R.id.lname_layout);
        username = findViewById(R.id.username_layout);
        email = findViewById(R.id.Email_layout);
        passwors = findViewById(R.id.password_layout);
        cpassword = findViewById(R.id.cpassword_layout);
        pnumber = findViewById(R.id.phone);
        dbreference=FirebaseDatabase.getInstance().getReference();
    }
    public void getEditTextData()
    {
        Fname= Objects.requireNonNull(FirstName.getText()).toString().trim();
        Lname= Objects.requireNonNull(LastName.getText()).toString().trim();
        Username= Objects.requireNonNull(UserName.getText()).toString().trim();
        Email= Objects.requireNonNull(Email_et.getText()).toString().trim();
        Password= Objects.requireNonNull(Password_et.getText()).toString().trim();
        BusNo=busno.getText().toString();
        PhoneNumber=pnumber.getEditableText().toString().trim();
    }
    // validate Text Fields
    public void validation() {
        if (!validatefname() || !validatelname() || !validateusername() || !validateemail() || !validatePassword() || !validatephonenumber()){
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
        }
        else if(val.length() < 6)
        {
            passwors.setError("Password Must be greter or equal to 6 character");
            return false;
        }
        else if (val1.isEmpty()) {
            cpassword.setError("This field cannot be empty");
            return false;
        }

        else if (!val1.matches(val)) {
            cpassword.setError("Password not match!");
            return false;
        }
        else {
            passwors.setError(null);
            passwors.setErrorEnabled(false);
            cpassword.setError(null);
            cpassword.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatephonenumber()
    {
        String val=pnumber.getEditableText().toString().trim();
        if(val.length() != 10)
        {
            pnumber.setError("Number pattern not correct");
            return false;
        }
        else
        {
            pnumber.setError(null);
            return true;
        }
    }

    public void savedata()
    {
        getEditTextData();
        fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressdialog.show();
                    String id= Objects.requireNonNull(task.getResult().getUser()).getUid();
                    String uniqueString = UUID.randomUUID().toString();
                    StorageReference Imagename = reference.child(uniqueString);
                    try {
                        Imagename.putFile(image).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Select profile picture", Toast.LENGTH_SHORT).show();
                                DriverHelper helper = new DriverHelper(id, Fname, Lname, Username, Email, Password, BusNo, PhoneNumber, "");
                                dbreference.child("User").child("Drivers").child("Profile").child(id).setValue(helper);
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DriverHelper helper = new DriverHelper(id, Fname, Lname, Username, Email, Password, BusNo, PhoneNumber, uri.toString());
                                        dbreference.child("User").child("Drivers").child("Profile").child(id).setValue(helper);
                                        progressdialog.dismiss();
                                        startActivity(new Intent(Driver_Signup_1.this, SignIn.class));
                                        finish();
                                    }
                                });
                            }
                        });

                    } catch (DatabaseException e)
                    {
                        System.out.println("Error"+ e);
                    }
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