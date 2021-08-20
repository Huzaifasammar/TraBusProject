package com.example.trabus.models;

import com.example.trabus.adapter.BookingAdapter;

public class BookingHelper {
    String busno,date;
    public BookingHelper()
    {}
    public BookingHelper(String busno,String date)
    {
        this.busno=busno;
        this.date=date;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
