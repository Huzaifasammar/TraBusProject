package com.example.trabus.Driver_Home_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.trabus.Login.SignIn;
import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.adapter.ScheduledAdapter;
import com.example.trabus.models.ScheduleBusNo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ScheduledAdapter scheduledAdapter;
    DatabaseReference reference,dbreference,filterreference;
    FirebaseUser Currentuser;
    EditText search;
    ArrayList<ScheduleBusNo>list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_schedule);
        scheduledAdapter=new ScheduledAdapter(list,Schedule.this);
        recyclerView=findViewById(R.id.Scheduledrecyclerview);
        recyclerView.setAdapter(scheduledAdapter);
        search=findViewById(R.id.schedulesearch);
        Currentuser= FirebaseAuth.getInstance().getCurrentUser();
        dbreference=FirebaseDatabase.getInstance().getReference();
        reference=FirebaseDatabase.getInstance().getReference();
        filterreference=FirebaseDatabase.getInstance().getReference();
        Query query =reference.child("User").child("Schedule").orderByChild("bus_number");
        linearLayoutManager=new LinearLayoutManager(Schedule.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        back=findViewById(R.id.backschedule);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcurrentuser();
            }
        });
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
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        ScheduleBusNo  scheduleBusNo=ds.getValue(ScheduleBusNo.class);
                        list.add(scheduleBusNo);
                    }
                    scheduledAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void  filter (String text)
    {
        ArrayList<ScheduleBusNo> filtereddata=new ArrayList<>();
        ScheduledAdapter adapter= new ScheduledAdapter(list, Schedule.this);
        recyclerView.setAdapter(scheduledAdapter);
        filterreference.child("User").child("Schedule").orderByChild("bus_number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ScheduleBusNo helper=dataSnapshot.getValue(ScheduleBusNo.class);
                    assert helper != null;
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
    public void checkcurrentuser() {
        if (Currentuser != null) {

            dbreference.child("User").child("Students").child("Profiles").child(Currentuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        startActivity(new Intent(Schedule.this, Student_Home.class));
                    } else {
                        startActivity(new Intent(Schedule.this, Driver_Home.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        else
        {
            startActivity(new Intent(Schedule.this, SignIn.class));
            finish();
        }
    }
}