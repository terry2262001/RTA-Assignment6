package com.example.rta_assignment6;

import com.example.rta_assignment6.Model.RegionInfo;

import java.util.List;

public interface DataListener {
    void onDataReceived(List<RegionInfo> data);

}
