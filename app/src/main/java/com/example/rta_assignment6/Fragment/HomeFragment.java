package com.example.rta_assignment6.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Region;
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
import android.util.Log;
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
import com.example.rta_assignment6.DataListener;
import com.example.rta_assignment6.Model.MyItem;
import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    RegionInfo loadRegion;
    double La, Long;
    RequestQueue queue;
    ArrayList<RegionInfo> regionList;
    DataListener dataListener;
    private ClusterManager<MyItem> clusterManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regionList = new ArrayList<>();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof DataListener) {
            dataListener = (DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataListener = null;

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
        clusterManager = new ClusterManager<MyItem>(getContext(), mMap);
        setUpClusterer(clusterManager, mMap);

    }




    public void displayReceivedData(RegionInfo region) {
        loadRegion = region;
        La = Double.parseDouble(loadRegion.getLatitude());

        Long = Double.parseDouble(loadRegion.getLongitude());

    }


    public void updateData(List<RegionInfo> newdata) {

        regionList.clear();
        clusterManager.clearItems();
        regionList.addAll(newdata);
        System.out.println("->>>>>>>>>>home----"+regionList.size());

        addItems(mMap);


    }

    private void setUpClusterer(ClusterManager<MyItem> clusterManager, GoogleMap map) {

//       map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.21966,71.19155 ),1));
//
//
//        map.setOnCameraIdleListener(clusterManager);
//        map.setOnMarkerClickListener(clusterManager);

        addItems(map);
    }

    private void addItems( GoogleMap map) {
        for (RegionInfo info : regionList){

            MyItem offsetItem = new MyItem(Double.parseDouble(info.getLatitude()),Double.parseDouble( info.getLongitude()), info.getRegion(), info.getWhere_coordinates());
            clusterManager.addItem(offsetItem);
            if (regionList.size() > 0) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(offsetItem.getPosition(), 10));
            } else {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.21966, 71.19155), 1));
            }


            map.setOnCameraIdleListener(clusterManager);
            map.setOnMarkerClickListener(clusterManager);

        }





    }


}

