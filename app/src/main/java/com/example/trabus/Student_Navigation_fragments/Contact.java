package com.example.trabus.Student_Navigation_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabus.R;
import com.example.trabus.adapter.ContactsAdapter;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Contact extends Fragment {
    public Contact()
    {}
     FirebaseDatabase database;
     RecyclerView contact;
     ArrayList<DriverHelper> list=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        contact=view.findViewById(R.id.contact_recyclerview);
        final ContactsAdapter adapter=new ContactsAdapter(list,getContext());
        contact.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        contact.setLayoutManager(linearLayoutManager);
        database=FirebaseDatabase.getInstance();
        database.getReference().child("User").child("Drivers").child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            list.clear();
            for (DataSnapshot dataSnapshot:snapshot.getChildren())
            {
                DriverHelper helper=dataSnapshot.getValue(DriverHelper.class);
                assert helper != null;
                list.add(helper);

            }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return view;
    }
}