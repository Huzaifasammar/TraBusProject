package com.example.trabus.Student_Navigation_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.models.StudentHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;


public class Complaint extends Fragment {
    AutoCompleteTextView complainttype;
    TextInputEditText Name,Email,Regno;
    EditText description;
    FirebaseAuth auth;
    FirebaseUser id;
    Button btncomplain,btncancel;
    FirebaseDatabase database;
    DatabaseReference reference;
    String sName,sEmail,sRegNo,sDescription,sComplaintype,Id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        complainttype=view.findViewById(R.id.complainttype);

        String[]selectGender ={"Buses ","Drivers","Transport Office","Other"};
        ArrayAdapter genderAdapter =new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,selectGender);
        complainttype.setAdapter(genderAdapter);
        Name=(TextInputEditText)view.findViewById(R.id.complainname);
        Email=view.findViewById(R.id.complainemail);
        Regno=view.findViewById(R.id.regno);
        btncomplain=view.findViewById(R.id.submitcmplain);
        btncancel=view.findViewById(R.id.cmplaincancel);
        description=view.findViewById(R.id.complaindescription);
        auth=FirebaseAuth.getInstance();
        id=FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance();
        reference=FirebaseDatabase.getInstance().getReference();
        Id=id.getUid();
        btncomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName= Objects.requireNonNull(Name.getText()).toString().trim();
                sEmail= Objects.requireNonNull(Email.getText()).toString().trim();
                sRegNo= Objects.requireNonNull(Regno.getText()).toString().trim();
                sDescription=description.getText().toString().trim();
                sComplaintype=complainttype.getText().toString();
                if(sName.isEmpty()|| sEmail.isEmpty()||sRegNo.isEmpty()||sDescription.isEmpty())
                {
                    Toast.makeText(getContext(),"Please fill all Fileds",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Yor Complain Sent Successfully",Toast.LENGTH_SHORT).show();
                    HashMap <String, String> helper =new HashMap<>();
                    helper.put("name",sName);
                    helper.put("email",sEmail);
                    helper.put("regno",sRegNo);
                    helper.put("complaintype",sComplaintype);
                    helper.put("description",sDescription);
                    reference.child("User").child("Students").child("Complaints").child(Id).setValue(helper);
                    startActivity(new Intent(Complaint.this.getActivity(), Student_Home.class));
                    getActivity().finish();
                }


            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Complaint.this.getActivity(), Student_Home.class));
                getActivity().finish();
            }
        });
        return view;

    }
}