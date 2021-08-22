package com.example.trabus.models;

import com.example.trabus.adapter.BookingAdapter;

public class BookingHelper {
    String Bus_Number,date;
    public BookingHelper()
    {}
    public BookingHelper(String BusNumber,String date)
    {
        this.Bus_Number=BusNumber;
        this.date=date;
    }

    public String getBusno() {
        return Bus_Number;
    }

    public void setBusno(String busno) {
        this.Bus_Number = busno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
