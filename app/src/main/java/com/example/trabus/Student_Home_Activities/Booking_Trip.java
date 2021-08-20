package com.example.trabus.Student_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.trabus.models.StudentHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.CollationElementIterator;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Booking_Trip extends AppCompatActivity {
    AutoCompleteTextView spnpickup,spndrop;
    ImageView backtrip,drop1,drop2;
    ArrayAdapter<CharSequence> adapter;
    LinearLayout calender;
    DatePickerDialog datePickerDialog;
    Button check_avaliable;
    String Email,name;
    Double estimatedfare,calcultfare;
    TextView fare;
    RelativeLayout bottom;
    TextView date,busdate,busnumber,bookfare;
    BottomSheetBehavior behavior;
    Double distance;
    Dialog dialog;
    String Number,pickup,drop,pickdate;
    LatLng latLng1,latLng2;
    DatabaseReference reference,reference1;
    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_booking_trip);
        spnpickup=findViewById(R.id.pickup);
        spndrop=findViewById(R.id.drop);
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("User").child("Students").child("Profiles").child(user.getUid());
        reference1=FirebaseDatabase.getInstance().getReference().child("User").child("Students").child("Booking").child("BusNumber 34");
        date=findViewById(R.id.tvdeparturedate);
        busdate=findViewById(R.id.tv_date);
        drop1=findViewById(R.id.drop1);
        fare=findViewById(R.id.fare);
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
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                StudentHelper helper=snapshot.getValue(StudentHelper.class);
                assert helper != null;
                Email=helper.getEmail();
                String fullname=helper.getFname()+" "+helper.getLname();
                name=fullname;

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
        bookfare=dialog.findViewById(R.id.bookfare);
        busnumber=dialog.findViewById(R.id.tv_bus_name);
        Number=getIntent().getStringExtra("busno");
        busnumber.setText(Number);

        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> helper=new HashMap<>();
                pickup=spnpickup.getText().toString();
                drop=spndrop.getText().toString();
                pickdate=date.getText().toString();
                helper.put("Email",Email);
                helper.put("Name",name);
                helper.put("Pickup",pickup);
                helper.put("drop",drop);
                helper.put("date",pickdate);
                reference1.setValue(helper);
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
                        farecalculation();
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        farecalculation();
                    }
                }


            }
        });

        backtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Trip.this, Student_Home.class));
                finish();
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
@SuppressLint("DefaultLocale")
public void farecalculation()
{
    latLng1=new LatLng(33.6844,73.0479);
    if(spndrop.getText().toString().equals("Nathia Gali"))
    {
        latLng2=new LatLng(34.0729,73.3812);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
    else if(spndrop.getText().toString().equals("Muree"))
    {
        latLng2=new LatLng(33.9070,73.3943);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
    else if(spndrop.getText().toString().equals("MuskPuri"))
    {
        latLng2=new LatLng(34.0602,73.4308);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
    else if(spndrop.getText().toString().equals("Lahore"))
    {
        latLng2=new LatLng(31.5204,74.3587);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
    else if(spndrop.getText().toString().equals("Kashmir"))
    {
        latLng2=new LatLng(33.2778,75.3412);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
    else if(spndrop.getText().toString().equals("KalarKahar"))
    {
        latLng2=new LatLng(32.7769,72.7068);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
    else if(spndrop.getText().toString().equals("Karachi"))
    {
        latLng2=new LatLng(24.8607,67.0011);
        distance=calculatedistance(latLng1,latLng2);
        estimatedfare=100.0;
        calcultfare=distance*estimatedfare;
        fare.setText((String.valueOf(String.format("%.0f", calcultfare))));
        String farebook=fare.getText().toString();
        bookfare.setText(farebook);
    }
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
    public Double calculatedistance(LatLng stratP, LatLng EndP)
    {
        int radius=6371; //earth radius in km
        double lat1=stratP.latitude;
        double lat2=EndP.latitude;
        double long1=stratP.longitude;
        double long2=EndP.longitude;
        double distancelat=Math.toRadians(lat2-lat1);
        double distancelon=Math.toRadians(long2-long1);
        double a=Math.sin(distancelat/2)*Math.sin(distancelat/2)+Math.cos(Math.toRadians(lat1))
                *Math.cos(Math.toRadians(lat2))*Math.sin(distancelon/2)*Math.sin(distancelon/2);
        double c=2*Math.asin(Math.sqrt(a));
        double valueresult=radius*c;
        double km=valueresult/1;
        DecimalFormat format=new DecimalFormat("####");
        int kmInDec = Integer.parseInt(format.format(km));
        double meter=valueresult%1000;
        int meterindec=Integer.valueOf(format.format(meter));
        Log.i("Radius Value", "" + valueresult + "   KM  " + kmInDec
                + " Meter   " + meterindec);
        return  radius*c;

    }

}