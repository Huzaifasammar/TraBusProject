package com.example.trabus.Driver_Navigation_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.trabus.Driver_Home_Activities.Maintanance;
import com.example.trabus.Driver_Home_Activities.Schedule;
import com.example.trabus.MapsActivity;
import com.example.trabus.R;


public class HomeFragment extends Fragment {
    RelativeLayout maintanance,schedule,location;
    View v;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         v= inflater.inflate(R.layout.driver_fragment_home, container, false);
        initialize();
        onclick();
        return v;
    }









    public void onclick()
    {
        maintanance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), Maintanance.class));
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), MapsActivity.class));
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(HomeFragment.this.getActivity(), Schedule.class));
                Intent intent=new Intent(HomeFragment.this.getActivity(),Schedule.class);
                startActivity(intent);

            }
        });
    }
    public void initialize()
    {
        maintanance=v.findViewById(R.id.RL_Maintanace);
        schedule=v.findViewById(R.id.Rl_Scehdule);
        location=v.findViewById(R.id.Rl_strtroute);
    }
}