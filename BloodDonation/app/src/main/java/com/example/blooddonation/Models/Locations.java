package com.example.blooddonation.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locations {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("latitude2")
    @Expose
    private Double latitude2;
    @SerializedName("longitude2")
    @Expose
    private Double longitude2;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(Double latitude2) {
        this.latitude2 = latitude2;
    }

    public Double getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(Double longitude2) {
        this.longitude2 = longitude2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

}