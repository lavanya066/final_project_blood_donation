package com.example.blooddonation.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.blooddonation.R;
import com.example.blooddonation.data.local.DatabaseHelper;
import com.example.blooddonation.data.local.DatabaseHelper_Survey;
import com.example.blooddonation.Interface_package.fragmentcallback;

@SuppressLint("ValidFragment")
public class Profile_page extends Fragment {
    fragmentcallback frag;
   Switch switch1;
   EditText username_profile,password_profile,email_profile,number_profile,age_profile;
   RadioGroup radioGroup;
   RadioButton radioButton;

   String gender,email,username,password,mobilenumber,survey,survey_test,registration,age;
DatabaseHelper_Survey databaseHelper_survey;
    @SuppressLint("ValidFragment")
    public Profile_page(fragmentcallback frm)
    {
        this.frag=frm;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.profile_page,container,false);
        switch1=view.findViewById(R.id.switch_donor);
        username_profile=view.findViewById(R.id.userupdate_profile);
        password_profile=view.findViewById(R.id.oldpassword_profile);
        email_profile=view.findViewById(R.id.emailupdate);

        number_profile=view.findViewById(R.id.mobile_number_profile);
databaseHelper_survey=new DatabaseHelper_Survey(getContext()    );
        age_profile=view.findViewById(R.id.age_profile);
String age_values;

                DatabaseHelper mydb=new DatabaseHelper(view.getContext());
        if(getArguments()!=null) {
            age = getArguments().getString("age");
            gender=getArguments().getString("gender");
            password=getArguments().getString("old_password");
            email=getArguments().getString("email");
            mobilenumber=getArguments().getString("phonenumber");
            username=getArguments().getString("username");

String age_value=String.valueOf(age);
            username_profile.setText(username);
            password_profile.setText(password);
            age_profile.setText(String.valueOf(age));
            //radioGroup=view.findViewById(R.id.radiogroup_profile);
            //int radioId=radioGroup.getCheckedRadioButtonId();
            //radioButton=view.findViewById(radioId);
            //radioButton.setText("female");
            email_profile.setText(email);
            number_profile.setText(mobilenumber);
            Cursor survey_registration=databaseHelper_survey.checkSurvey(mobilenumber);
            if(survey_registration.getCount()!=0){
                survey_registration.moveToFirst();
                if (survey_registration.moveToNext()){
                   survey =survey_registration.getString(0);

                }



            }
            Cursor survey_test_registration=databaseHelper_survey.SendSurvey_test(mobilenumber);
            if(survey_test_registration.getCount()!=0){
                survey_registration.moveToFirst();
                if (survey_test_registration.moveToNext()){
                    survey_test =survey_test_registration.getString(0);

                }



            }
            Cursor registration_cursor=databaseHelper_survey.SendRegistration(mobilenumber);
            if(registration_cursor.getCount()!=0){
                registration_cursor.moveToFirst();
                if (registration_cursor.moveToNext()){
                    registration =registration_cursor.getString(0);

                }



            }



          //   if(registration.equals("true")&&survey.equals("true")&&survey_test.equals("true"))
            //{
              //  switch1.setChecked(true);
            //}
        }



/*
        b2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                text2.setText("");

            }
        });
        text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                frameCallback.success(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
*/
        frag.success();
        return view;

    }
}
