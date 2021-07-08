package com.example.trabus.Driver_Home_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;

import java.util.Calendar;

public class Maintanance extends AppCompatActivity {
    RelativeLayout driversample;
    ImageView back,calendar,clock;
    TextView name,busno,time,date;
    AutoCompleteTextView PetrolPump;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar mcalendar;
    Button submit,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_maintanance);
        PetrolPump= findViewById(R.id.petrolpump);
        String[]selectpump ={"Pso Pump police Line","Pso pump G11 markaz","Pso Pump I10 2"};
        ArrayAdapter pumpAdapter =new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,selectpump);
        PetrolPump.setAdapter(pumpAdapter);
        submit=findViewById(R.id.submit);
        cancel=findViewById(R.id.cancel);
        driversample=findViewById(R.id.driversample);
        driversample.setBackground(getResources().getDrawable(R.drawable.white_backgroun_with_border));
        name=findViewById(R.id.driver_name_nav);
        name.setTextColor(getResources().getColor(R.color.black));
        busno=findViewById(R.id.driver_bus_nav);
        busno.setTextColor(getResources().getColor(R.color.black));
        back=findViewById(R.id.backmaintanance);
        calendar=findViewById(R.id.calendar);
        clock=findViewById(R.id.clock);
        time=findViewById(R.id.timeselected);
        date=findViewById(R.id.dateselected);

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcalendar=Calendar.getInstance();
                int hour=mcalendar.get(Calendar.HOUR_OF_DAY);
                int minute=mcalendar.get(Calendar.MINUTE);
                int am=mcalendar.get(Calendar.AM_PM);
                timePickerDialog=new TimePickerDialog(Maintanance.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay+":"+minute);
                        timePickerDialog.getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar clndr=Calendar.getInstance();
                int day=clndr.get(Calendar.DAY_OF_MONTH);
                int month=clndr.get(Calendar.MONDAY);
                int year=clndr.get(Calendar.YEAR);
                datePickerDialog=new DatePickerDialog(Maintanance.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"-"+month+"-"+year);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Maintanance.this,"Your response has been recorded",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(Maintanance.this,Driver_Home.class));
            }
        });

         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Maintanance.this,Driver_Home.class));
             }
         });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Maintanance.this, Driver_Home.class));
            }
        });
    }
}