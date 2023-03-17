package com.example.rta_assignment6.fetchdata;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.TypeConverter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rta_assignment6.MainActivity;
import com.example.rta_assignment6.dblocal.AppDatabase;
import com.example.rta_assignment6.dblocal.RegionInfoRepository;
import com.example.rta_assignment6.model.RegionInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewRegionModel extends ViewModel {
    private MutableLiveData<List<RegionInfo>> myData = new MutableLiveData<>();
    private MutableLiveData<List<RegionInfo>> regionData = new MutableLiveData<>();


    RequestQueue queue;
    private Context context;
    public int lastOffsset = 0  ;
    RegionInfoRepository resLocal ;



    public ViewRegionModel(Context context) {
        this.context = context;
        resLocal = new RegionInfoRepository((Application) context.getApplicationContext());

    }


//    public Collection<? extends RegionInfo> getDatafromAPI(int offset ){
//        System.out.println("----> limit"+ offset);
//        queue = Volley.newRequestQueue(context);
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                "https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=10&offset="+offset+"",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new GsonBuilder().create();
//                        Type regionInfoListType = new TypeToken<List<RegionInfo>>(){}.getType();
//                        List<RegionInfo> regionInfoListfromGson = gson.fromJson(response, regionInfoListType);
//                        myData.postValue(regionInfoListfromGson);
//                        regionCastToList = myData.getValue();
//
//
//                        AppDatabase db = AppDatabase.getDbInstance(context.getApplicationContext());
//                        System.out.print(myData.getValue()+"123123132");
//                      //  db.regionInfoDao().insertAll(regionCastToList);
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//        );
//        queue.add(stringRequest);
//
//        return null;
//    }
//public LiveData<List<RegionInfo>> getDataFromApiAndInsertToDb(int offset) {
//        System.out.print(offset+"getDataFromApiAndInsertToDb123");
//    MutableLiveData<List<RegionInfo>> myData = new MutableLiveData<>();
//
//    queue = Volley.newRequestQueue(context);
//    StringRequest stringRequest = new StringRequest(
//            Request.Method.GET,
//            "https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=10&offset="+offset+"",
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Gson gson = new GsonBuilder().create();
//                    Type regionInfoListType = new TypeToken<List<RegionInfo>>(){}.getType();
//                    List<RegionInfo> regionInfoListfromGson = gson.fromJson(response, regionInfoListType);
//                    myData.postValue(regionInfoListfromGson);
//
//
//
//                    List<RegionInfo> regionCastToList = myData.getValue();
//                    if (regionCastToList == null) {
//                        regionCastToList = new ArrayList<>(); // set default value if it's still null
//                    }
//                    System.out.print(regionCastToList.size()+"123123");
//
////                    AppDatabase db = AppDatabase.getDbInstance(context.getApplicationContext());
////                    db.regionInfoDao().insertAll(regionCastToList);
//
//
//
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//    );
//
//    queue.add(stringRequest);
//
//
//
//    return myData;
//}
//


    public int getLastOffsset() {
        return lastOffsset;
    }

    public void setLastOffsset(int lastOffsset) {
        this.lastOffsset = lastOffsset;
    }

    public LiveData<List<RegionInfo>> getDataFromApiAndInsertToDb1(int offset) {
        this.setLastOffsset(offset);

        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=10&offset="+offset+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();
                        Type regionInfoListType = new TypeToken<List<RegionInfo>>(){}.getType();
                        List<RegionInfo> regionInfoListfromGson = gson.fromJson(response, regionInfoListType);

                        // Update the LiveData object with the new data
                        List<RegionInfo> currentData = regionData.getValue();
                        if (currentData == null) {
                            currentData = new ArrayList<>(); // set default value if it's still null
                        }

                        currentData.addAll(regionInfoListfromGson);
                        Log.d("DATA_THO",currentData.toString());

                        regionData.postValue(currentData);




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

        return regionData;
    }
    public LiveData<List<RegionInfo>> getDataFromApiAndInsertToDb(int offset) {
        this.setLastOffsset(offset);
        queue = Volley.newRequestQueue(context);
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
                      //  List<RegionInfo> regionInfoList = new ArrayList<>();

                     //  regionInfoList.addAll(regionInfoListfromGson);

                        regionData.setValue(regionInfoListfromGson);
                        //Log.d("Listregion_last: " ,"-------->count:"+count+"size:"+itemChangeList.size()+" list"+itemChangeList.toString());
                        //   resLocal.insertAll(regionInfoList);


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

        return regionData;
    }







    public MutableLiveData<List<RegionInfo>> getMyData() {
        return myData;
    }

    public void setMyData(MutableLiveData<List<RegionInfo>> myData) {
        this.myData = myData;
    }
}

