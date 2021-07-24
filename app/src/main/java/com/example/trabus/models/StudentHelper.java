package com.example.trabus.models;

public class StudentHelper {
    String fname,lname,username,email,password,imageurl;
    int gender;
    public StudentHelper()
    {

    }
    public StudentHelper(String fname,String lname,String username,String email,String password,int gender,String imageurl)
    {
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.password=password;
        this.username=username;
        this.gender=gender;
        this.imageurl=imageurl;

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
