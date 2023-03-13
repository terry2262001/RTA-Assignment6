package com.example.rta_assignment6.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewMode extends ViewModel {
    private MutableLiveData<RegionInfo> regionInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDataReceivedLiveData = new MutableLiveData<>(false);

    public void setRegionInfo(RegionInfo regionInfo) {
        regionInfoLiveData.setValue(regionInfo);
        isDataReceivedLiveData.setValue(true);
    }

    public LiveData<RegionInfo> getRegionInfo() {
        return regionInfoLiveData;
    }

    public LiveData<Boolean> getIsDataReceived() {
        return isDataReceivedLiveData;
    }
}
