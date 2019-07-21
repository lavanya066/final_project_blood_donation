package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurveyModel {

    @SerializedName("survey")
    @Expose
    private List<Survey> survey = null;

    public List<Survey> getSurvey() {
        return survey;
    }

    public void setSurvey(List<Survey> survey) {
        this.survey = survey;
    }

}
