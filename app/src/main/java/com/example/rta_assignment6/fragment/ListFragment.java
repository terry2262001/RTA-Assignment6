package com.example.rta_assignment6.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rta_assignment6.adapter.RegionInfoAdapter;
import com.example.rta_assignment6.DataListener;
import com.example.rta_assignment6.MainActivity;
import com.example.rta_assignment6.dblocal.AppDatabase;
import com.example.rta_assignment6.dblocal.RegionInfoRepository;
import com.example.rta_assignment6.dblocal.ViewRegionLocalModel;
import com.example.rta_assignment6.model.RegionInfo;
import com.example.rta_assignment6.R;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements RegionInfoAdapter.onItemClickListener {


    //List<RegionInfo> regionList;
    private List<RegionInfo> regionList = new ArrayList<>();
    List<RegionInfo> regionChildList;


    RecyclerView rvList;
    RegionInfoAdapter regionInfoAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;
    SendMessage SM;
    int offset = 0;
    DataListener dataListener;
    private ProgressBar progressBar;
    ViewRegionLocalModel viewRegionLocalModel;
    private MainActivity mainActivity;
    private RegionInfoRepository resLocal;


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) getContext();

        if (activity instanceof DataListener) {
            dataListener = (DataListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement DataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        rvList = view.findViewById(R.id.rvList);
        MainActivity mainActivity = (MainActivity) getActivity();
        viewPager = mainActivity.findViewById(R.id.vpAdd);
        tabLayout = mainActivity.findViewById(R.id.tabLayout);
        progressBar = view.findViewById(R.id.progressBar);
        resLocal = new RegionInfoRepository(getActivity().getApplication());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
         regionList = new ArrayList<>();
        regionChildList = new ArrayList<>();


        regionInfoAdapter = new RegionInfoAdapter(getContext(), regionList, this);

        rvList.setAdapter(regionInfoAdapter);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)) {
                    progressBar.setVisibility(View.VISIBLE);

                    loadNexpage();

                }
            }
        });


        return view;
    }


    @Override
    public void OnItemClick(RegionInfo region, int pos) {
        SM.sendData(region);



    }
public void updateData(List<RegionInfo> newdata) {
        Log.d("dataupdatae",newdata.toString());


        if (newdata.size() > 0) {
            regionList.clear();
            regionList.addAll(newdata);

            if (regionInfoAdapter == null) {
                regionInfoAdapter = new RegionInfoAdapter(getContext(), regionList, this);
                rvList.setAdapter(regionInfoAdapter);
            } else {
                regionInfoAdapter.notifyDataSetChanged();
            }


        }
}



    private void loadNexpage() {

        offset =  mainActivity.offset +=10;
        int i =1;
        Log.d("api_loadNextPage",String.valueOf(i++)+": "+ offset);
        if ((offset < resLocal.getCount() )){


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("api_loadLocal", offset+"");
                    ((MainActivity) getActivity()).loadMoreData(offset);
                    progressBar.setVisibility(View.GONE);


                }
            }, 2000);


        }else {
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

            if (isConnected){

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("api_loadAPI", offset+"");
                        mainActivity.loalDataFromAPI(offset);
                        progressBar.setVisibility(View.GONE);


                    }
                }, 2000);


            }else{
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("api_loadMoreData", offset+"");
                        ((MainActivity) getActivity()).loadMoreData(offset);
                        progressBar.setVisibility(View.GONE);


                    }
                }, 2000);
                Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();



            }



        }

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