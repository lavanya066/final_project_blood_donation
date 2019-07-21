package com.example.blooddonation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.R;
import com.example.blooddonation.data.local.DatabaseHelper;
import com.example.blooddonation.utilities.SessionManager;
import com.facebook.stetho.Stetho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    String phone_number_user,email_login,password_login;
    EditText useredit,passwordedit,mobile_no_reg,email;
    ImageView person,lock;
    String e,p;
    SessionManager mManager;
    final DatabaseHelper mydb=new DatabaseHelper(this);
    private String verification_id;
    private FirebaseAuth mAuth;
    CoordinatorLayout coordinatorLayout;
    EditText otp;
    TextView login_with_otp,search_for_donor,login_with_credentials,sign_in_login_page,verify,logsubmit;
    LinearLayout linearLayout,linearotp,image_facebook_google;
    public static final String SHARED_PREFS="sharedPrefs";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        mManager = SessionManager.getInstance(getApplicationContext());
        Stetho.initializeWithDefaults(this);
        useredit = (EditText)findViewById(R.id.useredit);
        login_with_otp=(TextView)findViewById(R.id.login_with_otp);
        verify=findViewById(R.id.verify);
        logsubmit=findViewById(R.id.logsubmit);
        passwordedit=(EditText)findViewById(R.id.passwordedit);
        linearLayout=(LinearLayout)findViewById(R.id.linear_layout);
        linearotp=(LinearLayout)findViewById(R.id.linear_layout_otp);
        search_for_donor=(TextView)findViewById(R.id.search_for_donor);
        email=findViewById(R.id.useredit);
        image_facebook_google=(LinearLayout)findViewById(R.id.image_facebook_google);
        login_with_credentials=(TextView)findViewById(R.id.login_with_credentials);
        mobile_no_reg=(EditText)findViewById(R.id.moblilenoreg);

        sign_in_login_page=(TextView)findViewById(R.id.sign_in_login_page);
        if(mManager.getMobileNumber()!=null&&search_for_donor.getVisibility()==View.VISIBLE){

            Intent intent=new Intent(MainActivity.this,HomePage.class);
            intent.putExtra("phonenumber",mManager.getMobileNumber());
            startActivity(intent);
        }

        logsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_login=email.getText().toString();
                password_login=passwordedit.getText().toString();
