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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Maintanance extends AppCompatActivity {
    CircleImageView driverImage;
    ImageView back,calendar,clock;
    String Bus;
    TextView time,date,BusNo;
    EditText oil,brake,synthetic,mobileoil,mileagepetrol,litres,other,mileageservices;
    AutoCompleteTextView PetrolPump;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar mcalendar;
    Button submit,cancel;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    FirebaseUser Id;
    DatabaseReference reference,reference1;
    String sTime,sDate,sOil,sBrake,sSynthetic,sMobileoil,sMileagepetrol,sLitres,sOther,sMileageservices,sPetrolpump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintanance);
        initialize();
        getedittextdata();
        onclick();
        String[]selectpump ={"Pso Pump police Line","Pso pump G11 markaz","Pso Pump I10 2"};
        ArrayAdapter pumpAdapter =new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,selectpump);
        PetrolPump.setAdapter(pumpAdapter);








    }
public void onclick()
{
    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            if(snapshot.exists()) {

                DriverHelper helper = snapshot.getValue(DriverHelper.class);
                assert helper != null;
                Bus=helper.getBusno().toString();
                BusNo.setText(Bus);
            }

        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    });
    clock.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mcalendar=Calendar.getInstance();
            int hour=mcalendar.get(Calendar.HOUR_OF_DAY);
            int minute=mcalendar.get(Calendar.MINUTE);
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

                                      getedittextdata();
                                      HashMap<String,String> helper=new HashMap<>();
                                      helper.put("time",sTime);
                                      helper.put("Date",sDate);
                                      helper.put("Diesel",sLitres);
                                      helper.put("petrol_pump",sPetrolpump);
                                      helper.put("Mileage_Diesel",sMileagepetrol);
                                      helper.put("oil_change_price",sOil);
                                      helper.put("synthetic_services_Price",sSynthetic);
                                      helper.put("BrakeServicesPrice",sBrake);
                                      helper.put("Other_services_Price",sOther);
                                      helper.put("Mileage_Services",sMileageservices);
                                      if(sTime.isEmpty()||sDate.isEmpty()||sMileagepetrol.isEmpty()||sLitres.isEmpty())
                                      {
                                          Toast.makeText(Maintanance.this,"Please Fill Require Fields",Toast.LENGTH_LONG).show();

                                      }
                                      else {
                                          reference.child("User").child("Drivers").child("Maintanance").child(Id.getUid()).child(Bus).push().setValue(helper);
                                          Toast.makeText(Maintanance.this, "Your response has been recorded", Toast.LENGTH_LONG).show();
                                          startActivity(new Intent(Maintanance.this, Driver_Home.class));
                                          finish();
                                      }
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
        back=findViewById(R.id.backmaintanance);
        calendar=findViewById(R.id.calendar);
        clock=findViewById(R.id.clock);
        oil=findViewById(R.id.etoil);
        PetrolPump= findViewById(R.id.petrolpump);
        brake=findViewById(R.id.etbreak);
        synthetic=findViewById(R.id.etsynthetic);
        mobileoil=findViewById(R.id.etmobileoil);
        time=findViewById(R.id.timeselected);
        date=findViewById(R.id.dateselected);
        fAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        Id=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference();
        reference1=FirebaseDatabase.getInstance().getReference().child("User").child("Drivers").child("Profile").child(Id.getUid());
        driverImage=findViewById(R.id.driver_image_nav);
        mileagepetrol=findViewById(R.id.petrolmileage);
        litres=findViewById(R.id.totallitres);
        other=findViewById(R.id.etother);
        mileageservices=findViewById(R.id.servicesmileage);
        BusNo=findViewById(R.id.tvbusnomaintanace);
    }
    public void getedittextdata()
    {
        sTime=time.getText().toString();
        sDate=date.getText().toString();
        sLitres=litres.getText().toString();
        sMileagepetrol=mileagepetrol.getText().toString();
        sOil=oil.getText().toString();
        sSynthetic=synthetic.getText().toString();
        sBrake=brake.getText().toString();
        sMobileoil=mobileoil.getText().toString();
        sOther=other.getText().toString();
        sMileageservices=mileageservices.getText().toString();
        sPetrolpump=PetrolPump.getText().toString();
    }

}