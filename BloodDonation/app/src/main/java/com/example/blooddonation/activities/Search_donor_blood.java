package com.example.blooddonation.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.Interface_package.Endpoint;
import com.example.blooddonation.Models.Accept;
import com.example.blooddonation.Models.AcceptModel;
import com.example.blooddonation.Models.Locations;
import com.example.blooddonation.Models.Marker_location;
import com.example.blooddonation.R;
import com.example.blooddonation.data.remote.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_donor_blood extends FragmentActivity implements OnMapReadyCallback {
LayoutInflater layoutInflater;
    private GoogleMap mMap;
    LocationManager locationManager;
    TextView submit_blood_group;
    Spinner select_blood_group;
    String blood_group;
    ListView people;
    List<Locations> data= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor_blood);
        submit_blood_group=findViewById(R.id.blood_group_txt);
        select_blood_group=findViewById(R.id.blood_group_spinner);
people=findViewById(R.id.people);


                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_frag);
                mapFragment.getMapAsync(this);





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

}



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10.2f));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat_acceptor = location.getLatitude();
                    double lon_acceptor = location.getLongitude();
                    getLocation(lat_acceptor,lon_acceptor);
                    LatLng latLng=new LatLng(lat_acceptor,lon_acceptor);
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try{

                        List<Address> addressList= geocoder.getFromLocation(lat_acceptor,lon_acceptor,1);
                        String str=addressList.get(0).getLocality()+",";
                        str+=addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,7.2f));
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat_acceptor = location.getLatitude();
                    double lon_acceptor = location.getLongitude();
                    getLocation(lat_acceptor,lon_acceptor);
                    LatLng latLng=new LatLng(lat_acceptor,lon_acceptor);
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try{

                        List<Address> addressList= geocoder.getFromLocation(lat_acceptor,lon_acceptor,1);
                        String str=addressList.get(0).getLocality()+",";
                        str+=addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,7.2f));
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }
    private void getLocation(final double lat, final double lon){
        submit_blood_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        Endpoint apiService =
                Api.getClient().create(Endpoint.class);
        blood_group=select_blood_group.getSelectedItem().toString();
        mMap.clear();
        Call<Marker_location> call = apiService.setAll(blood_group,lat,lon);
        Toast.makeText(Search_donor_blood.this, blood_group, Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<Marker_location>() {
            @Override
            public void onResponse(Call<Marker_location> call, Response<Marker_location> response) {

                List<Locations> data=response.body().getLocations();
               int  a = response.body().getLocations().size();
                Toast.makeText(Search_donor_blood.this,  data.toString(), Toast.LENGTH_LONG).show();
                Search_Adapter requestAdapter = new Search_Adapter(data);
                people.setAdapter(requestAdapter);
                int i;
                double lat2,lon2;
for(i=0;i<a;i++){
    lat2=data.get(i).getLatitude2();
    lon2= data.get(i).getLongitude2();
    Geocoder geocoder=new Geocoder(getApplicationContext());
    LatLng latLng=new LatLng(lat2,lon2);

    Toast.makeText(Search_donor_blood.this,  String.valueOf(lat2), Toast.LENGTH_LONG).show();
    mMap.addMarker(new MarkerOptions().position(latLng).title("here"));
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
}



            }







            @Override
            public void onFailure(Call<Marker_location> call, Throwable t) {
                Log.d("ERRORS", t.toString());
                Toast.makeText(Search_donor_blood.this, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });



    }

class Search_Adapter extends BaseAdapter{
        List<Locations> data;
TextView name,email,phone_number;

    public Search_Adapter(List<Locations> data) {
        this.data=data;
    }



    @Override
    public int getCount() {
        return data.size();
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
        View view=getLayoutInflater().inflate(R.layout.people_list,null);
        name=view.findViewById(R.id.donor_name);
        email=view.findViewById(R.id.donor_email);
        phone_number=view.findViewById(R.id.donor_phonenumber);
        name.setText(data.get(position).getName());
        email.setText(data.get(position).getEmail());
        phone_number.setText(data.get(position).getPhonenumber());

        Endpoint apiService =
                Api.getClient().create(Endpoint.class);
        final String[] no = new String[1];
        Call<AcceptModel> calling =apiService.Inputaccept_send_request(phone_number.getText().toString());
        calling.enqueue(new Callback<AcceptModel>() {
            @Override
            public void onResponse(Call<AcceptModel> call, Response<AcceptModel> response) {
                List<Accept> acceptList=response.body().getAccept();
                if(acceptList==null){
                    no[0] =acceptList.get(0).getPhonenumberUser().toString();
                    if(no[0].equals(phone_number.getText().toString())) {
                        email.setText("the donor is ready to donate blood");
                    }

                }

            }

            @Override
            public void onFailure(Call<AcceptModel> call, Throwable t) {

            }
        });
        return view;
    }
}





}



        //

