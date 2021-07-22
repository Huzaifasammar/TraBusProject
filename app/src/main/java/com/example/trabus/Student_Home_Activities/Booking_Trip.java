package com.example.trabus.Student_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.textfield.TextInputLayout;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.CollationElementIterator;
import java.util.Calendar;

public class Booking_Trip extends AppCompatActivity {
    AutoCompleteTextView spnpickup,spndrop;
    ImageView backtrip,drop1,drop2;
    ArrayAdapter<CharSequence> adapter;
    LinearLayout calender;
    DatePickerDialog datePickerDialog;
    Button check_avaliable;
    RelativeLayout bottom;
    TextView date,busdate;
    BottomSheetBehavior behavior;
    Dialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_booking_trip);
        spnpickup=findViewById(R.id.pickup);
        spndrop=findViewById(R.id.drop);
        date=findViewById(R.id.tvdeparturedate);
        busdate=findViewById(R.id.tv_date);
        drop1=findViewById(R.id.drop1);
        drop2=findViewById(R.id.drop2);
        bottom=findViewById(R.id.Rl_bottom_sheet);
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
        dialog=new Dialog(Booking_Trip.this);
        dialog.setContentView(R.layout.activity_book_bus);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.book_bus_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations =R.style.animation;

        Button book_now =dialog.findViewById(R.id.book);
        Button book_cancel=dialog.findViewById(R.id.book_cancel);
        TextView book_date=dialog.findViewById(R.id.tv_date4);


        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Booking_Trip.this, "Congratulation you have sucessfully booked bus", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        book_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        check_avaliable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatepickup()||!validatedrop()||!validatecalendar())
                {


                }
                 else {
                    if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }


            }
        });

        backtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Trip.this, Student_Home.class));
            }
        });
        drop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnpickup.showDropDown();
            }
        });
        drop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spndrop.showDropDown();
            }
        });

        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
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
                            book_date.setText(dayOfMonth+"-"+Month+"-"+year);

                        }
                    },year,month,day);
                    datePickerDialog.show();
            }
        });


        adapter=ArrayAdapter.createFromResource(this,R.array.spinnerlistpickup, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
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

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.spinnerlistdropoff, android.R.layout.simple_dropdown_item_1line);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown);
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

    public boolean validatepickup() {
        String val = spnpickup.getText().toString();
        if (val.isEmpty()) {
           Toast.makeText(getApplicationContext(),"Please Select pickup location",Toast.LENGTH_LONG).show();
            return false;
        } else {
            spnpickup.setError(null);
            return true;
        }
    }
    public boolean validatedrop() {
        String val = spndrop.getText().toString();
        if (val.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please Select drop off location",Toast.LENGTH_LONG).show();
            return false;
        } else {
            spndrop.setError(null);
            return true;
        }
    }
    public boolean validatecalendar() {
        String val =date.getText().toString();
        if (val.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select travel date", Toast.LENGTH_LONG).show();
            return false;
        } else {
            date.setError(null);
            return true;
        }

    }

}