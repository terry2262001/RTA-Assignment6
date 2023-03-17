package com.example.rta_assignment6.model;


import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "regionInfo")
public class RegionInfo implements Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idlocal")
    private int id_local= 0 ;
    private  int id ;
    private String country_id;
    private String conflict_name;
    private String region;
    private String country;
    private String where_coordinates;
    private String source_article;
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

    public RegionInfo(int id, String country_id, String conflict_name, String region, String country, String where_coordinates, String source_article, String latitude, String longitude) {
        this.id = id;
        this.country_id = country_id;
        this.conflict_name = conflict_name;
        this.region = region;
        this.country = country;
        this.where_coordinates = where_coordinates;
        this.source_article = source_article;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public String getConflict_name() {
        return conflict_name;
    }

    public void setConflict_name(String conflict_name) {
        this.conflict_name = conflict_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSource_article() {
        return source_article;
    }

    public void setSource_article(String source_article) {
        this.source_article = source_article;
    }
}
