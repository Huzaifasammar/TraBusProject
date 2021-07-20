package com.example.trabus.Student_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Driver_Home_Activities.Maintanance;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.CollationElementIterator;
import java.util.Calendar;

public class Booking_Trip extends AppCompatActivity {
    Spinner spnpickup,spndrop;
    ImageView backtrip;
    LinearLayout calender;
    DatePickerDialog datePickerDialog;
    Button check_avaliable;
    RelativeLayout bottomsheet;
    TextView date,busdate;
    BottomSheetBehavior behavior;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_booking_trip);

        spnpickup=findViewById(R.id.spinner_pickup);
        spndrop=findViewById(R.id.spinner_dropoff);
        date=findViewById(R.id.tvdeparturedate);
        busdate=findViewById(R.id.tv_date);
        backtrip=findViewById(R.id.back_trip_booking);
        calender=findViewById(R.id.ll_calender_booking);
        check_avaliable=findViewById(R.id.btn_check_avalibilty);
        View bottomsheet=findViewById(R.id.RL_design_bottom_sheet);
         behavior=BottomSheetBehavior.from(bottomsheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull @NotNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                      break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull @NotNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback","slideOffset: " + slideOffset);

            }
        });
        check_avaliable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        backtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Trip.this, Student_Home.class));
            }
        });

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Calendar clndr=Calendar.getInstance();
                    int day=clndr.get(Calendar.DAY_OF_MONTH);
                    int month=clndr.get(Calendar.MONTH);
                    int year=clndr.get(Calendar.YEAR);
                    datePickerDialog=new DatePickerDialog(Booking_Trip.this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            int Month=month+1;
                            date.setText(dayOfMonth+"-"+Month+"-"+year);
                            busdate.setText(dayOfMonth+"-"+Month+"-"+year);

                        }
                    },year,month,day);
                    datePickerDialog.show();
            }
        });


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.spinnerlistpickup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnpickup.setAdapter(adapter);

        spnpickup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.spinnerlistdropoff, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spndrop.setAdapter(adapter1);

        spndrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}