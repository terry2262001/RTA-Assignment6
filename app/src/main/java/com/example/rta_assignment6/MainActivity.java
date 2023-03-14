package com.example.rta_assignment6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.rta_assignment6.Adapter.ViewPagerAdapter;
import com.example.rta_assignment6.Fragment.HomeFragment;
import com.example.rta_assignment6.Fragment.ListFragment;
import com.example.rta_assignment6.Model.RegionInfo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListFragment.SendMessage {

TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}