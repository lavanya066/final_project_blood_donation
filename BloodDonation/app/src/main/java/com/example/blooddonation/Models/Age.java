package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Age {

    @SerializedName("age")
    @Expose
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
