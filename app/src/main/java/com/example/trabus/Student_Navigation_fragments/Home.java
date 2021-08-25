package com.example.trabus.Student_Navigation_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.trabus.Driver_Home_Activities.Schedule;
import com.example.trabus.R;
import com.example.trabus.Student_Home_Activities.Booking_Trip;
import com.example.trabus.Student_Home_Activities.Live_Tracking;


public class Home extends Fragment {
   RelativeLayout buses_schedule,booking,live_tracking;
   View v;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         v=inflater.inflate(R.layout.student_fragment_home, container, false);
         intilize();
         onclick();
        return v;
    }





//onClick Lixteners ----------------------------------------------------

    public void onclick(){
        buses_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Home.this.getActivity(), Buses_Schedule.class));
                Intent intent=new Intent(Home.this.getActivity(), Schedule.class);
                startActivity(intent);
            }
        });
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Home.this.getActivity(), Booking_Trip.class));
                Intent intent=new Intent(Home.this.getActivity(),Booking_Trip.class);
                startActivity(intent);
            }
        });
        live_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this.getActivity(), Live_Tracking.class);
                startActivity(intent);
            }
        });


    }
    //initiazlize all fields ------------------------------------------------------------------

    public void intilize(){
        buses_schedule=v.findViewById(R.id.Rl_bus_schedule);
        booking=v.findViewById(R.id.Rl_booking);
        live_tracking=v.findViewById(R.id.Rl_livetracking);
    }
}