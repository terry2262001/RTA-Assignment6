package com.example.rta_assignment6;

import com.example.rta_assignment6.Model.RegionInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionList {

//
//    @SerializedName("country_id")
//    public String country_id;
//
//    @SerializedName("where_coordinates")
//    public String where_coordinates;
//
//    @SerializedName("latitude")
//    public String latitude;
//
//    @SerializedName("where_coordinates")
//    public String longitude;

    private List<RegionInfo> regions = null;

    public RegionList(){

    }

    public RegionList(List<RegionInfo> regions) {
        super();
        this.regions = regions;
    }
    public List<RegionInfo> getRegions() {
        return regions;
    }
    public void setRegions(List<RegionInfo> contacts) {
        this.regions = regions;
    }










}
