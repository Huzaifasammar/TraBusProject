package com.example.trabus.models;

public class HistoryModel {

    String Date,time,Diesel,Mileage_Diesel;

    public HistoryModel(){}

    public HistoryModel(String Date, String time,String Diesel,String Mileage_Diesel) {
        this.Date = Date;
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDiesel() {
        return Diesel;
    }

    public void setDiesel(String diesel) {
        Diesel = diesel;
    }

    public String getMileage_Diesel() {
        return Mileage_Diesel;
    }

    public void setMileage_Diesel(String mileage_Diesel) {
        Mileage_Diesel = mileage_Diesel;
    }
}
