package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodModel {

    @SerializedName("blood")
    @Expose
    private List<Blood> blood = null;

    public List<Blood> getBlood() {
        return blood;
    }

    public void setBlood(List<Blood> blood) {
        this.blood = blood;
    }

}
