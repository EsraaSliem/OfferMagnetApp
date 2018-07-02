package com.example.hp.offermagnet;

import java.io.Serializable;

/**
 * Created by ASUS on 29/04/2018.
 */

public class User implements Serializable {
    private String id;
    private String userName;
    private String phone;
    private String birthDate;
    private String gender;
    private String city;
    private String state;
    private String password;


    User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    User(String id) {
        this.id = id;
    }

    User(String id,String birthDate, String gender, String city) {

        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