//                phone_number_user=mobile_no_reg.getText().toString();
                if (mydb.checkUser(email_login, password_login) == 1) {
                    Cursor cursor=mydb.EnterPhoneNumber(email_login,password_login);
                    if(cursor.getCount()!=0){
                        if (cursor.moveToNext()){
                            String number=cursor.getString(0);
                            mManager.setMobileNumber(number);
                           // mManager.setUserId(email_login);
                            Intent intent=new Intent(MainActivity.this,Survey_test.class);
                            intent.putExtra("phonenumber",number);
                            startActivity(intent);
                            saveData();
                            loadData();
                            updateData();
                        }

                    }

                }
            }
        });

        login_with_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearotp.getVisibility()==View.GONE)
                {
                    linearotp.setVisibility(View.VISIBLE);
                }
                if(image_facebook_google.getVisibility()==View.GONE )
                {
                    image_facebook_google.setVisibility(View.VISIBLE);
                }
                if(linearLayout.getVisibility()==View.VISIBLE )
                {
                    linearLayout.setVisibility(View.GONE);
                }
                if(login_with_otp.getVisibility()==View.GONE){
                    login_with_otp.setVisibility(View.VISIBLE);
                }
                mobile_no_reg=findViewById(R.id.phone_edit);
                mAuth= FirebaseAuth.getInstance();
                otp=findViewById(R.id.otp_edit);
                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        phone_number_user = mobile_no_reg.getText().toString();
                        if (mydb.checkPhoneNumber(phone_number_user) == 1) {
                            mManager.setMobileNumber(phone_number_user);
                            if (phone_number_user.isEmpty() || phone_number_user.length() < 10) {
                                mobile_no_reg.setError("number required");
                                mobile_no_reg.requestFocus();
                                return;
                            }
                            String phonenumber = "+" + "91" + phone_number_user;
                            sendVerificationCode(phonenumber);
                        }
                    }
                });

                logsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            String code = otp.getText().toString().trim();
                            if (code.isEmpty() ) {
                                otp.setError("can't be left empty");
                                otp.requestFocus();
                                return;
                            }
                            verifycode(code);

                            saveData();
                        loadData();
                        updateData();
                        }
                });

            }

               // phone_number_string=mobile_no_reg.getText().toString();

           // intent.putExtra("mobilenumber",phone_number_string);


        });
        search_for_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(linearotp.getVisibility()==View.GONE)
                {
                    linearotp.setVisibility(View.VISIBLE);
                }
                if(image_facebook_google.getVisibility()==View.VISIBLE )
                {
                    image_facebook_google.setVisibility(View.GONE);
                }
                if(linearLayout.getVisibility()==View.VISIBLE )
                {
                    linearLayout.setVisibility(View.GONE);
                }
                if(login_with_otp.getVisibility()==View.GONE){
                    login_with_otp.setVisibility(View.VISIBLE);
                }
                if(search_for_donor.getVisibility()==View.VISIBLE){
                    search_for_donor.setVisibility(View.GONE);
                }

                mobile_no_reg=findViewById(R.id.phone_edit);
                mAuth= FirebaseAuth.getInstance();
                otp=findViewById(R.id.otp_edit);


                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        phone_number_user = mobile_no_reg.getText().toString();


                            if (phone_number_user.isEmpty() || phone_number_user.length() < 10) {
                                mobile_no_reg.setError("number required");
                                mobile_no_reg.requestFocus();
                                return;
                            }

                            String phonenumber = "+" + "91" + phone_number_user;
                            sendVerificationCode(phonenumber);
                        }

                });


                logsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String code=otp.getText().toString().trim();
                        if(code.isEmpty()){
                            otp.setError("can't be left empty");
                            otp.requestFocus();
                            return;
                        }
                        verifycode(code);
                        saveData();
                        loadData();
                        updateData();
                    }
                });

            }


        });
        login_with_credentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility()==View.GONE )
                {
                    linearLayout.setVisibility(View.VISIBLE);
                }
                if(linearotp.getVisibility()==View.VISIBLE)
                {
                    linearotp.setVisibility(View.GONE);
                }
                if(login_with_otp.getVisibility()==View.GONE){
                    login_with_otp.setVisibility(View.VISIBLE);
                }
                if(image_facebook_google.getVisibility()==View.GONE )
                {
                    image_facebook_google.setVisibility(View.VISIBLE);
                }
                logsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email_login=email.getText().toString();
                        password_login=passwordedit.getText().toString();
//                phone_number_user=mobile_no_reg.getText().toString();

                        if (mydb.checkUser(email_login, password_login) == 1) {
                            Cursor cursor=mydb.EnterPhoneNumber(email_login,password_login);
                            if(cursor.getCount()!=0){
                                if (cursor.moveToNext()){
                                    String number=cursor.getString(0);
                                    Intent intent=new Intent(MainActivity.this,Survey_test.class);
                                    intent.putExtra("phonenumber",number);
                                    startActivity(intent);
                                    saveData();
                                    loadData();
                                    updateData();
                                }
                            }
                        }

                    }
                });


            }
        });
        sign_in_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }
    private void verifycode(String code){
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verification_id,code);
        signInwithCredential(credential);
    }
    private void signInwithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if(search_for_donor.getVisibility()==View.VISIBLE){
                        Intent intent = new Intent(MainActivity.this, HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mManager.setMobileNumber(phone_number_user);
                        intent.putExtra("phonenumber",phone_number_user);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, Search_donor_blood.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mManager.setMobileNumber(phone_number_user);
                        intent.putExtra("phonenumber",phone_number_user);
                        startActivity(intent);
                    }


                }
                else {
                    Toast.makeText(MainActivity.this, "not possible", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallback);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verification_id=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                otp.setText(code);
                verifycode(code);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };
    public void saveData(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(EMAIL,email_login);
        editor.putString(PASSWORD,password_login);
        editor.apply();
    }
    public  void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        e=sharedPreferences.getString(EMAIL,"");
        p=sharedPreferences.getString(PASSWORD,"");
    }
    public  void updateData(){
        email.setText(e);
        passwordedit.setText(p);
    }

}
