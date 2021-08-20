package com.example.trabus.models;

public class ScheduleBusNo {
    String busno,first_time,second_time,third_time,fourt_time,fifth_time;
    public ScheduleBusNo()
    {

    }
    public ScheduleBusNo(String busno,String first_time,String second_time,String third_time,String fourt_time,String fifth_time)
    {
        this.busno=busno;
        this.first_time=first_time;
        this.second_time=second_time;
        this.third_time=third_time;
        this.fourt_time=fourt_time;
        this.fifth_time=fifth_time;

    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getSecond_time() {
        return second_time;
    }

    public void setSecond_time(String second_time) {
        this.second_time = second_time;
    }

    public String getThird_time() {
        return third_time;
    }

    public void setThird_time(String third_time) {
        this.third_time = third_time;
    }

    public String getFourt_time() {
        return fourt_time;
    }

    public void setFourt_time(String fourt_time) {
        this.fourt_time = fourt_time;
    }

    public String getFifth_time() {
        return fifth_time;
    }

    public void setFifth_time(String fifth_time) {
        this.fifth_time = fifth_time;
    }
}
