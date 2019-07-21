package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accept {

    @SerializedName("phonenumber_user")
    @Expose
    private String phonenumberUser;

    public String getPhonenumberUser() {
        return phonenumberUser;
    }

    public void setPhonenumberUser(String phonenumberUser) {
        this.phonenumberUser = phonenumberUser;
    }

}