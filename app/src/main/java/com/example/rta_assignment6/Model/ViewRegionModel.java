package com.example.rta_assignment6.Model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class ViewRegionModel extends ViewModel {
    private MutableLiveData<List<RegionInfo>> myData = new MutableLiveData<>();
    RequestQueue queue;
    private Context context;
    private  int limit ;

    public ViewRegionModel(Context context) {
        this.context = context;
    }


    public Collection<? extends RegionInfo> getDatafromAPI(int limit ){
        System.out.println("----> limit"+ limit);
        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit="+limit+"&offset=0",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();
                        Type regionInfoListType = new TypeToken<List<RegionInfo>>(){}.getType();
                        List<RegionInfo> regionInfoListfromGson = gson.fromJson(response, regionInfoListType);
                        myData.postValue(regionInfoListfromGson);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

        );
        queue.add(stringRequest);

        return null;
    }

    public MutableLiveData<List<RegionInfo>> getMyData() {
        return myData;
    }

    public void setMyData(MutableLiveData<List<RegionInfo>> myData) {
        this.myData = myData;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

