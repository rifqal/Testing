package com.example.testing;

import java.io.Serializable;

public class User implements Serializable {

    String fullname, pwd, address, email, birthdate, gender;

    public User(String fullname, String pwd, String address, String email, String birthdate, String gender)
    {
        this.fullname=fullname;
        this.pwd=pwd;
        this.address=address;
        this.email=email;
        this.birthdate=birthdate;
        this.gender=gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
