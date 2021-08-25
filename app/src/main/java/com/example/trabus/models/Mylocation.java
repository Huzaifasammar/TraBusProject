package com.example.trabus.models;

public class Mylocation {
    private double longitude;
    private double latitude;
    private double speed;
    public Mylocation(double longitude,double latitude,double speed)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.speed=speed;

    }
    public Mylocation(){}

    public double getSpeed() {
        return speed;
    }

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
