package com.example.blooddonation.Interface_package;

import com.example.blooddonation.Models.AcceptModel;
import com.example.blooddonation.Models.AgeModel;
import com.example.blooddonation.Models.BloodModel;
import com.example.blooddonation.Models.CountModel;
import com.example.blooddonation.Models.Example;
import com.example.blooddonation.Models.Marker_location;
import com.example.blooddonation.Models.Request_Model;
import com.example.blooddonation.Models.Success_messages;
import com.example.blooddonation.Models.SurveyModel;
import com.example.blooddonation.Models.Tip;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Endpoint {

    @GET("/blog")
    Call<Example> getitem();
    @GET ("tip")
    Call<Tip> getValue();
    @FormUrlEncoded
    @POST("/location")
    Call<Success_messages>  setValue(@Field("name")String name,
                                     @Field("age")String age,
                                     @Field("email")String email,
                                     @Field("username")String username,
                                     @Field("password")String password,
                                     @Field("phonenumber")String phonenumber,
                                     @Field("latitude2")Double latitude2,
                                     @Field("longitude2")Double longitude2,
                                     @Field("bloodgroup")String bloodgroup);
    @FormUrlEncoded
    @POST("/locations")
    Call<Marker_location>  setAll(@Field("bloodgroup")String bloodgroup,
                                  @Field("latitude1")Double latitude1,
                                  @Field("longitude1")Double longitude1
                                     );
    @FormUrlEncoded
    @POST("/getblood")
    Call<BloodModel>  InputBlood(@Field("phonenumber")String phonenumber);

    @FormUrlEncoded
    @POST("/requests")
    Call<Request_Model>  InputValue(@Field("bloodgroup")String blood);

    @FormUrlEncoded
    @POST("/getage")
    Call<AgeModel>  InputAge(@Field("phonenumber")String phonenumber);
    @FormUrlEncoded
    @POST("/surveytest")
    Call<Success_messages>  InputSurvey(@Field("register")String register,
                                   @Field("survey")String survey,
                                   @Field("survey_test")String survey_test,
                                   @Field("phonenumber")String phonenumber);
    @FormUrlEncoded
    @POST("/surveytests")
    Call<SurveyModel>  InputSurvey_post(
                                   @Field("phonenumber")String phonenumber);
    @FormUrlEncoded
    @POST("/surveytestcount")
    Call<CountModel>  InputSurveycount(
                                   @Field("phonenumber")String phonenumber);
    @FormUrlEncoded
    @POST("/accept_send_request")
    Call<AcceptModel>  Inputaccept_send_request(
            @Field("phonenumber")String phonenumber
    );
    @FormUrlEncoded
    @POST("/accept_request")
    Call<Success_messages>  Inputaccept_request(
            @Field("name")String name,
            @Field("email")String email,
            @Field("phonenumber")String phonenumber,
            @Field("name_user")String name_user,
            @Field("email_user")String email_user,
            @Field("phonenumber_user")String phonenumber_user
            );

    @FormUrlEncoded
    @PUT("/surveytestsupdate")
    Call<Success_messages>  InputSurveyupdate(
            @Field("register")String register,
            @Field("survey")String survey,
            @Field("survey_test")String survey_test,
            @Field("phonenumber")String phonenumber);

}
