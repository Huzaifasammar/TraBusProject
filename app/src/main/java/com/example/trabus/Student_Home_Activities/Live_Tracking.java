package com.example.trabus.Student_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.adapter.ContactsAdapter;
import com.example.trabus.adapter.TrackingAdapter;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Live_Tracking extends AppCompatActivity  {
    public Live_Tracking()
    {
    }
    ImageView back_arrow_track;
    FirebaseDatabase database;
    RecyclerView Tracking;
    EditText search;
    ArrayList<DriverHelper> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_live_tracking);
        database=FirebaseDatabase.getInstance();
        search=findViewById(R.id.searchbus);
        Tracking=findViewById(R.id.recyclerviewtracking);
        final TrackingAdapter adapter= new TrackingAdapter(list,Live_Tracking.this);
        Tracking.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Live_Tracking.this);
        Tracking.setLayoutManager(linearLayoutManager);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               filter(s.toString());

            }
        });
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


        back_arrow_track=findViewById(R.id.back_bus_track);
        back_arrow_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Live_Tracking.this, Student_Home.class));
                finish();
            }
        });
    }
    private void  filter (String text)
    {
         ArrayList<DriverHelper> filtereddata=new ArrayList<>();
         TrackingAdapter adapter= new TrackingAdapter(list,Live_Tracking.this);
         Tracking.setAdapter(adapter);
        database.getReference().child("User").child("Drivers").child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    DriverHelper helper=dataSnapshot.getValue(DriverHelper.class);
                        if(helper.getBusno().toLowerCase().contains(text.toLowerCase()))
                        {
                            filtereddata.add(helper);
                        }
                        adapter.filterlist(filtereddata);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

}