package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptModel {

    @SerializedName("accept")
    @Expose
    private List<Accept> accept = null;

    public List<Accept> getAccept() {
        return accept;
    }

    public void setAccept(List<Accept> accept) {
        this.accept = accept;
    }

}