package com.example.blooddonation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.Interface_package.Endpoint;
import com.example.blooddonation.Models.Age;
import com.example.blooddonation.Models.AgeModel;
import com.example.blooddonation.Models.Count;
import com.example.blooddonation.Models.CountModel;
import com.example.blooddonation.Models.Model_Survey;
import com.example.blooddonation.Models.Success_messages;
import com.example.blooddonation.R;
import com.example.blooddonation.data.local.DatabaseHelper;
import com.example.blooddonation.data.local.DatabaseHelper_Survey;
import com.example.blooddonation.data.remote.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Survey_test extends AppCompatActivity {
    TextView skip, submit;
   String ageLinit;
    RadioGroup group1, group2, group3, group4;
    RadioButton answer1, answer2, answer3, answer4;
    String falseup="false";
    String trueup="true";
     int age;
    String gender;
    Integer count;
    //String age_registration;

    DatabaseHelper_Survey mydb;
    DatabaseHelper databaseHelper;
    final List<Age> ageList1 = new ArrayList<>();
     String phone_number_registration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_for_donation);
        mydb= new DatabaseHelper_Survey(this);
        final Endpoint apiService =
                Api.getClient().create(Endpoint.class);

        phone_number_registration=getIntent().getStringExtra("phonenumber");
        Toast.makeText(Survey_test.this,phone_number_registration.toString(),Toast.LENGTH_LONG).show();


        final Call<AgeModel> call2=apiService.InputAge(phone_number_registration.toString());

        call2.enqueue(new Callback<AgeModel>() {
            @Override
            public void onResponse(Call<AgeModel> call, Response<AgeModel> response) {
                List<Age> ageList1=response.body().getAge();
                ageLinit=ageList1.get(0).getAge().toString();
                Log.e("ERRORRR",gender);
            }

            @Override
            public void onFailure(Call<AgeModel> call, Throwable t) {
                Log.e("ERRORRR",ageList1.toString());
            }
        });
        /*Cursor gender_registration=databaseHelper.getGender_db(phone_number_registration);
        if(gender_registration.getCount()!=0){
            if (gender_registration.moveToNext()){
                gender=gender_registration.getString(0);

            }



        }*/
        gender="male";




        submit = (TextView)findViewById(R.id.submit);

        skip = findViewById(R.id.skip);



        final Model_Survey u = new Model_Survey();
        group1 = findViewById(R.id.group1);
        group2 = findViewById(R.id.group2);
        group3 = findViewById(R.id.group3);
        group4 = findViewById(R.id.group4);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId1 = group1.getCheckedRadioButtonId();
                answer1 = findViewById(radioId1);

                int radioId2 = group2.getCheckedRadioButtonId();
                answer2 = findViewById(radioId2);

                int radioId3 = group3.getCheckedRadioButtonId();
                answer3 = findViewById(radioId3);

                // answer3.getText().toString().equals("Blood donation where specific components of blood are seperated and transfused");

                int radioId4 = group4.getCheckedRadioButtonId();
                answer4 = findViewById(radioId4);

                age=Integer.valueOf(ageLinit);
                test_Survey(age, gender);
                String b=answer2.getText().toString();
                String s=answer4.getText().toString();
                int scrore = 0;
                Log.e("ERRORRR",s);
