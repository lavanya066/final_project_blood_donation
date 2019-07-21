package com.example.blooddonation.Models;

public class Model_Survey  {
    public static final String create = "survey_test";

    public static final String phonenumber = "phonenumber";
    public static final String result = "survey_result";
    public static final String registration = "registration";
    public static final String survey_test_result = "survey_test_result";
    public static final String Create_Table = "Create Table " +create + "( "+


            phonenumber + " number," +
            registration + " number," +
            result + " text," +
            survey_test_result + " text" +
            ")";
    private int id1;
    private String phone_number;
    private String registration_enter;

    private String survey_result_true_or_false;
    private String survey_test_result1;
    public Model_Survey() {

    }
    public Model_Survey( String phno,String registration_enter,String survey_result_true_or_false, String survey_test_result) {

        this.phone_number=phno;
this.registration_enter=registration_enter;
        this.survey_result_true_or_false=survey_result_true_or_false;
this.survey_test_result1=survey_test_result;
    }
    public String getRegistration(){return registration_enter;}
    public String getPhone_number(){
        return phone_number;
    }
    public String getSurvey_result(){
        return survey_result_true_or_false;
    }
    public String getSurvey_test_result(){
        return survey_test_result1;
    }
    public void setRegistration(String registration_enter){
        this.registration_enter=registration_enter;
    }
    public void setPhone_number(String phno){
        this.phone_number=phno;
    }
    public void setSurvey_result(String survey_result_true_or_false){
        this.survey_result_true_or_false=survey_result_true_or_false;
    }
    public void setSurvey_test_result(String survey_test_result){
        this.survey_test_result1=survey_test_result;
    }

}
