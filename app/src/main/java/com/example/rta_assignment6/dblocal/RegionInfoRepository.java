package com.example.rta_assignment6.dblocal;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.android.volley.toolbox.Volley;
import com.example.rta_assignment6.model.RegionInfo;

import java.util.List;

public class RegionInfoRepository {
    private RegionInfoDao regionInfoDao;
    private LiveData<List<RegionInfo>> allRegions;
    private LiveData<List<RegionInfo>> pagingData;


    public RegionInfoRepository(Application application) {
        AppDatabase database = AppDatabase.getDbInstance(application);
        regionInfoDao = database.regionInfoDao();
        allRegions =  database.regionInfoDao().getAllRegions();
        pagingData = regionInfoDao.getPagingData(10, 0);
    }
    public int getCount() {
        return regionInfoDao.getCount();
    }

    public LiveData<List<RegionInfo>> getAllRegions() {
        return allRegions;
    }

    public LiveData<List<RegionInfo>> getPagingData(int limit, int offset) {
        pagingData = regionInfoDao.getPagingData(limit, offset);
        return pagingData;
    }
    public void insertAll(List<RegionInfo> regionInfoList) {
        regionInfoDao.insertAll(regionInfoList);
    }

    public void insertRegion(final RegionInfo regionInfo) {
        new InsertAsyncTask(regionInfoDao).execute(regionInfo);
    }

    public void deleteRegion(final RegionInfo regionInfo) {
        new DeleteAsyncTask(regionInfoDao).execute(regionInfo);
    }

    public void deleteAllRegions() {
        new DeleteAllAsyncTask(regionInfoDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<RegionInfo, Void, Void> {
        private RegionInfoDao regionInfoDao;

        InsertAsyncTask(RegionInfoDao regionInfoDao) {
            this.regionInfoDao = regionInfoDao;
        }

        @Override
        protected Void doInBackground(final RegionInfo... params) {
            regionInfoDao.insertRegion(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<RegionInfo, Void, Void> {
        private RegionInfoDao regionInfoDao;

        DeleteAsyncTask(RegionInfoDao regionInfoDao) {
            this.regionInfoDao = regionInfoDao;
        }

        @Override
        protected Void doInBackground(final RegionInfo... params) {
            regionInfoDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private RegionInfoDao regionInfoDao;

        DeleteAllAsyncTask(RegionInfoDao regionInfoDao) {
            this.regionInfoDao = regionInfoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            regionInfoDao.deleteRegions();
            return null;
        }
    }
}
