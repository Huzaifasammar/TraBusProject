package com.example.trabus.Driver_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Maintanance extends AppCompatActivity {
    RelativeLayout driversample;
    CircleImageView driverImage;
    ImageView back,calendar,clock;
    String id;
    TextView name,busno,time,date,total;
    EditText oil,brake,synthetic,mobileoil;
    AutoCompleteTextView PetrolPump;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar mcalendar;
    Button submit,cancel;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintanance);
        initialize();
        retrivedata();
        onclick();
        String[]selectpump ={"Pso Pump police Line","Pso pump G11 markaz","Pso Pump I10 2"};
        ArrayAdapter pumpAdapter =new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,selectpump);
        PetrolPump.setAdapter(pumpAdapter);








    }
public void onclick()
{
    clock.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mcalendar=Calendar.getInstance();
            int hour=mcalendar.get(Calendar.HOUR_OF_DAY);
            int minute=mcalendar.get(Calendar.MINUTE);
            int am=mcalendar.get(Calendar.AM_PM);
            timePickerDialog=new TimePickerDialog(Maintanance.this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
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
            int month=clndr.get(Calendar.MONTH);
            int year=clndr.get(Calendar.YEAR);
            datePickerDialog=new DatePickerDialog(Maintanance.this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    int Month=month+1;
                    date.setText(dayOfMonth+"-"+Month+"-"+year);

                }
            },year,month,day);
            datePickerDialog.show();
        }
    });

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(Maintanance.this,"Your response has been recorded",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Maintanance.this,Driver_Home.class));
            finish();
        }
    });

    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Maintanance.this,Driver_Home.class));
            finish();
        }
    });




    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Maintanance.this, Driver_Home.class));
            finish();
        }
    });
}
    private void initialize()
    {
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
        total=findViewById(R.id.tvtotal);
        oil=findViewById(R.id.etoil);
        PetrolPump= findViewById(R.id.petrolpump);
        brake=findViewById(R.id.etbreak);
        synthetic=findViewById(R.id.etsynthetic);
        mobileoil=findViewById(R.id.etmobileoil);
        time=findViewById(R.id.timeselected);
        date=findViewById(R.id.dateselected);
        fAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        driverImage=findViewById(R.id.driver_image_nav);
    }
    public void retrivedata()
    {
        id=fAuth.getUid();
        DatabaseReference reference=database.getReference("User").child("Drivers").child("Profile").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    DriverHelper helper=snapshot.getValue(DriverHelper.class);
                    assert helper != null;
                    String FirstName=helper.getFname();
                    String LastName=helper.getLname();
                    String BusNo=helper.getBusno();
                    String Image=helper.getImageurl();
                    Picasso.get().load(Image).into(driverImage);
                    String Fullname=FirstName+" "+LastName;
                    name.setText(Fullname);
                    busno.setText(BusNo);


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(Maintanance.this, "Network error.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}