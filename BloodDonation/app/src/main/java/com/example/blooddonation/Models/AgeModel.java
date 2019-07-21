package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AgeModel {

    @SerializedName("age")
    @Expose
    private List<Age> age = null;

    public List<Age> getAge() {
        return age;
    }

    public void setAge(List<Age> age) {
        this.age = age;
    }

}