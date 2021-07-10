package com.example.trabus.models;

public class Mylocation {
    private double longitude;
    private double latitude;
    public Mylocation(double longitude,double latitude)
    {
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public Mylocation(){}

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
