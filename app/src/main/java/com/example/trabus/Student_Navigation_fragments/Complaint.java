package com.example.trabus.Student_Navigation_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.trabus.R;


public class Complaint extends Fragment {
    AutoCompleteTextView complainttype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        complainttype=view.findViewById(R.id.complainttype);
        String[]selectGender ={"Buses ","Drivers","Transport Office","Other"};
        ArrayAdapter genderAdapter =new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,selectGender);
        complainttype.setAdapter(genderAdapter);
        return view;

    }
}