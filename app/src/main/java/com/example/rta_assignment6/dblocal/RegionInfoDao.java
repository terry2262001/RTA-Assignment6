package com.example.rta_assignment6.dblocal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rta_assignment6.model.RegionInfo;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

@Dao
public interface RegionInfoDao {

    @Query("SELECT * FROM regionInfo order by idlocal ASC ")
    LiveData<List<RegionInfo>> getAllRegions();

    @Query("DELETE FROM regionInfo")
     void deleteRegions();

    @Query("SELECT COUNT(*) FROM  regionInfo")
    int getCount();

    @Query("SELECT * FROM regionInfo ORDER BY idlocal ASC LIMIT :limit OFFSET :offset")
    LiveData<List<RegionInfo>> getPagingData(int limit, int offset);


    @Insert
    void insertRegion(RegionInfo... regionInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RegionInfo> regionInfoList);

    @Delete
    void delete(RegionInfo regionInfo);


}
