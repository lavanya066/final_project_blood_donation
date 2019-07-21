package com.example.blooddonation.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.Interface_package.Endpoint;
import com.example.blooddonation.Models.Model_Registration;
import com.example.blooddonation.Models.Success_messages;
import com.example.blooddonation.R;
import com.example.blooddonation.data.local.DatabaseHelper;
import com.example.blooddonation.data.local.DatabaseHelper_Survey;
import com.example.blooddonation.data.remote.Api;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    Uri uriImage;
    private static final int PICK_IMAGE=1;
    EditText name,age,username,password,phone_number,location,email;
    Spinner blood_group;
    TextView register_values,click_to_map,send,verify_otp;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CircleImageView imageView;
    private String verification_id;
    private FirebaseAuth mAuth;
    EditText otp;
   Double lat,lng;
    final Model_Registration u = new Model_Registration();
    final DatabaseHelper databaseHelper=new DatabaseHelper(this);
    Spinner country;
    private String phone_number_user;
    DatabaseHelper_Survey databaseHelper_survey ;
    Integer age_reg;
    int radioId;
     String name_reg;
    String username_reg;
    String phone_reg;
    String location_reg;
    String gender_reg;
    String password_reg;
    String email_reg;
    String bloodgroup;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE&&resultCode==RESULT_OK){
            uriImage=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriImage);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        databaseHelper_survey = new DatabaseHelper_Survey(this);
        send=findViewById(R.id.send);
        name=(EditText)findViewById(R.id.name);
        age=(EditText)findViewById(R.id.agereg);
        email=(EditText)findViewById(R.id.emailreg);
        username=(EditText)findViewById(R.id.usernamereg);
        password=(EditText)findViewById(R.id.passwordreg);
        phone_number=(EditText)findViewById(R.id.moblilenoreg);
        register_values=(TextView)findViewById(R.id.register_values);
        location=(EditText)findViewById(R.id.location_reg);

        click_to_map=(TextView)findViewById(R.id.click_to_map);
        imageView=findViewById(R.id.circular_image);
        verify_otp=findViewById(R.id.verify_otp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("Image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"select picture"),PICK_IMAGE);
            }
        });


blood_group=findViewById(R.id.blood_group_list_donor);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mAuth=FirebaseAuth.getInstance();
        otp=findViewById(R.id.otp);
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=otp.getText().toString().trim();
                if(code.isEmpty()){
                    otp.setError("can't be left empty");
                    otp.requestFocus();
                    return;
                }
                verifycode(code);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                phone_number_user = phone_number.getText().toString();
                if (phone_number_user.isEmpty() || phone_number_user.length() < 10) {
                    phone_number.setError("number required");
                    phone_number.requestFocus();
                    return;


                }

                    String phonenumber = "+" + "91" + phone_number_user;
                    sendVerificationCode(phonenumber);

            }
        });


        click_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findOnMap(v);
            }
        });




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




    }
    public void findOnMap(View v){
        Geocoder geocoder=new Geocoder(this);
        try {
            List<Address> myList=geocoder.getFromLocationName(location.getText().toString(),1);
            Address address=myList.get(0);
            String locality=address.getSubLocality();
             lat=address.getLatitude();
            lng= address.getLongitude();

            getLocation(locality,lat,lng,20);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private  void getLocation(String locality, final double lat, final double lng, double scope){
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);
        radioGroup=(RadioGroup)findViewById(R.id.radio);
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(radioId);
        register_values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_reg=name.getText().toString();
                age_reg=Integer.parseInt(age.getText().toString());
                username_reg=username.getText().toString();
                password_reg=password.getText().toString();
                phone_reg=phone_number.getText().toString();
                location_reg=location.getText().toString();
                email_reg=email.getText().toString();
                bloodgroup=blood_group.getSelectedItem().toString();
                //gender_reg="male";
               //radioGroup=  findViewById(R.id.radio);
              // int id=radioGroup.getCheckedRadioButtonId();
              // radioButton=findViewById(id);
               gender_reg=radioButton.getText().toString();
                //email_reg=email.getText().toString();
                u.setName(name_reg);
                u.setAg(age_reg);
                u.setEl(email_reg);
                u.setUsr(username_reg);
                u.setPwd(password_reg);
                u.setLocation(location_reg);
                u.setPhonenumber(phone_reg);
                u.setGdr(gender_reg);

                if (databaseHelper.checkUser(email_reg,password_reg)==0) {

                    databaseHelper.insertval(u.getName(),u.getAg(),u.getGdr(),u.getEl(),u.getUsr(),u.getPwd(),u.getPhonenumber(),u.getLocation());
                    Intent intent = new Intent(Registration.this, Survey_test.class);
                    String age = String.valueOf(age_reg);

                    Endpoint apiService =
                            Api.getClient().create(Endpoint.class);

                    Call<Success_messages> call = apiService.setValue(name_reg, age, email_reg, username_reg, password_reg, phone_reg, lat, lng, bloodgroup);
                    call.enqueue(new Callback<Success_messages>() {
                        @Override
                        public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {
                            String a = response.body().getMessage().toString();
                            if (a != null) {

                                Toast.makeText(Registration.this, a, Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Success_messages> call, Throwable t) {
                            Log.d("ERRORS", t.toString());
                            Toast.makeText(Registration.this, "Network Issue", Toast.LENGTH_SHORT).show();
                        }
                    });


                    intent.putExtra("phonenumber", phone_reg);
                    intent.putExtra("age", age_reg);
                    intent.putExtra("gender", gender_reg);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Registration.this, "not possible", Toast.LENGTH_LONG).show();
                }






            }

        });

        String falseup="false";
        databaseHelper_survey.insertvalue(phone_reg, falseup, falseup, falseup);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in "+locality));
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(sydney, (float) scope);
        mMap.moveCamera(update);
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

                    Toast.makeText(Registration.this, "possible", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Registration.this, "not possible", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,40, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallback);
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
            Toast.makeText(Registration.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };


}
