package com.example.rta_assignment6.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.example.rta_assignment6.Adapter.RegionInfoAdapter;
import com.example.rta_assignment6.MainActivity;
import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.Model.ViewRegionModel;
import com.example.rta_assignment6.R;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment  implements RegionInfoAdapter.onItemClickListener{



    Button button;
    RequestQueue queue;
    ArrayList<RegionInfo> regionList ;
    RecyclerView rvList;
    RegionInfoAdapter regionInfoAdapter;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

     ViewPager viewPager ;
     TabLayout tabLayout ;
    SendMessage SM;
//    private OnListItemClickListener listener;
private ViewRegionModel viewRegionModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         viewRegionModel = new ViewRegionModel(getContext());
        // Create the observer which updates the UI.
        viewRegionModel.getDatafromAPI();
        viewRegionModel.getMyData().observe(this, new Observer<List<RegionInfo>>() {
            @Override
            public void onChanged(List<RegionInfo> regionInfos) {
                regionList.addAll(regionInfos);
                regionInfoAdapter.notifyDataSetChanged();
            }
        });






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        rvList = view.findViewById(R.id.rvList);


        MainActivity mainActivity = (MainActivity) getActivity();

        viewPager = mainActivity.findViewById(R.id.vpAdd);
        tabLayout = mainActivity.findViewById(R.id.tabLayout);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        regionList =  new ArrayList<>();

        regionInfoAdapter = new RegionInfoAdapter(getContext(), regionList,this);
        rvList.setAdapter(regionInfoAdapter);


        return view ;
    }

    @Override
    public void OnItemClick(RegionInfo region, int pos) {
        SM.sendData(region);

        viewPager.setCurrentItem(1);



    }

public interface SendMessage {
    void sendData(RegionInfo regionInfo);
}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }

    }
}