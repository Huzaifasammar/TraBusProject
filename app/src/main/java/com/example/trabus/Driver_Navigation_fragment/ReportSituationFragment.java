package com.example.trabus.Driver_Navigation_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.trabus.R;
import com.example.trabus.adapter.PlaceAutosuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;


public class ReportSituationFragment extends Fragment {
    AutoCompleteTextView situation,Busno,location;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_report_situation, container, false);
        situation=v.findViewById(R.id.situation);
        location=v.findViewById(R.id.location_layout);
        location.setAdapter(new PlaceAutosuggestAdapter(ReportSituationFragment.this.getActivity(),R.layout.support_simple_spinner_dropdown_item));
        Busno=v.findViewById(R.id.busno);
        String[]selectGender ={"Students","Road Blockage","Bus Problem","Other"};
        ArrayAdapter genderAdapter =new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,selectGender);
        situation.setAdapter(genderAdapter);
        String[]selectBus ={"Bus No 1","Bus No 2","Bus No 3","Bus No 4","Bus No 10","Bus No 41","Bus No 45","Bus No 59"};
        ArrayAdapter BusAdapter =new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,selectBus);
        Busno.setAdapter(BusAdapter);
        return v;
    }
}