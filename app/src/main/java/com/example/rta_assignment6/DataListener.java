package com.example.rta_assignment6;

import com.example.rta_assignment6.model.RegionInfo;

import java.util.HashSet;
import java.util.List;

public interface DataListener {
    void onDataReceived(List<RegionInfo> data);

}

interface OnDataLoadedListener {
    void onDataLoaded(List<RegionInfo> data);
}
