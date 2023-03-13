package com.example.rta_assignment6.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;


public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener {
     GoogleMap mMap;
    SupportMapFragment mapFragment;
    RegionInfo  loadRegion ;
    double La,Long;

    Location location;
    String a ="";


    private boolean isDataReceived = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        System.out.println("===================>"+Long);


        return view;

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng stu = new LatLng(Double.parseDouble(loadRegion.getLatitude()), Double.parseDouble(loadRegion.getLongitude()));
//        mMap.addMarker(new MarkerOptions()
//                .position(stu)
//                .title(loadRegion.getRegion())
//                .snippet(loadRegion.getWhere_coordinates())
//        );
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stu, 18));



    }


    public void displayReceivedData(RegionInfo region)
    {
        loadRegion = region;
        La = Double.parseDouble(loadRegion.getLatitude());
        System.out.println(La+"==============>");
        Long = Double.parseDouble(loadRegion.getLongitude());

    }







}
