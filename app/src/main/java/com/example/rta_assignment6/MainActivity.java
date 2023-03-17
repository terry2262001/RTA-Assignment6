package com.example.rta_assignment6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Region;
import android.os.Bundle;

import com.example.rta_assignment6.Adapter.ViewPagerAdapter;
import com.example.rta_assignment6.Fragment.HomeFragment;
import com.example.rta_assignment6.Fragment.ListFragment;
import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.Model.ViewRegionModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.SendMessage,DataListener{

TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    List<RegionInfo> regionList;
    ViewRegionModel viewRegionModel;
    int limit=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regionList = new ArrayList<>();

        viewRegionModel = new ViewRegionModel(MainActivity.this);
        viewRegionModel.getDatafromAPI(limit);
        viewRegionModel.getMyData().observe(this, new Observer<List<RegionInfo>>() {
            @Override
            public void onChanged(List<RegionInfo> regionInfos) {
                regionList = regionInfos;
                onDataReceived(regionList);

            }
        });



        viewPager = (ViewPager) findViewById(R.id.vpAdd);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public void sendData(RegionInfo regionInfo) {
        String tag = "android:switcher:" + R.id.vpAdd + ":" + 1;
        HomeFragment f = (HomeFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(regionInfo);
    }

    @Override
    public void onDataReceived(List<RegionInfo> data) {
        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpAdd + ":" + 0);
        HomeFragment fragment2 = (HomeFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpAdd + ":" + 1);
        if (fragment != null) {
            fragment.updateData(data);
           fragment2.updateData(data);
        }

    }
    public ViewRegionModel getViewRegionModel() {
        return viewRegionModel;
    }

}