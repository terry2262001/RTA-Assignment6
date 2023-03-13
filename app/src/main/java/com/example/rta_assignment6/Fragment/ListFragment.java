package com.example.rta_assignment6.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rta_assignment6.Adapter.RegionInfoAdapter;
import com.example.rta_assignment6.MainActivity;
import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.R;
import com.example.rta_assignment6.RegionList;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=10&offset=0",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();
                        //      System.out.println(response);
                        Type regionInfoListType = new TypeToken<List<RegionInfo>>(){}.getType();
                        List<RegionInfo> regionInfoListfromGson = gson.fromJson(response, regionInfoListType);
                        regionList.addAll(regionInfoListfromGson);
                        regionInfoAdapter.notifyDataSetChanged();




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

        );
        queue.add(stringRequest);

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