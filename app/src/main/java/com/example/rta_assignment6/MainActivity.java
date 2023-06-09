package com.example.rta_assignment6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rta_assignment6.adapter.ViewPagerAdapter;
import com.example.rta_assignment6.dblocal.AppDatabase;
import com.example.rta_assignment6.dblocal.RegionInfoRepository;
import com.example.rta_assignment6.dblocal.ViewRegionLocalModel;
import com.example.rta_assignment6.fragment.HomeFragment;
import com.example.rta_assignment6.fragment.ListFragment;
import com.example.rta_assignment6.model.RegionInfo;
import com.example.rta_assignment6.fetchdata.ViewRegionModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.ObjLongConsumer;

public class MainActivity extends AppCompatActivity implements ListFragment.SendMessage, DataListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public List<RegionInfo> regionList;
    public List<RegionInfo> regionListTemp;
    ViewRegionModel viewRegionModel;
    public int offset = 0;
    ViewRegionLocalModel viewRegionLocalModel;
    RegionInfoRepository resLocal;
    RequestQueue queue;
    private boolean isDataDisplayed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       regionList = new ArrayList<>();
        regionListTemp = new ArrayList<>();
        queue = Volley.newRequestQueue(MainActivity.this);

        viewRegionLocalModel = new ViewModelProvider(this).get(ViewRegionLocalModel.class);
        resLocal = new RegionInfoRepository(getApplication());


        viewPager = (ViewPager) findViewById(R.id.vpAdd);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        viewRegionModel = new ViewRegionModel(MainActivity.this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

        viewPager.post(new Runnable() {
            @Override
            public void run() {

                if (resLocal.getCount() > 0){
                    regionList.clear();
                    loadMoreData(offset);
                }else{
                    loalDataFromAPI(offset);
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void sendData(RegionInfo regionInfo) {
        String tag = "android:switcher:" + R.id.vpAdd + ":" + 1;
        HomeFragment f = (HomeFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(regionInfo);
    }

    @Override
    public void onDataReceived(List<RegionInfo> data) {
        List<RegionInfo> datanew = new ArrayList<>(data);
        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpAdd + ":" + 0);
        HomeFragment fragment2 = (HomeFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpAdd + ":" + 1);
        if (fragment != null && fragment.getView() != null && fragment2 != null && fragment2.getView() != null) {
            fragment.updateData(datanew);
            fragment2.updateData(datanew);
        }

    }




    public void loadMoreData(int offset) {
        viewRegionLocalModel.getPaging(10, offset).observe(MainActivity.this, new Observer<List<RegionInfo>>() {
            @Override
            public void onChanged(List<RegionInfo> regionInfoList) {
                int sizeOld = regionList.size();
                regionList.addAll(regionInfoList);
                int sizeNew = regionList.size();
                if(sizeOld <sizeNew){
                    onDataReceived(regionList);

                }


            }
        });
    }

    public void loalDataFromAPI(int offset) {

        loadMoreDataNO(offset);


    }


    public void loadMoreDataNO(int offset) {
        String url = "https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=10&offset=" + offset;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();
                        Type regionInfoListType = new TypeToken<List<RegionInfo>>(){}.getType();
                        List<RegionInfo> regionInfoListfromGson = gson.fromJson(response, regionInfoListType);
                        Log.d("datagetAPI","size"+regionInfoListfromGson.size()+regionInfoListfromGson.toString());



                        int sizeOld = regionList.size();
                       regionList.addAll(regionInfoListfromGson);
                       regionListTemp.addAll(regionInfoListfromGson);
                        int sizeNew = regionList.size();
                        if(sizeOld <sizeNew){
                            onDataReceived(regionList);

                        }
                        onDataReceived(regionList);
                        regionInfoListfromGson.clear();


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(stringRequest);

    }


    public ViewRegionModel getViewRegionModel() {
        return viewRegionModel;
    }

    public ViewRegionLocalModel getViewRegionLocalModel() {
        return viewRegionLocalModel;
    }

    @Override
    protected void onPause() {
        super.onPause();



    }

    @Override
    protected void onStop() {
        super.onStop();
       resLocal.insertAll(regionListTemp);
        regionListTemp.clear();
    }
}




