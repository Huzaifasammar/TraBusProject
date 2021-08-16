package com.example.trabus.Driver_Navigation_fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.Student_Home_Activities.TrackBuses;
import com.example.trabus.adapter.AlarmReciever;
import com.example.trabus.adapter.DriverReminderReciever;

import java.util.Calendar;
import java.util.Objects;


public class DriverReminder extends Fragment implements View.OnClickListener {

    private int  NotificationId=1;
    View view;
    TimePicker timePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reminder, container, false);
        view.findViewById(R.id.btnsetreminder).setOnClickListener(this);
        view.findViewById(R.id.btncancel).setOnClickListener(this);
        timePicker=view.findViewById(R.id.timepicker);
        return view;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DriverReminder.this.getActivity(), DriverReminderReciever.class);
        intent.putExtra("notificationid", NotificationId);

        PendingIntent reminderintent = PendingIntent.getBroadcast(DriverReminder.this.getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);

        switch (v.getId()) {
            case R.id.btnsetreminder:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar starttime = Calendar.getInstance();
                starttime.set(Calendar.HOUR_OF_DAY, hour);
                starttime.set(Calendar.MINUTE, minute);
                starttime.set(Calendar.SECOND, 0);
                long alrmstarttime = starttime.getTimeInMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, alrmstarttime, reminderintent);
                Toast.makeText(getContext(), "Reminder Set", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), Driver_Home.class));
                break;

            case R.id.btncancel:
                alarmManager.cancel(reminderintent);
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                break;


        }
    }
}