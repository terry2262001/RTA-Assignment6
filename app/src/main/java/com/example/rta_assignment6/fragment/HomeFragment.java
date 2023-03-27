package com.example.rta_assignment6.fragment;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.rta_assignment6.DataListener;
import com.example.rta_assignment6.model.MyItem;
import com.example.rta_assignment6.model.RegionInfo;
import com.example.rta_assignment6.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

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
    private boolean isDataReady = false; //


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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        setUpClusterer(clusterManager, mMap);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Toast.makeText(getContext(), "tho123123", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }




    public void displayReceivedData(RegionInfo region) {
        System.out.print(region.toString());

    }


    public void updateData(List<RegionInfo> newdata) {

        regionList.clear();
        if (clusterManager != null){
            clusterManager.clearItems();
        }

        regionList.addAll(newdata);
        System.out.println("->>>>>>>>>>----"+regionList.size());
        addItems(mMap);
        isDataReady = true;



    }


    private void setUpClusterer(ClusterManager<MyItem> clusterManager, GoogleMap map) {

        addItems(map);
    }

        private void addItems( GoogleMap map) {

            LatLngBounds.Builder builder = new LatLngBounds.Builder();


            for (RegionInfo info : regionList){

                MyItem offsetItem = new MyItem(Double.parseDouble(info.getLatitude()),Double.parseDouble( info.getLongitude()),info);

                clusterManager.addItem(offsetItem);
                builder.include(offsetItem.getPosition());
            }
            clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
                @Override
                public boolean onClusterItemClick(MyItem item) {
                   showMarkerDetail(item);
                    return false;
                }
            });
           // mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));
            map.setOnCameraIdleListener(clusterManager);
            map.setOnMarkerClickListener(clusterManager);

            if (!isDataReady) {
                return;
            }
            LatLngBounds bounds = builder.build();
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));







        }
    private void showMarkerDetail(MyItem item) {
        // Tạo Dialog để hiển thị thông tin chi tiết của marker
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.item_infor);
        TextView tvId,tvConflict_name,tvRegion,tvCountry,tvWhere_coordinates,tvsource_article ;

        tvId = dialog.findViewById(R.id.tvId);
        tvConflict_name = dialog.findViewById(R.id.tvConflict_name);
        tvCountry = dialog.findViewById(R.id.tvCountry);
        tvsource_article = dialog.findViewById(R.id.tvsource_article);
        tvRegion = dialog.findViewById(R.id.tvRegion);
        tvWhere_coordinates = dialog.findViewById(R.id.tvWhere_coordinates);

        tvId.setText("ID: "+item.getRegionInfo().getId());
        tvConflict_name.setText(item.getRegionInfo().getConflict_name());
        tvCountry.setText("Country: "+item.getRegionInfo().getCountry());
        tvsource_article.setText(item.getRegionInfo().getSource_article());
        tvRegion.setText(item.getRegionInfo().getRegion());
        tvWhere_coordinates.setText(item.getRegionInfo().getWhere_coordinates());




        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }


    }



