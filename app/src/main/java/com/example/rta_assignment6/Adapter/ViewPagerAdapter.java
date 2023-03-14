package com.example.rta_assignment6.Adapter;

import android.graphics.Region;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rta_assignment6.Fragment.HomeFragment;
import com.example.rta_assignment6.Fragment.ListFragment;
import com.example.rta_assignment6.Model.RegionInfo;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
private List<RegionInfo> regions;
public ViewPagerAdapter(FragmentManager fm, List<RegionInfo> regions) {
    super(fm);
    this.regions = regions;
}



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new ListFragment();
        } else if (position == 1) {
            fragment = new HomeFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "List";
        } else if (position == 1) {
            title = "Map";
        }
        return title;
    }


}
