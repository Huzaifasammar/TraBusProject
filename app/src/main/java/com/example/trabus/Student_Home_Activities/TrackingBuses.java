package com.example.trabus.Student_Home_Activities;

import android.location.Location;

import androidx.annotation.NonNull;

public interface TrackingBuses {
    void onLocationChanged(@NonNull Location location);
}
