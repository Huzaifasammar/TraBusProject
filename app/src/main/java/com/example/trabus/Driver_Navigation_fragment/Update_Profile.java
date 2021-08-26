package com.example.trabus.Driver_Navigation_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Update_Profile extends Fragment {

    private static final int RESULT_LOAD_IMAGE =0 ;
    private View v;
    private CircleImageView circleImageView;
    private TextView mNameTx,mEmailTx;
    private TextInputEditText mNumber,mBusNo;
    private Button mUpdate;

    private Uri image;

    private FirebaseUser mCurrentUser;
    private DatabaseReference reference;
    private FirebaseStorage storage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_update_profile_driver, container, false);
       intialise();
       gettingData();
       updateDriver();

       return v;
    }

    private void updateDriver() {
        // select driver image
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference storageReference = storage.getReference().child("ProfileImagesDrivers");

                storageReference.putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUri = uri.toString();

                                    HashMap hashMap = new HashMap();
                                    hashMap.put("imageurl",imageUri);
                                    hashMap.put("busno",mBusNo.getText().toString().trim());
                                    hashMap.put("phonenumber",mNumber.getText().toString().trim());
                                    reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                                                gettingData();
                                            }
                                            else
                                                Toast.makeText(getContext(),"Profile not Updated",Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(getContext(),"Database Error",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

            }
        });


    }

    private void gettingData() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                        DriverHelper helper = snapshot.getValue(DriverHelper.class);
                        assert helper != null;
                        mNameTx.setText(helper.getFname()+" "+helper.getLname());
                        mEmailTx.setText(helper.getEmail());
                        mBusNo.setText(helper.getBusno());
                        mNumber.setText(helper.getPhonenumber());
                        Picasso.get().load(helper.getImageurl()).into(circleImageView);
                    }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void intialise() {
        circleImageView = (CircleImageView)v.findViewById(R.id.driver_pic_d);
        mNameTx = (TextView)v.findViewById(R.id.driver_name_p);
        mEmailTx = (TextView)v.findViewById(R.id.driver_email);
        mBusNo = (TextInputEditText) v.findViewById(R.id.bus_Driver);
        mNumber = (TextInputEditText)v.findViewById(R.id.phoneDiver);

        mUpdate = (Button)v.findViewById(R.id.driverUpdate);



        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("User").child("Drivers").child("Profile").child(mCurrentUser.getUid());
        storage = FirebaseStorage.getInstance();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                image = data.getData();
                circleImageView.setImageURI(image);
            }
        }
    }
}