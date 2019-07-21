package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blood {

    @SerializedName("bloodgroup")
    @Expose
    private String bloodgroup;

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

}