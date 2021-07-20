package com.example.trabus.models;

public class DriverHelper {
    String fname,lname,username,email,password,imageurl,phonenumber,busno,id;
    public DriverHelper(String id,String fname,String lname,String username,String email,String password,String busno,String phonenumber,String imageurl)
    {
        this.busno=busno;
        this.email=email;
        this.fname=fname;
        this.imageurl=imageurl;
        this.lname=lname;
        this.username=username;
        this.phonenumber=phonenumber;
        this.password=password;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }
}
