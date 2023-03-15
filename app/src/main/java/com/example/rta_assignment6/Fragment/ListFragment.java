package com.example.rta_assignment6.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Region;
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

import com.example.rta_assignment6.Adapter.RegionInfoAdapter;
import com.example.rta_assignment6.DataListener;
import com.example.rta_assignment6.MainActivity;
import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.Model.ViewRegionModel;
import com.example.rta_assignment6.PanigationScrollListener;
import com.example.rta_assignment6.R;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment  implements RegionInfoAdapter.onItemClickListener{




    List<RegionInfo> regionList ;
    RecyclerView rvList;
    RegionInfoAdapter regionInfoAdapter;

     ViewPager viewPager ;
     TabLayout tabLayout ;
    SendMessage SM;
    int limit =10;
    DataListener dataListener;
    private boolean isLoading ;
    private boolean isLastPage ;
    int totalPage = 100;
    int currentPage = 1;






    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

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
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()+1;

                if (lastVisibleItemPosition == 10){
                    viewRegionModel.setLimit(20);
                    viewRegionModel.getDatafromAPI();
                    System.out.println(viewRegionModel.getLimit());
                    regionInfoAdapter.notifyDataSetChanged();
                }
            }
        });

    //    setFirstData();
              rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()+1;

                if (!recyclerView.canScrollVertically(1)){
                    limit +=10;
                    ((MainActivity) getActivity()).getViewRegionModel().getDatafromAPI(limit);

                }
            }
        });





        return view ;
    }


    @Override
    public void OnItemClick(RegionInfo region, int pos) {
        SM.sendData(region);


        viewPager.setCurrentItem(1);




    }

    public void updateData(List<RegionInfo> newData) {
        regionList.clear();
        regionList.addAll(newData);

        setFirstData();
    }
    private void setFirstData(){
        System.out.println("------>>>>>>"+regionList.size());
        regionInfoAdapter.notifyDataSetChanged();

        if (currentPage  < totalPage ){
            regionInfoAdapter.addFooterLoanding();
        }else{
            isLastPage = true;
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

    private  List<RegionInfo> getRegionList(){
        List<RegionInfo> list  = new ArrayList<>();
        for(int i = 1 ; i <=  10; i++){
            list.add(new RegionInfo("Usre"+i,"12312312"));
        }
        return  list;

    }


}