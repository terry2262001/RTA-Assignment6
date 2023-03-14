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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.Model.ViewRegionModel;
import com.example.rta_assignment6.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    RegionInfo loadRegion;
    double La, Long;
    RequestQueue queue;
    ArrayList<RegionInfo> regionList;
    Location location;
    String a = "";
    private boolean mIsDataLoaded = false;


    private boolean isDataReceived = false;
    ViewRegionModel viewRegionModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regionList = new ArrayList<>();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        viewRegionModel = new ViewRegionModel(getContext());
        // Create the observer which updates the UI.
        viewRegionModel.getDatafromAPI();
        viewRegionModel.getMyData().observe(this, new Observer<List<RegionInfo>>() {
            @Override
            public void onChanged(List<RegionInfo> regionInfos) {
                for (RegionInfo info : regionInfos) {

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(info.getLatitude()), Double.parseDouble(info.getLongitude())))
                            .title(info.getRegion())
                            .snippet(info.getWhere_coordinates()));
                }

            }
        });



    }


    public void displayReceivedData(RegionInfo region) {
        loadRegion = region;
        La = Double.parseDouble(loadRegion.getLatitude());
        System.out.println(La + "==============>");
        Long = Double.parseDouble(loadRegion.getLongitude());

    }



}
