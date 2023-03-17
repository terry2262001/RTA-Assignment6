package com.example.rta_assignment6.dblocal;

import android.app.Application;
import android.content.Context;
import android.graphics.Region;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rta_assignment6.model.RegionInfo;

import java.util.List;

public class ViewRegionLocalModel extends AndroidViewModel {


        private LiveData<List<RegionInfo>> regionInfoListLiveData;
        private RegionInfoRepository regionInfoRepository;

        public ViewRegionLocalModel(@NonNull Application application) {
                super(application);
                regionInfoRepository = new RegionInfoRepository(application);
        }


        public LiveData<List<RegionInfo>> getPaging(int limit, int offset) {
                regionInfoListLiveData = regionInfoRepository.getPagingData(limit, offset);
                return regionInfoListLiveData;
        }





}