//       age=Integer.valueOf(ageList1.toString());
                if (age > 15 && (gender.equals("female"))) {
                    if(s.equals("More than  50 kg")&&b.equals("No")){
                        Call<CountModel> countModelCall=apiService.InputSurveycount(phone_number_registration);
                        countModelCall.enqueue(new Callback<CountModel>() {
                            @Override
                            public void onResponse(Call<CountModel> call, Response<CountModel> response) {
                                List<Count> countList=response.body().getCount();
                                count=countList.get(0).getCount();
                            }

                            @Override
                            public void onFailure(Call<CountModel> call, Throwable t) {

                            }
                        });
                        if(count==0){
                            Call<Success_messages>surveyModelCall=apiService.InputSurvey("true","true","true",phone_number_registration);
                            surveyModelCall.enqueue(new Callback<Success_messages>() {
                                @Override
                                public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                   String s = response.body().getMessage();

                                }

                                @Override
                                public void onFailure(Call<Success_messages> call, Throwable t) {
                                    Toast.makeText(Survey_test.this, "error", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else {
                            Call<Success_messages> success_messagesCall=apiService.InputSurveyupdate("true","true","true",phone_number_registration);
                            success_messagesCall.enqueue(new Callback<Success_messages>() {
                                @Override
                                public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                    String o=response.body().getMessage();
                                }

                                @Override
                                public void onFailure(Call<Success_messages> call, Throwable t) {

                                }
                            });
                        }

                        mydb.insertvalue(phone_number_registration, trueup, trueup, trueup);
                    }
                    scrore += 1;
                }
                else if (age>15&&(gender.equals("male"))&&answer2.getText().toString().equals("No")) {
                    if(s.equals("More than  50 kg")||s.equals("Range between 45 kg to 50 kg")){
                        mydb.insertvalue(phone_number_registration, trueup, trueup, trueup);


                        Call<CountModel> countModelCall=apiService.InputSurveycount(phone_number_registration);
                        countModelCall.enqueue(new Callback<CountModel>() {
                            @Override
                            public void onResponse(Call<CountModel> call, Response<CountModel> response) {
                                List<Count> countList=response.body().getCount();
                                count=countList.get(0).getCount();
                                Log.e("ERRORRR",count.toString());
                                if(count==0){
                                    Call<Success_messages>surveyModelCall=apiService.InputSurvey("true","true","true",phone_number_registration);
                                    surveyModelCall.enqueue(new Callback<Success_messages>() {
                                        @Override
                                        public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                            String s = response.body().getMessage();

                                        }

                                        @Override
                                        public void onFailure(Call<Success_messages> call, Throwable t) {
                                            Toast.makeText(Survey_test.this, "error", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                                else {
                                    Call<Success_messages> success_messagesCall=apiService.InputSurveyupdate("true","true","true",phone_number_registration);
                                    success_messagesCall.enqueue(new Callback<Success_messages>() {
                                        @Override
                                        public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                           //String o=response.body().getMessage().toString();
                                            Log.e("ERRORRR",count.toString());
                                        }

                                        @Override
                                        public void onFailure(Call<Success_messages> call, Throwable t) {

                                        }
                                    });
                                }

                            }

                            @Override
                            public void onFailure(Call<CountModel> call, Throwable t) {

                            }
                        });

                    }
                    scrore += 1;
                }
                else {


                    Call<CountModel> countModelCall=apiService.InputSurveycount(phone_number_registration);
                    countModelCall.enqueue(new Callback<CountModel>() {
                        @Override
                        public void onResponse(Call<CountModel> call, Response<CountModel> response) {
                            List<Count> countList=response.body().getCount();
                            count=countList.get(0).getCount();
                            if(count==0){
                                Call<Success_messages>surveyModelCall=apiService.InputSurvey("true","true","true",phone_number_registration);
                                surveyModelCall.enqueue(new Callback<Success_messages>() {
                                    @Override
                                    public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                        String surveys = response.body().getMessage();
                                        Log.e("ERRORRR",count.toString());
                                    }

                                    @Override
                                    public void onFailure(Call<Success_messages> call, Throwable t) {
                                        Toast.makeText(Survey_test.this, "error", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                            else {
                                Call<Success_messages> success_messagesCall=apiService.InputSurveyupdate("true","true","false",phone_number_registration);
                                success_messagesCall.enqueue(new Callback<Success_messages>() {
                                    @Override
                                    public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                        //String o=response.body().getMessage();
                                        Log.e("ERRORRR",count.toString());
                                    }

                                    @Override
                                    public void onFailure(Call<Success_messages> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<CountModel> call, Throwable t) {

                        }
                    });

                    mydb.insertvalue(phone_number_registration, trueup, trueup, falseup);
                }



               /* Call<SurveyModel>surveyModelCall=apiService.InputSurvey("true","true","false",phone_number_registration);
                surveyModelCall.enqueue(new Callback<SurveyModel>() {
                    @Override
                    public void onResponse(Call<SurveyModel> call, Response<SurveyModel> response) {
                        List<Survey> surveys=response.body().getSurvey();

                    }

                    @Override
                    public void onFailure(Call<SurveyModel> call, Throwable t) {
                        Toast.makeText(Survey_test.this,"error",Toast.LENGTH_LONG).show();
                    }
                });*/
                Intent intent = new Intent(Survey_test.this, HomePage.class);
                intent.putExtra("phonenumber",phone_number_registration);
                startActivity(intent);


            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Survey_test.this, HomePage.class);
                intent.putExtra("phonenumber",phone_number_registration);
                startActivity(intent);

                Call<CountModel> countModelCall=apiService.InputSurveycount(phone_number_registration);
                countModelCall.enqueue(new Callback<CountModel>() {
                    @Override
                    public void onResponse(Call<CountModel> call, Response<CountModel> response) {
                        List<Count> countList=response.body().getCount();
                        count=countList.get(0).getCount();
                        if(count==0){
                            Call<Success_messages>surveyModelCall=apiService.InputSurvey("true","true","true",phone_number_registration);
                            surveyModelCall.enqueue(new Callback<Success_messages>() {
                                @Override
                                public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                    String surveys = response.body().getMessage();

                                }

                                @Override
                                public void onFailure(Call<Success_messages> call, Throwable t) {
                                    Toast.makeText(Survey_test.this, "error", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else {
                            Call<Success_messages> success_messagesCall=apiService.InputSurveyupdate("true","false","false",phone_number_registration);
                            success_messagesCall.enqueue(new Callback<Success_messages>() {
                                @Override
                                public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                                    //String o=response.body().getMessage();
                                }

                                @Override
                                public void onFailure(Call<Success_messages> call, Throwable t) {

                                }
                            });
                        }
                        Log.e("ERRORRR",count.toString());
                    }

                    @Override
                    public void onFailure(Call<CountModel> call, Throwable t) {

                    }
                });

                mydb.insertvalue(phone_number_registration, "false", "true", "false");
            }
        });
    }

    private void test_Survey(int age,String gender_registration) {
        Endpoint apiService =
                Api.getClient().create(Endpoint.class);


}}
