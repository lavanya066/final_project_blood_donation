package com.example.blooddonation.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blooddonation.Interface_package.Endpoint;
import com.example.blooddonation.Interface_package.fragmentcallback;
import com.example.blooddonation.Models.Blood;
import com.example.blooddonation.Models.BloodModel;
import com.example.blooddonation.Models.Example;
import com.example.blooddonation.Models.Request;
import com.example.blooddonation.Models.Request_Model;
import com.example.blooddonation.Models.Success_messages;
import com.example.blooddonation.Models.Survey;
import com.example.blooddonation.Models.SurveyModel;
import com.example.blooddonation.Models.overall;
import com.example.blooddonation.R;
import com.example.blooddonation.data.remote.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class HomeScreen_inside_Navigation extends Fragment {

    fragmentcallback frag;

    @SuppressLint("ValidFragment")
    public HomeScreen_inside_Navigation(fragmentcallback frm)
    {
        this.frag=frm;
    }



   // Example_Adapters example_adapters;
   ListView mlist;
   // @NonNull
   // @Override
ViewPager viewPager;
ListView listView;
TextView name,email,phonenumber;
String blood_matter;
LinearLayout layout;
    CheckBox accept,deny;
    List<Blood> bloodList1 = new ArrayList<>();
String phone,donorname_accept,donoremail_accept;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        List<overall> data = new ArrayList<>();
        final String[] bloodgroup = new String[1];

        if(getArguments()!=null) {
            donoremail_accept=getArguments().getString("email");
            donorname_accept=getArguments().getString("username");
            phone=getArguments().getString("phonenumber");
        }
        final View view = inflater.inflate(R.layout.activity_home_screen_inside__navigation,container,false);
        viewPager = view.findViewById(R.id.View_pager);
listView=view.findViewById(R.id.request_list);
        accept=view.findViewById(R.id.accept);



        final Endpoint apiService =
                Api.getClient().create(Endpoint.class);
        final Call<BloodModel> call2=apiService.InputBlood(phone.toString());
        call2.enqueue(new Callback<BloodModel>() {
            @Override
            public void onResponse(Call<BloodModel> call, Response<BloodModel> response) {
                bloodList1=response.body().getBlood();
                 bloodgroup[0] =bloodList1.get(0).getBloodgroup();
                 blood_matter=bloodgroup[0];
                Log.e("ERRORRR", bloodgroup[0].toString());
            }

            @Override
            public void onFailure(Call<BloodModel> call, Throwable t) {
                Log.e("ERRORRR",bloodList1.toString());
            }
        });
        Call<Example> call = apiService.getitem();

        call.enqueue(new Callback<Example>() {

            @Override
            @NonNull
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<overall> data=response.body().getData();
                int a=data.size();

                //mlist=view.findViewById(R.id.list_item);
                Example_Adaters example_adapters=new Example_Adaters(data,getContext());
                viewPager.setAdapter(example_adapters);
                //sendVal(data);
              //
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("ERRORRR",t.toString());
                //Toast.makeText(HomeScreen_inside_Navigation.this,"not possible",Toast.LENGTH_LONG).show();
            }

        });
        Call<SurveyModel> surveyModelCall=apiService.InputSurvey_post(phone);
        surveyModelCall.enqueue(new Callback<SurveyModel>() {
            @Override
            public void onResponse(Call<SurveyModel> call, Response<SurveyModel> response) {

                List<Survey> surveyList=response.body().getSurvey();
                    String reg=surveyList.get(0).getRegister();


                    String survey=surveyList.get(0).getSurvey();

                    String survey_test=surveyList.get(0).getSurveyTest();
                    if(reg.equals("true")&&survey.equals("true")&&survey_test.equals("true")){
                        List<Request> requests=new ArrayList<>();
                        Call<Request_Model> call1=apiService.InputValue(bloodList1.get(0).getBloodgroup());

                        call1.enqueue(new Callback<Request_Model>() {
                            @Override
                            public void onResponse(Call<Request_Model> call, Response<Request_Model> response) {

                                List<Request>requests=response.body().getRequest();
//                Log.e("ERRORRR",requests.toString());

                                if (requests!=null) {
                                    RequestAdapter requestAdapter = new RequestAdapter(requests,phone,donoremail_accept,donorname_accept);
                                    listView.setAdapter(requestAdapter);


                                }
                                else{
                                    listView.setVisibility(View.GONE);
                                }


                                // Log.d("ERRORRR",requests.toString());
                /*else{
                    //Log.d("ERRORRR",requests.toString());
                    listView.setVisibility(View.GONE);
                }*/

                            }

                            @Override
                            public void onFailure(Call<Request_Model> call, Throwable t) {
                                Log.e("ERRORRR",t.toString());
                            }
                        });

                    }




            }

            @Override
            public void onFailure(Call<SurveyModel> call, Throwable t) {

            }
        });


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
    class Example_Adaters extends PagerAdapter{
        List<overall> data;
        Context context;
        LayoutInflater layoutInflater;
        Example_Adaters(List<overall> data,Context context){
            this.data=data;
            this.context=context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.image_adapter_layout,container,false);
            ImageView image_url=view.findViewById(R.id.image_url);
            TextView blogdata=view.findViewById(R.id.blog_data);
            TextView blogdate=view.findViewById(R.id.blog_date);
            TextView blogname=view.findViewById(R.id.blog_name);
            blogdata.setText(data.get(position).getBlogdata());
            blogdate.setText(data.get(position).getBlogdate());
            blogname.setText(data.get(position).getBlogname());
            //Toast.makeText(HomeScreen_inside_Navigation.this,data.toString(), Toast.LENGTH_LONG).show();
            Picasso.with(context).load(data.get(position).getImageurl()).into(image_url);
            container.addView(view);
            return view;


            //ImageView imageView=view.findViewById(R.id.image_blog);

            //return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object );
        }
    }
    class RequestAdapter extends BaseAdapter {
        String phone,phonenumber_request,email_request,name_request;
        String email_user;
        String name_user;
        List<Request> requests;
        public RequestAdapter(List<Request> requests,String phone,String email,String name){
            this.requests=requests;
            this.phone=phone;
            this.email_user=email;
            this.name_user=name;
        }

        @Override
        public int getCount() {
            return requests.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view=getLayoutInflater().inflate(R.layout.request_adapter,null);
            email=view.findViewById(R.id.email_address);
            phonenumber=view.findViewById(R.id.phonenumber);
            name=view.findViewById(R.id.name_req);

            layout=view.findViewById(R.id.layout101);
            email.setText(requests.get(position).getEmail());
            phonenumber.setText(requests.get(position).getPhonenumber());
            name.setText(requests.get(position).getName());
            email_request=email.getText().toString();
            phonenumber_request=phonenumber.getText().toString();
            name_request=name.getText().toString();
            deny=view.findViewById(R.id.reject);
            deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(deny.isChecked()){
                        name.setVisibility(View.GONE);
                        email.setVisibility(View.GONE);
                        phonenumber.setVisibility(View.GONE);
                        deny.setVisibility(View.GONE);
                        Endpoint apiService =
                                Api.getClient().create(Endpoint.class);
                        Call<Success_messages> messagesCall=apiService.Inputaccept_request(name_request,email_request,phonenumber_request,name_user,email_user,phone);
                        messagesCall.enqueue(new Callback<Success_messages>() {
                            @Override
                            public void onResponse(Call<Success_messages> call, Response<Success_messages> response) {

                            }

                            @Override
                            public void onFailure(Call<Success_messages> call, Throwable t) {

                            }
                        });
                    }
                }
            });
            return view;
        }
    }



    /*private void sendVal(List<overall> data){
        example_adapters = new Example_Adapters(data);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }*/
}
