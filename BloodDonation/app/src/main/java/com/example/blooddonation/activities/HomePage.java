package com.example.blooddonation.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.blooddonation.Interface_package.fragmentcallback;
import com.example.blooddonation.R;
import com.example.blooddonation.data.local.DatabaseHelper;
import com.example.blooddonation.data.local.DatabaseHelper_Survey;
import com.example.blooddonation.fragments.HomeScreen_inside_Navigation;
import com.example.blooddonation.fragments.Profile_page;
import com.example.blooddonation.utilities.SessionManager;
import com.facebook.stetho.Stetho;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, fragmentcallback {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment fragment;
    FragmentManager fragmentManager;
    SessionManager sessionManager;
    Button log_out;
    CoordinatorLayout coordinatorLayout;
    String gender,answer,email,username,password,age;
   // Integer age;
    String result;
    String phone_number_login;
    DatabaseHelper mydb;
ImageView glass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = SessionManager.getInstance(getApplicationContext());
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.homepage);
        DatabaseHelper_Survey databaseHelper_survey=new DatabaseHelper_Survey(this);
       mydb=new DatabaseHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        glass=findViewById(R.id.search_donor_glass);
        drawerLayout = findViewById(R.id.Drawer_layout);
        fragmentManager = getSupportFragmentManager();
        navigationView = findViewById(R.id.Navigation_Drawer);
        log_out=findViewById(R.id.log_out);
        navigationView.setNavigationItemSelectedListener(this);
        phone_number_login=getIntent().getStringExtra("phonenumber");
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logOut(phone_number_login);
                Intent intent=new Intent(HomePage.this,MainActivity.class);
                startActivity(intent);
            }
        });
 Cursor gender_registration=mydb.getGender_db(phone_number_login);
        if(gender_registration.getCount()!=0){
           // gender_registration.moveToFirst();
            if (gender_registration.moveToNext()){
                gender=gender_registration.getString(0);


            }



        }
        Cursor email_registration=mydb.getEmail_db(phone_number_login);
        if(email_registration.getCount()!=0){
            //email_registration.moveToFirst();
            if (email_registration.moveToNext()){
                email=email_registration.getString(0);


            }



        }

        Cursor user_registration=mydb.getUsername_db(phone_number_login);
        if(user_registration.getCount()!=0){
            //user_registration.moveToFirst();
            if (user_registration.moveToNext()){
                username=user_registration.getString(0);


            }



        }

        Cursor age_registration=mydb.getAge_db(phone_number_login);
        if(age_registration.getCount()!=0){
           // age_registration.moveToFirst();

            if (age_registration.moveToNext()){
                Log.d("TAGG",age_registration.getColumnName(0));
                age=age_registration.getString(0);
              //  Toast.makeText(HomePanger.this,age.toString(),Toast.LENGTH_LONG).show();

            }



        }


        Cursor password_registration=mydb.getPassword_db(phone_number_login);
        if(password_registration.getCount()!=0){
           // password_registration.moveToFirst();
            if (password_registration.moveToNext()){
                password=password_registration.getString(0);

            }



        }

        answer=getIntent().getStringExtra("answer");
glass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(HomePage.this,Search_donor_blood.class);
        startActivity(intent);
    }
});

        //result=databaseHelper_survey.checkSurvey(phone_number_login);
        /*if(result.equals("false")){
            coordinatorLayout=findViewById(R.id.coordinatorLayout);
            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Complete your survey",Snackbar.LENGTH_LONG).setAction("Survey", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(HomePage.this,Survey_test.class);
                    startActivity(intent);
                }
            });
            snackbar.show();
        }*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Toast.makeText(HomePage.this,phone_number_login.toString(), Toast.LENGTH_LONG).show();
        Bundle bundle=new Bundle();
        bundle.putString("phonenumber",phone_number_login);
        bundle.putString("email",email);
        bundle.putString("name",username);
            fragment = new HomeScreen_inside_Navigation(this);
            fragmentManager.beginTransaction().replace(R.id.frame_layout_container, fragment).commit();

        navigationView.setNavigationItemSelectedListener(this);
        fragment.setArguments(bundle);
        //gender=getIntent().getStringExtra("gender");
       // answer=getIntent().getStringExtra("answer");
       // age=getIntent().getIntExtra("age",0);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.Drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.Profile) {
if(!(fragment instanceof Profile_page)){
    Bundle bundle=new Bundle();
    bundle.putString("age",age);
    bundle.putString("gender",gender);
    bundle.putString("phonenumber",phone_number_login);
    bundle.putString("email",email);
    bundle.putString("username",username);
    bundle.putString("old_password",password);

    fragment = new Profile_page(this);

    fragmentManager.beginTransaction().replace(R.id.frame_layout_container, fragment).commit();
    fragment.setArguments(bundle);


    return true;

}

            // Handle the camera action
        }
        else  if (id == R.id.home) {

            if (!(fragment instanceof HomeScreen_inside_Navigation)) {
                Bundle bundle=new Bundle();
                bundle.putString("phonenumber",phone_number_login);
                fragment = new HomeScreen_inside_Navigation(this);

                fragmentManager.beginTransaction().replace(R.id.frame_layout_container, fragment).commit();
                fragment.setArguments(bundle);
            }

            return true;
            // Handle the camera action
        }
        else  if (id == R.id.Survey_updatee) {

            Intent a=new Intent(HomePage.this,Survey_test.class);
            a.putExtra("phonenumber",phone_number_login);
            startActivity(a);

            return true;
            // Handle the camera action
        }


        DrawerLayout drawer = findViewById(R.id.Drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void success() {

    }
}
