package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Survey {

    @SerializedName("register")
    @Expose
    private String register;
    @SerializedName("survey")
    @Expose
    private String survey;
    @SerializedName("survey_test")
    @Expose
    private String surveyTest;

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getSurveyTest() {
        return surveyTest;
    }

    public void setSurveyTest(String surveyTest) {
        this.surveyTest = surveyTest;
    }

}
