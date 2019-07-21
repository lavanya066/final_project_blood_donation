package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("data")
    @Expose
    private List<overall> data = null;

    public List<overall> getData() {
        return data;
    }

    public void setData(List<overall> data) {
        this.data = data;
    }

}
