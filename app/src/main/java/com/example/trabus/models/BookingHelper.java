package com.example.trabus.models;

import com.example.trabus.adapter.BookingAdapter;

public class BookingHelper {
    String BusNumber,date;
    public BookingHelper()
    {}
    public BookingHelper(String BusNumber,String date)
    {
        this.BusNumber=BusNumber;
        this.date=date;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBus(String BusNumber) {
        this.BusNumber = BusNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
