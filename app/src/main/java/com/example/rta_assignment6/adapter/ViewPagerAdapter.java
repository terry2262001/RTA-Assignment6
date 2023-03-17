package com.example.rta_assignment6.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rta_assignment6.MainActivity;
import com.example.rta_assignment6.fragment.HomeFragment;
import com.example.rta_assignment6.fragment.ListFragment;
import com.example.rta_assignment6.model.RegionInfo;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    //    public ViewPagerAdapter(FragmentManager fm,OnAdapterCreatedListener listener) {
//        super(fm);
//    }
    private ListFragment listFragment;
    private HomeFragment homeFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
//public ViewPagerAdapter(@NonNull FragmentManager fm) {
//    super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//    listFragment = new ListFragment();
//    homeFragment = new HomeFragment();
//}


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
        } else if (position == 2) {
            title = "Blank";
        }
        return title;
    }


    public interface OnAdapterCreatedListener {
        void onAdapterCreated();
    }
}
