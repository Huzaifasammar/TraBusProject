package com.example.trabus.Driver_Navigation_fragment;

import android.content.Intent;
import android.location.Location;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;
import com.example.trabus.adapter.PlaceAutosuggestAdapter;
import com.example.trabus.models.DriverHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.HashMap;


public class ReportSituationFragment extends Fragment {
    AutoCompleteTextView situation,location;
    EditText description;
    View v;
    TextView BusNo;
    String Location,Situation,Description,BusNumber;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference reference,reference1;
    Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v =inflater.inflate(R.layout.fragment_report_situation, container, false);
        Initialization();
        getdata();
        OnClick();
        String[]selectGender ={"Students","Road Blockage","Bus Problem","Other"};
        ArrayAdapter genderAdapter =new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,selectGender);
        situation.setAdapter(genderAdapter);
        return v;
    }
    public void Initialization()
    {
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference();
        reference1=FirebaseDatabase.getInstance().getReference().child("User").child("Drivers").child("Profile").child(user.getUid());
        situation=v.findViewById(R.id.situation);
        location=v.findViewById(R.id.location_layout);
        description=v.findViewById(R.id.etdescription);
        location.setAdapter(new PlaceAutosuggestAdapter(ReportSituationFragment.this.getActivity(),R.layout.support_simple_spinner_dropdown_item));
        btn=v.findViewById(R.id.btnreportsituation);
        BusNo=v.findViewById(R.id.bus_number);
    }
    public void getdata()
    {
        Location=location.getText().toString();
        Situation=situation.getText().toString();
        Description=description.getText().toString();
    }
    public void OnClick()
    {

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    DriverHelper helper = snapshot.getValue(DriverHelper.class);
                    assert helper != null;
                    BusNumber = helper.getBusno().toString();
                    BusNo.setText(BusNumber);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                HashMap<String,String> helper=new HashMap<>();
                helper.put("Location",Location);
                helper.put("Situation",Situation);
                helper.put("Description",Description);
                if(Location.isEmpty()||Situation.isEmpty()||Description.isEmpty())
                {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    reference.child("User").child("Drivers").child("ReportSituation").child(user.getUid()).child(BusNumber).setValue(helper);
                    Toast.makeText(getContext(), "Reported Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ReportSituationFragment.this.getActivity(), Driver_Home.class));
                }
            }
        });

    }
}