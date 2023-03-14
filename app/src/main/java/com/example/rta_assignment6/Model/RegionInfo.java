package com.example.rta_assignment6.Model;


import java.io.Serializable;

public class RegionInfo implements Serializable {
    private String country_id;
    private String region;
    private String where_coordinates;
    private String latitude;
    private String longitude;


    public RegionInfo() {
    }
    public RegionInfo(String region,String where_coordinates ) {
        this.region = region;
        this.where_coordinates = where_coordinates;
    }

    public RegionInfo(String country_id, String region, String where_coordinates, String latitude, String longitude) {
        this.country_id = country_id;
        this.region = region;
        this.where_coordinates = where_coordinates;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWhere_coordinates() {
        return where_coordinates;
    }

    public void setWhere_coordinates(String where_coordinates) {
        this.where_coordinates = where_coordinates;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "RegionInfo{" +
                "country_id='" + country_id + '\'' +
                ", region='" + region + '\'' +
                ", where_coordinates='" + where_coordinates + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
